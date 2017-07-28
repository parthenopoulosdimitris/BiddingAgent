package brown.prediction.strategies;

import brown.prediction.good.GoodPriceVector;


public interface IPredictionStrategy {

	public GoodPriceVector getPrediction();
	
}
