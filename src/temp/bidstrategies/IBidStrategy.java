package temp.bidstrategies;

import java.util.Map;
import java.util.Set;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;
import temp.predictions.IPricePrediction;
//TODO: a bid strategy is a maximizer.

public interface IBidStrategy {
  
  public Map<Tradeable, Value> getBids(Map<Tradeable, Double> values, IPricePrediction prediction);
  
  public Map<Tradeable, Double> getBidsBundle(Map<Set<Tradeable>, Double> values, IPricePrediction prediction);
}