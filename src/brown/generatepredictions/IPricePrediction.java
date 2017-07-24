package brown.generatepredictions;


import java.util.Map;

import brown.prediction.Good;

public interface IPricePrediction {
	
	public Map<Good,Price> getMeanPricePrediction();

	public Map<Good, Price> getRandomPricePrediction();

	
	
	
}
