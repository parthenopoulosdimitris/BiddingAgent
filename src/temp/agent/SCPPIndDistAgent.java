package temp.agent; 

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.logging.library.Logging;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.channel.library.SealedBidChannel;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.messages.library.PrivateInformationMessage;
import brown.platform.messages.library.ValuationInformationMessage;
import brown.system.setup.ISetup;
import brown.system.setup.library.SSSPSetup;
import temp.maximizers.IMaxDist;
import temp.maximizers.library.LocalBidIndDist;
import temp.maximizers.library.TargetPriceDist;
import temp.predictions.IDistributionPrediction;
import temp.predictors.library.SCPPIndDist;


/**
 * SCPP ind dist agent produces self-confirming price predictions stored as 
 * independent histograms over possible prices of goods.
 * @author andrew
 *
 */
public class SCPPIndDistAgent extends AbsPredictAgent {

  public SCPPIndDistAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
  }

  @Override
  public void onSimpleSealed(SealedBidChannel channel) {
    IDistributionPrediction indDistPrediction = ((SCPPIndDist) this.predictor).getPrediction(); 
    // we're gonna want to capture the mean prediction 
    //PointRep rep =  (PointRep) indDistPrediction.getMeanPrediction(new HashSet<ITradeable>(this.tradeables)); 
    //System.out.println("SCPP Prediction: " + rep.rep);
    Map<ITradeable, Double> valuations  = new HashMap<ITradeable, Double>();
    for (ITradeable t : this.tradeables) {
      valuations.put(t, this.valuation.getValuation(t));
    }
    //System.out.println("VALUATIONS: " + valuations);
    Map<ITradeable, Double> bid = ((IMaxDist) this.maximizer).getBids(valuations, indDistPrediction);
    // more annoying conversion
    Map<ITradeable, BidType> returnMap = new HashMap<ITradeable, BidType>();
    for (Entry<ITradeable, Double> e : bid.entrySet()) {
      returnMap.put(e.getKey(), new BidType(e.getValue(), 1)); 
    }
    System.out.println("BIDS: " + returnMap);
    channel.bid(this, new AuctionBidBundle(returnMap));
  }
  
  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {   
    if (privateInfo instanceof ValuationInformationMessage) {
      Logging.log("[-] Valuation Info Received");
      this.tradeables = ((ValuationInformationMessage) privateInfo).getTradeables();
      this.valuation = ((ValuationInformationMessage) privateInfo).getPrivateValuation();
      this.vDistribution = ((ValuationInformationMessage) privateInfo).getAllValuations();
      this.maximizer = new LocalBidIndDist(new TargetPriceDist()); 
      this.predictor = new SCPPIndDist((IMaxDist) this.maximizer, this.tradeables.size(), 
          (AdditiveValuationDistribution) this.vDistribution); 
      for (ITradeable t: this.tradeables) {
        Logging.log("Agent " + this.ID + ", Good: " + t.toString() + ", Value: " +this.valuation.getValuation(t));
      }      
    } else {
      Logging.log("[x] AbsSSSPAgent: Wrong Kind of PrivateInformation Received");
    }
  } 
  
  public static void main(String[] args) {
    // predictor and maximizer rely on private information.
    new SCPPIndDistAgent("localhost", 2121, new SSSPSetup()); 
    while(true){}
}

}