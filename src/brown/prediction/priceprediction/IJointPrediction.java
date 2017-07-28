package brown.prediction.priceprediction;

import brown.prediction.histogram.IndependentHistogram;
import brown.prediction.histogram.JointHistogram;

/**
 * Implementation-level interface for price predictions over
 * goods whose values are dependent.
 * 
 * @author acoggins
 *
 */
public interface IJointPrediction extends IDistribution {

public JointHistogram getPriceDistribution();
  
}
