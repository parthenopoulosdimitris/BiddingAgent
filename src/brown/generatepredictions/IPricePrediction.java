package brown.generatepredictions;

import generatePredictions.Price;

import java.util.Map;

import brown.prediction.Good;

public interface IPricePrediction {
	
	public Map<Good,Price> getMeanPricePrediction();

	public Map<Good, Price> getRandomPricePrediction();

	
	
	
}
