package temp.maximizers;

import java.util.Map;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;
import temp.predictions.IDistributionPrediction;

public interface IMaxDist extends IMaximizer {
  
  public Map<Tradeable, Double> getBids(Map<Tradeable, Value> valuations,
      IDistributionPrediction prediction);
  
}