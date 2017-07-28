package brown.prediction.strategies;

import brown.prediction.good.GoodPriceVector;
import brown.prediction.priceprediction.IPricePrediction;
import brown.prediction.valuation.IValuation;

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
	public GoodPriceVector getPrediction(IPricePrediction aPrediction,
	    IValuation aValuation);
	
}
