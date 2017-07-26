package brown.interfaces;

import java.util.Map;

import brown.generatepredictions.Price;
import brown.prediction.Good;
import brown.prediction.GoodPriceVector;

public interface IBidStrategy {
	
	public Map<Good,Price> getBids();
}
