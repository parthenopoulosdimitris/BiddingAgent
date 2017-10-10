package brown.interfaces;

import java.util.Map;
import java.util.Set;

import brown.generatepredictions.IPricePrediction;
import brown.generatepredictions.Price;
import brown.valuable.library.Tradeable;

public interface IBidStrategy {
	
	public Map<Tradeable,Price> getBids(Map<Set<Tradeable>, Double> valuations, IPricePrediction aPrediction);
	
	//public Map<Good, Price> getBids(Map<Good, Double> valuations, IPricePrediction aPrediction)
}
