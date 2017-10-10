package temp.maximizers;

import java.util.Map;
import java.util.Set;

import brown.generatepredictions.Price;
import brown.valuable.library.Tradeable;
import temp.predictions.IJointPrediction;

/**
 * maximizes a bid based on 
 * @author andrew
 *
 */
public interface IBundleMaximizer extends IMaximizer {
  
  public Map<Tradeable,Price> getBids(Map<Set<Tradeable>, Double> valuations, IJointPrediction aPrediction);
  
}