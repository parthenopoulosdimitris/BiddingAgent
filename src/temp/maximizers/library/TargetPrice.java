package temp.maximizers.library; 

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.tradeable.ITradeable;
import temp.maximizers.IMaxPoint;
import temp.predictions.IPointPrediction;
import temp.price.Price;
import temp.representation.PointRep;

/**
 * only bid if it will contribute to the bottom line.
 * @author acoggins
 *
 */
public class TargetPrice implements IMaxPoint {

  @Override
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IPointPrediction aPrediction) {
    // some annoying conversions.
    Map<ITradeable, Price> pred = ((PointRep) aPrediction.getPrediction(valuations.keySet())).rep; 
    Map<ITradeable, Double> predTwo = new HashMap<ITradeable, Double>(); 
    for (Entry<ITradeable, Price> e : pred.entrySet()) {
      if (e.getValue().rep > 0)
        predTwo.put(e.getKey(), e.getValue().rep); 
    }
    return predTwo;
  }
  
  
}