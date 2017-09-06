package brown.prediction.strategies;

import brown.prediction.goodprice.Price;
import brown.prediction.goodprice.Bundle;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPriceVector;
import brown.prediction.priceprediction.IPricePrediction;

/**
 * Interface for a prediction strategy.
 * @author acoggins
 *
 */
public interface IPredictionStrategy {

  /*
   * every conceivable prediction strategy has to return a proposed bid 
   * given some price prediction, and some valuation
   */
	public GoodPriceVector<Good, Price> getPrediction(IPricePrediction aPrediction,
	    GoodPriceVector<Bundle, Price> aValuation);
	
}
