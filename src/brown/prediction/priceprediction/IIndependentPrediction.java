package brown.prediction.priceprediction;

import brown.prediction.histogram.IndependentHistogram;

/**
 * implementation-level interface for price prediction. 
 * Gives a prediction for a distribution over prices of goods
 * whose values are independent.
 * 
 * @author acoggins
 *
 */
public interface IIndependentPrediction extends IDistribution { 

  public IndependentHistogram getPriceDistribution();
  
}
