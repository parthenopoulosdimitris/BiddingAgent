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
   PointRep pred = (PointRep) prediction.getMeanPrediction(valuations.keySet());
   Map<ITradeable, Price> rep = pred.rep;
   Map<ITradeable, Double> returnMap = new HashMap<ITradeable, Double>();
   for(Entry<ITradeable, Price> item : rep.entrySet()) {
     if (item.getValue().rep <= valuations.get(item.getKey())) { 
       returnMap.put(item.getKey(), item.getValue().rep); 
     }
   }
   return returnMap;
  }
  
}