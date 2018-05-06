package temp.maximizers;

import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import temp.predictions.IDistributionPrediction;

public interface IMaxComplexDist extends IMaximizer {
  
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IDistributionPrediction prediction);
}