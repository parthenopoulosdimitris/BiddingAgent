package temp.maximizers;

import java.util.Map;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;
import temp.predictions.IPointPrediction;

public interface IAddMaxPoint extends IMaximizer {
  
  public Map<Tradeable,Double> getBids(Map<Tradeable, Value> valuations, IPointPrediction aPrediction);
  
}