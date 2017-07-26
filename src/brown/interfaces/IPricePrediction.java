package brown.interfaces;

import java.util.Map;

import brown.generatepredictions.Price;
import brown.prediction.Good;
import brown.prediction.GoodPrice;
import brown.prediction.GoodPriceVector;

public interface IPricePrediction {
	
	public GoodPriceVector getPrediction();

	Map<Good, Price> getMeanPricePrediction();

	Map<Good, Price> getRandomPricePrediction();

}
