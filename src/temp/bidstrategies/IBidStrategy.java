package temp.bidstrategies;

import java.util.Map;
import java.util.Set;

import brown.mechanism.tradeable.ITradeable;
import temp.predictions.IPricePrediction;
//TODO: a bid strategy is a maximizer.

public interface IBidStrategy {
  
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> values, IPricePrediction prediction);
  
  public Map<ITradeable, Double> getBidsBundle(Map<Set<ITradeable>, Double> values, IPricePrediction prediction);
}