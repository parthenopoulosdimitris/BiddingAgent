package brown.prediction.strategies; 

import brown.prediction.goodprice.Price;
import brown.prediction.goodprice.Bundle;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPriceVector;
import brown.prediction.priceprediction.IPricePrediction;

/**
 * Expected Value prediction strategy implements a price-prediction strategy
 * that gives the expected value of each good in a vector, given a
 * distribution of possible prices for the goods.
 * 
 * @author acoggins
 *
 */
public class ExpectedValue implements IPredictionStrategy {


@Override
public GoodPriceVector<Good, Price> getPrediction(IPricePrediction aPrediction,
		GoodPriceVector<Bundle, Price> aValuation) {
	// TODO Auto-generated method stub
	return null;
} 
  
}