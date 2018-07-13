package temp.maximizers.library;

import java.util.HashMap;
import java.util.Map;

import brown.mechanism.tradeable.ITradeable;
import temp.maximizers.IMaxPoint;
import temp.predictions.IPointPrediction;
import temp.price.Price;
import temp.representation.PointRep;

public class PointRandom implements IMaxPoint {

  @Override
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IPointPrediction aPrediction) {
    Map<ITradeable, Price> pred = ((PointRep) aPrediction.getPrediction(valuations.keySet())).rep; 
    
    Map<ITradeable, Double> returnMap = new HashMap<ITradeable, Double>(); 
    for (ITradeable t : pred.keySet()) {
      returnMap.put(t, Math.random()); 
    }
    return returnMap;
  }
  
}