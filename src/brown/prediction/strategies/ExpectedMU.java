package brown.prediction.strategies;

import brown.prediction.goodprice.Bundle;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPriceVector;
import brown.prediction.goodprice.Price;
import brown.prediction.priceprediction.IPricePrediction;

/**
 * ExpectedMU is a price prediction strategy that gives the expected
 * marginal utility of a good given a distribution of prices for 
 * each good.
 * @author acoggins
 *
 */
public class ExpectedMU implements IPredictionStrategy {

@Override
public GoodPriceVector<Good, Price> getPrediction(IPricePrediction aPrediction,
		GoodPriceVector<Bundle, Price> aValuation) {
	// TODO Auto-generated method stub
	return null;
}
  
}