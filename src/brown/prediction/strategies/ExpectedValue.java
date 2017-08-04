package brown.prediction.strategies; 

import brown.prediction.good.GoodPriceVector;
import brown.prediction.priceprediction.IPricePrediction;
import brown.prediction.valuation.IValuation;

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
  public GoodPriceVector getPrediction(IPricePrediction aPrediction,
      IValuation aValuation) {
    // TODO Auto-generated method stub
    return null;
  } 
  
}