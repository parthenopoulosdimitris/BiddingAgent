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
import temp.maximizers.IMaxPoint;
import temp.maximizers.library.TargetPrice;
import temp.predictions.IPointPrediction;
import temp.predictors.library.SCPPPoint;

/**
 * SCPP Point Agent uses SCPP to find point predicitons for 
 * the prices of goods. 
 * @author andrew
 *
 */
public class SCPPPointAgent extends AbsPredictAgent {

  public SCPPPointAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
  }

  @Override
  public void onSimpleSealed(SealedBidChannel channel) {
  // 1. generate good price predictions
  // 2. bid accordingly
  // 1. 
  System.out.println("Beginning of SCPP"); 
  IPointPrediction simplePoint = ((SCPPPoint) this.predictor).getPrediction(); 
  System.out.println("End of SCPP"); 
  // annoying conversion, pt. 1
  Map<ITradeable, Double> valuations = new HashMap<ITradeable, Double>(); 
  for (ITradeable t : this.tradeables) {
    valuations.put(t, this.valuation.getValuation(t)); 
  }
  System.out.println("VALUATIONS: " + valuations);
  // 2. 
  Map<ITradeable, Double> bid = ((IMaxPoint) this.maximizer).getBids(valuations, simplePoint);
  // more annoying conversion
  Map<ITradeable, BidType> bType = new HashMap<ITradeable, BidType>();
  for (Entry<ITradeable, Double> e : bid.entrySet()) {
    bType.put(e.getKey(), new BidType(e.getValue(), 1)); 
  }
  System.out.println(bid); 
  channel.bid(this, new AuctionBidBundle(bType)); 
  }
  
  // TODO: make some sort of dependent valuation class.
  // TODO: SCPP ind. dist.
  // TODO: write a better price prediction strategy. 
  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {   
    if (privateInfo instanceof ValuationInformationMessage) {
      Logging.log("[-] Valuation Info Received");
      this.tradeables = ((ValuationInformationMessage) privateInfo).getTradeables();
      this.valuation = ((ValuationInformationMessage) privateInfo).getPrivateValuation();
      this.vDistribution = ((ValuationInformationMessage) privateInfo).getAllValuations();
      this.maximizer = new TargetPrice(); 
      this.predictor = new SCPPPoint((IMaxPoint) this.maximizer, this.tradeables.size(), 100, 100, 0.05, 
          (AdditiveValuationDistribution) this.vDistribution); 
      for (ITradeable t: this.tradeables){
        Logging.log("Agent " + this.ID + ", Good: " + t.toString() + ", Value: " +this.valuation.getValuation(t));
      }      
    } else {
      Logging.log("[x] AbsSSSPAgent: Wrong Kind of PrivateInformation Received");
    }
  } 
  
  public static void main(String[] args) {
      // predictor and maximizer rely on private information.
      new SCPPPointAgent("localhost", 2121, new SSSPSetup()); 
      while(true){}
  }

  
  
}