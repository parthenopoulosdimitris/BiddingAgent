package temp.maximizers;

import java.util.Map;
import brown.mechanism.tradeable.ITradeable;
import temp.predictions.IDistributionPrediction;

public interface IMaxComplexDist extends IMaximizer {
  
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IDistributionPrediction prediction);
}