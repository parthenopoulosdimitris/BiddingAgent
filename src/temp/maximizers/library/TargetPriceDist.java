package temp.maximizers.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.tradeable.ITradeable;
import temp.maximizers.IMaxDist;
import temp.predictions.IDistributionPrediction;
import temp.price.Price;
import temp.representation.PointRep;

/**
 * takes in valuations and a prediction over the distribution
 * of prices. Bids at the mean price if such good is in the
 * optimal bid bundle at the predicted price.
 * @author andrew
 *
 */
public class TargetPriceDist implements IMaxDist {

  @Override
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IDistributionPrediction prediction) {
  // System.out.println("VALUATIONS: " + valuations);
  // System.out.println("PREDICTION: " + ((PointRep) prediction.getMeanPrediction(valuations.keySet())).rep);
   PointRep pred = (PointRep) prediction.getMeanPrediction(valuations.keySet());
   Map<ITradeable, Price> rep = pred.rep;
   Map<ITradeable, Double> returnMap = new HashMap<ITradeable, Double>();
   for(Entry<ITradeable, Price> item : rep.entrySet()) {
     //System.out.println("A");
     //System.out.println(item.getValue().rep);
     //System.out.println(valuations.get(item.getKey()));
     if (item.getValue().rep <= valuations.get(item.getKey())) { 
       returnMap.put(item.getKey(), item.getValue().rep); 
     }
   }
   //System.out.println("RETURN MAP: " + returnMap);
   return returnMap;
  }
  
}