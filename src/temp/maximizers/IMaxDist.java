package temp.maximizers;

import java.util.Map;

import brown.tradeable.ITradeable;
import temp.predictions.IDistributionPrediction;

public interface IMaxDist extends IMaximizer {
  
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IDistributionPrediction prediction);
  
}