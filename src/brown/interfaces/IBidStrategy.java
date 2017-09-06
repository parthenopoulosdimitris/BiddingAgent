package brown.interfaces;

import java.util.Map;

import brown.generatepredictions.Price;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPriceVector;

public interface IBidStrategy {
	
	public Map<Good,Price> getBids();
}
