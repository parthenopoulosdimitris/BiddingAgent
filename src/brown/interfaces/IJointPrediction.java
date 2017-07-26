package brown.interfaces;

import brown.prediction.IndependentHistogram;
import brown.prediction.JointHistogram;

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
