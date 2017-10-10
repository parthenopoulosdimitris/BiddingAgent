package temp.bidstrategies;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;
import temp.predictions.IPricePrediction;

public interface IBidStrategy {
  
  public Map<Tradeable, Value> getBids(Map<Tradeable, Double> values, IPricePrediction prediction);
  
  public Map<Tradeable, Double> getBidsBundle(Map<Set<Tradeable>, Double> values, IPricePrediction prediction);
}