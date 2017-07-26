package brown.interfaces;

import java.util.Map;

import brown.generatepredictions.Price;
import brown.prediction.Good;

/**
 * Disribution-level interface for Price predictions.
 * Is extended by IJointPrediction and IIndependentPrediction.
 * 
 * @author acoggins
 *
 */
public interface IDistribution extends IPricePrediction {
  
}
