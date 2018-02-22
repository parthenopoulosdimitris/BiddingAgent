package temp.agent; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import temp.maximizers.IMaxDist;
import temp.maximizers.IMaxPoint;
import temp.maximizers.library.TargetPrice;
import temp.predictions.IDistributionPrediction;
import temp.predictors.library.SCPPIndDist;
import temp.price.Price;
import temp.representation.PointRep;
import brown.bid.interim.BidType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.ValuationInformationMessage;
import brown.setup.ISetup;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;

public class SCPPIndDistAgent extends MaxPredictAgent {

  public SCPPIndDistAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }

  @Override
  public void onSimpleSealed(AuctionChannel channel) {
    System.out.println("Beginning of SCPP"); 
    IDistributionPrediction indDistPrediction = ((SCPPIndDist) this.predictor).getPrediction(); 
    // we're gonna want to capture the mean prediction
    System.out.println("End of SCPP"); 
    Map<ITradeable, Double> valuations  = new HashMap<ITradeable, Double>();
    for (ITradeable t : this.goods) {
      valuations.put(t, this.valuation.getValuation(t));
    }
    Map<ITradeable, Double> bid = ((IMaxDist) this.maximizer).getBids(valuations, indDistPrediction);
    // more annoying conversion
    Map<ITradeable, BidType> returnMap = new HashMap<ITradeable, BidType>();
    for (Entry<ITradeable, Double> e : bid.entrySet()) {
      returnMap.put(e.getKey(), new BidType(e.getValue(), 1)); 
    }
    channel.bid(this, new AuctionBidBundle(returnMap));
  }
  
  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {   
    if (privateInfo instanceof ValuationInformationMessage) {
      Logging.log("[-] Valuation Info Received");
      this.tradeables = ((ValuationInformationMessage) privateInfo).getTradeables();
      this.valuation = ((ValuationInformationMessage) privateInfo).getPrivateValuation();
      this.vDistribution = ((ValuationInformationMessage) privateInfo).getAllValuations();
      this.maximizer = new TargetPrice(); 
      // TODO: valuation over complex tradeables.
      this.predictor = new SCPPIndDist((IMaxDist) this.maximizer, this.tradeables.size(), 100, 100, 0.05, 
          (AdditiveValuationDistribution) this.vDistribution); 
      for (ITradeable t: this.tradeables) {
        Logging.log("Agent " + this.ID + ", Good: " + t.toString() + ", Value: " +this.valuation.getValuation(t));
      }      
    } else {
      Logging.log("[x] AbsSSSPAgent: Wrong Kind of PrivateInformation Received");
    }
  } 
  
  public static void main(String[] args) throws AgentCreationException {
    // predictor and maximizer rely on private information.
    new SCPPIndDistAgent("localhost", 2121, new SSSPSetup()); 
    while(true){}
}
  
}