package temp.agent; 

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.auction.value.valuation.IBundleValuation;
import brown.logging.library.Logging;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.channel.library.SealedBidChannel;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.ComplexTradeable;
import brown.platform.messages.library.PrivateInformationMessage;
import brown.platform.messages.library.ValuationInformationMessage;
import brown.system.setup.library.SSSPSetup;
import temp.maximizers.IMaxPoint;
import temp.maximizers.library.PointRandom;
import temp.maximizers.library.TargetPrice;
import temp.predictions.IPointPrediction;
import temp.predictors.library.SCPPPoint;

/**
 * SCPP Point Agent uses SCPP to find point predicitons for 
 * the prices of goods. 
 * @author andrew
 *
 */
public class SCPPPointV2Agent extends AbsPredictAgent {

  public SCPPPointV2Agent(String host, int port) {
    super(host, port, new SSSPSetup());
  }
  
  public SCPPPointV2Agent(String host, int port, String name) {
    super(host, port, new SSSPSetup(), name);
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
  Map<ComplexTradeable, Double> bv = ((IBundleValuation) this.valuation).getAllValuations(); 
  for (ComplexTradeable ct : bv.keySet()) {
    valuations.put(ct, bv.get(ct)); 
  }
  System.out.println("VALUATIONS: " + valuations);
  // 2. 
  Map<ITradeable, Double> bid = ((IMaxPoint) new TargetPrice()).getBids(valuations, simplePoint);
  // more annoying conversion
  Map<ITradeable, BidType> bType = new HashMap<ITradeable, BidType>();
  for (Entry<ITradeable, Double> e : bid.entrySet()) {
    bType.put(e.getKey(), new BidType(e.getValue(), 1)); 
  }
  System.out.println("BID: " + bid); 
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
      this.maximizer = new PointRandom(); 
      this.predictor = new SCPPPoint((IMaxPoint) this.maximizer, this.tradeables.size(), 100, 100, 0.05, 
          this.vDistribution);       
    } else {
      Logging.log("[x] AbsSSSPAgent: Wrong Kind of PrivateInformation Received");
    }
  } 
  
  public static void main(String[] args) {
      // predictor and maximizer rely on private information.
      new SCPPPointV2Agent("localhost", 2121); 
      while(true){}
  }

  
  
}