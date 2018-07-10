package temp.maximizers;

import java.util.Map;

import brown.mechanism.tradeable.ITradeable;
import temp.predictions.IPointPrediction;

public interface IMaxPoint extends IMaximizer {
  
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IPointPrediction aPrediction);
  
}