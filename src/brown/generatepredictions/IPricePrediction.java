package brown.generatepredictions;


import java.util.Map;

import brown.prediction.good.Good;

public interface IPricePrediction {
	
	public Map<Good,Price> getMeanPricePrediction();

	public Map<Good, Price> getRandomPricePrediction();

	
	
	
}
