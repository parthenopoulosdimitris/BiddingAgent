package brown.generatepredictions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import brown.prediction.good.Good;
import brown.prediction.good.GoodPrice;
import brown.prediction.good.GoodPriceVector;
import brown.prediction.histogram.IndependentHistogram;
import brown.prediction.priceprediction.IIndependentPrediction;

public class IndHistogram implements IIndependentPrediction {


private Map<Good, Map<Price, Double>> hist;
private Set<Price> priceSet;

/*
 * The constructor for IndHist. Contains an argument representing the number of Goods that are being auctioned at the same time.
 * The constructor populates a Map, with Key the different goods that are being auctioned and Valuem the different prices
 * for the good, and the probability that the bidder has that price as Valuation.
 */
	public IndHistogram(int numGoods) {
		  priceSet = new HashSet<Price>();
		 Double range =Constants.MAX_VAL- Constants.MIN_VAL;
		 Double step =(range + 1.0) / (double) Constants.NUM_PRICES;
		 for(int j = 1; j < Constants.NUM_PRICES; j++) {
			 Price price = new Price((double) Math.round((Constants.MIN_VAL + j * step)));
			priceSet.add(price);
		 }
		 for (Double i = Constants.MIN_VAL; i <= Constants.MAX_VAL; i += step){
			 Price priceStep = new Price(i);
			 priceSet.add(priceStep);
		 }
		 for(Good good : Constants.goodSet) {
			 Map<Price, Double> initialCounts = new HashMap<Price, Double>();
			 for (Price price:priceSet) {
				 initialCounts.put(price, 1.0);
			 }
			 hist.put(good, initialCounts);
		 }
	}
	
	// This method returns the bucket Price for a bid.
	public Price getBucket(Good g, double bid) {
		Price bucketPrice= new Price(Math.max(Constants.MIN_VAL, Math.min(Constants.MAX_VAL, Math.round(bid))));
		return bucketPrice;
	}
	
	//This method increases the Count for a specific Price of a specific Good by 1.0 and updates the histogram.
	public void incCount(Good good, Price price) {
		Map<Price, Double> innerMap = new HashMap<Price, Double>();
		innerMap.put(price, hist.get(good).get(price) + 1.0);
		hist.put(good, innerMap);
	}

	public void normalize() {
		for(Good good:hist.keySet()) {
			hist.put(good,normalize(hist.get(good)));
		}
	}
	
	private Map<Price, Double> normalize(Map<Price, Double> toNormalize) {
		Map<Price, Double> normalized = new HashMap<Price,Double>();
		double total = 0.0;
		for(Price price : priceSet){
			total += toNormalize.get(price);
		}
		for(Price price:priceSet){
			normalized.put(price, toNormalize.get(price)/total);
		}
		return normalized;
	}
	
	public Map<Good,Map<Price,Double>> getMap() {
		return hist;
	}

	@Override
	public Map<Good, Price> getMeanPricePrediction() {
		Map<Good, Price> meanPricePrediction = new HashMap<Good, Price>();
		Set<Good> good = hist.keySet();
		Iterator<Good> goodIterator = good.iterator();
		while (goodIterator.hasNext()){
			Good nextGood = goodIterator.next();
			Map<Price, Double> priceSet = hist.get(nextGood);
			Iterator<Price> priceIterator=priceSet.keySet().iterator();
			Price mean = new Price(0.0);
			Double meanPrice = 0.0;
			while(priceIterator.hasNext()){
				Price nextPrice = priceIterator.next();
				meanPrice += nextPrice.getPrice() * priceSet.get(nextPrice); 
				mean.setPrice(meanPrice);
				priceIterator.remove(); 
			}
			meanPricePrediction.put(nextGood, mean);
			goodIterator.remove(); 
		}
		return meanPricePrediction;
	}

	@Override
	public Map<Good, Price> getRandomPricePrediction() {
		Map<Good, Price> randomPricePredictionMap = new HashMap<Good,Price>();
		Double[] increasingProbs = new Double[hist.size()];
		Set<Good> good = hist.keySet();
		Iterator<Good> goodIterator = good.iterator();
		Map<Double, Price> increasedProbsMap = new HashMap<Double, Price>();
		while(goodIterator.hasNext()){
			Good nextGood = goodIterator.next();
			Map<Price, Double> priceSet = hist.get(nextGood);
			Iterator<Price> priceIterator=priceSet.keySet().iterator();
			Price priceOne = priceIterator.next();
			increasingProbs[0]=priceOne.getPrice();
			increasedProbsMap.put(priceOne.getPrice(),priceOne);
			int counter =1;
			while(priceIterator.hasNext()){
				Price nextPrice=priceIterator.next();
				increasingProbs[counter]=nextPrice.getPrice()+increasingProbs[counter-1];
				increasedProbsMap.put(increasingProbs[counter], nextPrice);
				counter+=1;
			}
			double randomSample =Math.random();
			Price randomPrice=null;
			int newCounter=1;
			if (randomSample > increasingProbs[0]){
				while (randomSample>increasingProbs[newCounter]){
					newCounter+=1;
				}
				randomPrice=increasedProbsMap.get(increasingProbs[counter]);
			}
			else{
				randomPrice=increasedProbsMap.get(increasingProbs[0]);
			}
			randomPricePredictionMap.put(nextGood,randomPrice);
		}
		return randomPricePredictionMap;
	}
	
	

	/*
	 * This methods returns the distribution of probabilities for each good in the form of
	 * an array.
	 
		@Override
		public Double[] getDistPerGood(int goodNumber) {
			Double[] distributionPerGood = new Double[(int) Constants.NUM_PRICES];
			for (int j=0; j<Constants.NUM_PRICES; j++){
				distributionPerGood[j] =_histogram[goodNumber][j];
			}
			return distributionPerGood;
		}

	 * This method returns the mean price per good, as calculated based on the probabilities 
	 * drawn from the histogram for each good. For each good the probability for each price is 
	 * multiplied with the value of the price itself and then added to the mean. 
	 
		@Override
		public Double[] getMeanPerGood() {
			Double[] meanPerGood = new Double[Constants.NUM_GOODS];
			for (int i=0; i< Constants.NUM_GOODS; i++){
				for (int j=0; j<Constants.NUM_PRICES; j++){
					meanPerGood[i] +=_histogram[i][j] * j; 
				}
			}
			return meanPerGood;
		}

		@Override
		public Double getRandomSample(int goodNumber) {
			int sumOfProbs = 0;
			ArrayList<Double> list = new ArrayList<Double>();
			for (int j=0; j<Constants.NUM_PRICES; j++){
				sumOfProbs =(int) (_histogram[goodNumber][j]*10);
				for (int k=0; k<sumOfProbs; k++){
					list.add((double) j);
				}
			}
			return list.get((int) (Math.random()*list.size()));
		}
		*/

	@Override
	public GoodPriceVector getPrediction() {
		// TODO Auto-generated method stub
		return null;
	}

  @Override
  public IndependentHistogram getPriceDistribution() {
    // TODO Auto-generated method stub
    return null;
  }



}
