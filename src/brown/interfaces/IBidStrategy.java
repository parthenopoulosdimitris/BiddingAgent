package brown.interfaces;

import java.util.Map;

import brown.generatepredictions.Price;
import brown.prediction.good.Good;
import brown.prediction.good.GoodPriceVector;

public interface IBidStrategy {
	
	public Map<Good,Price> getBids();
}
