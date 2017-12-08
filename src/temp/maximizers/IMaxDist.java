package temp.maximizers;

import java.util.Map;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;
import temp.predictions.IDistributionPrediction;

public interface IMaxDist extends IMaximizer {
  
  public Map<Tradeable, Double> getBids(Map<Tradeable, Value> valuations,
      IDistributionPrediction prediction);
  
}