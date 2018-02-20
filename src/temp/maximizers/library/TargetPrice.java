package temp.maximizers.library; 

import java.util.HashMap;
import java.util.Map;

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
    // check that the valuation is higher than the prediction and bid if it is.
    for (ITradeable t : pred.keySet()) {
      if ((valuations.get(t) - pred.get(t).rep) > 0)
        predTwo.put(t, valuations.get(t)); 
    }
    return predTwo;
  }
  
  
}