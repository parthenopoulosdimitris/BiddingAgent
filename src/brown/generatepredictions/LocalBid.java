package brown.generatepredictions;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.interfaces.IBidStrategy;
import brown.interfaces.IPricePrediction;
import brown.interfaces.IValuation;
import brown.prediction.Good;

public class LocalBid implements IBidStrategy {
	double slack = 0.0001;
	double NUM_ITERATIONS=100;
	int NUM_SAMPLES=1000;
	Map<Good,Price> bids=null;
	private Double[] _currentBid;
	
	public LocalBid(IPricePrediction pp, IValuation val) {
		bids=pp.getMeanPricePrediction();
		for (int i=1; i<NUM_ITERATIONS; i++){
			for(Good good:bids.keySet()){
				double marginalUtility=0.0;
				double totalMarginalUtility = 0.0;
				for (int j=1; j<NUM_SAMPLES; j++){
					Map<Good,Price> prices =pp.getRandomPricePrediction();
					double totalPrice=0.0;
					Set<Good> winnings = new HashSet<Good>();
					for(Good g:prices.keySet()){
						if(bids.get(good).getPrice()+slack >prices.get(good).getPrice()){
							winnings.add(good);
							if(!good.equals(g)){
								totalPrice += prices.get(g).getPrice();
							}
						}   
					}
					double valWithGood, valWithOutGood;
					winnings.add(good);
					valWithGood=val.getValuation(winnings);
					winnings.remove(good);
					valWithOutGood=val.getValuation(winnings);
					totalMarginalUtility+=(valWithGood-valWithOutGood)-totalPrice;
				}
				Price totMarginUtilityPrice = new Price(totalMarginalUtility/(double)NUM_SAMPLES);
				bids.put(good, totMarginUtilityPrice);
			}
			
		}
	}
	
	@Override
	public Map<Good, Price> getBids() {
		return bids;
		}
	}
