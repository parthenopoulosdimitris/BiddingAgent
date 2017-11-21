package temp.maximizers;

import java.util.Map;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;
import temp.predictions.IPointPrediction;

public interface IMaxPoint extends IMaximizer {
  
  public Map<Tradeable, Double> getBids(Map<Tradeable, Value> valuations,
      IPointPrediction aPrediction);
  
}