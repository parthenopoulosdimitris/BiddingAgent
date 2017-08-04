package brown.prediction.strategies;

import brown.prediction.good.GoodPriceVector;
import brown.prediction.priceprediction.IPricePrediction;
import brown.prediction.valuation.IValuation;

/**
 * ExpectedMU is a price prediction strategy that gives the expected
 * marginal utility of a good given a distribution of prices for 
 * each good.
 * @author acoggins
 *
 */
public class ExpectedMU implements IPredictionStrategy {

  @Override
  public GoodPriceVector getPrediction(IPricePrediction aPrediction,
      IValuation aValuation) {
    // TODO Auto-generated method stub
    return null;
  }
  
}