package brown.interfaces;

import java.util.Map;
import java.util.Set;

import temp.predictions.IPricePrediction;
import brown.generatepredictions.Price;
import brown.tradeable.library.Tradeable;

public interface IBidStrategy {
	
	public Map<Tradeable,Price> getBids(Map<Set<Tradeable>, Double> valuations, IPricePrediction aPrediction);
	
	//public Map<Good, Price> getBids(Map<Good, Double> valuations, IPricePrediction aPrediction)
}
