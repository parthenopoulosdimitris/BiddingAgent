package brown.prediction.priceprediction;

import brown.prediction.goodprice.Dist;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPriceVector;

/**
 * implementation-level interface for price prediction. 
 * Gives a prediction for a distribution over prices of goods
 * whose values are independent.
 * 
 * @author acoggins
 *
 */
public interface IIndependentPrediction extends IDistribution { 

  public GoodPriceVector<Good, Dist> getPrediction();
  
  public void setPrediction(GoodPriceVector<Good, Dist> inputPrediction);
  
}
