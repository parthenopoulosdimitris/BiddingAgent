package brown.interfaces;

import brown.prediction.GoodPriceVector;


public interface IPredictionStrategy {

	public GoodPriceVector getPrediction();
	
}
