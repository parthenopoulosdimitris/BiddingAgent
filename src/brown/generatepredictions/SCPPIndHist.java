package brown.generatepredictions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.generator.library.NormalGenerator;
import brown.interfaces.IBidStrategy;
import brown.prediction.histogram.IndependentHistogram;
import brown.prediction.priceprediction.IIndependentPrediction;
import brown.prediction.priceprediction.IPricePrediction;
import brown.prediction.strategies.IPredictionStrategy;
import brown.valuation.library.AdditiveValuation;
import temp.MetaVal;

public class SCPPIndHist implements IIndependentPrediction {
	 double alpha =0.1;
	 double eps=0.1;
	 int NUM_ITERATIONS=100;
	 int NUM_SAMPLES=1000;
	 IPricePrediction pricePrediction = null;
	 MetaVal distInfo;
	 
	 public SCPPIndHist(IBidStrategy bs, MetaVal distInfo){
	   this.distInfo = distInfo;
		 IndHistogram pp = new IndHistogram(Constants.NUM_GOODS);
		 pp.normalize();
		 IndHistogram temp = new IndHistogram(Constants.NUM_GOODS);
		 for (int i=1; i<NUM_ITERATIONS; i++){
			 temp.getMap().clear();
			 temp=populateTemp(bs);
			 pp=this.smooth(pp,temp);
		 }
		 pricePrediction=pp;
	 }
	 private IndHistogram populateTemp(IBidStrategy bs) {
		 IndHistogram temp = new IndHistogram(Constants.NUM_GOODS);
		 for(int j=1; j<NUM_SAMPLES; j++){
			 for(int l=1; l<Constants.NUM_AGENTS; l++){
			   Map<Good, Double> = getValuations(NUM_GOODS);
				 Map<Good,Price> bids = bs.getBids();
				 for(Good good:bids.keySet()) {
					 double priceValue =temp.getBucket(good, bids.get(good).getPrice()).getPrice();
					 Price price = new Price(priceValue);
					 temp.incCount(good,price);
				 }
			 }
		 }
		 temp.normalize();
		 return temp;
	 }
	 
	 private IndHistogram smooth(IndHistogram pp, IndHistogram temp){
		 for(Good good:pp.getMap().keySet()){ 
			for(Price price:pp.getMap().get(good).keySet()){
				double newProb =(1.0-alpha)*pp.getMap().get(good).get(price)+alpha*temp.getMap().get(good).get(price);
				Map<Price,Double> newProbMap = new HashMap<Price, Double>();
				 newProbMap.put(price,newProb);
				 pp.getMap().put(good, newProbMap);
			}	 
		 }
		 return pp;
	 }
	 
	 
	 private Map<Good, Double> getValuations(int numGoods) {
	   Set<brown.valuable.library.Good> goods = new HashSet<>();
	   Map<Good, Double> valuations = new HashMap<Good, Double>();
	   for(int i = 0; i < numGoods; i++) {
	     brown.valuable.library.Good newGood = new brown.valuable.library.Good(i);
	     goods.add(newGood);
	   }
	   NormalGenerator norm = new NormalGenerator(distInfo.getValFunction(),
	        distInfo.getMonotonic(), distInfo.getScale());
	   AdditiveValuationSet addVal = norm.getAdditiveValuation(new Bundle(goods));
	   for(brown.valuable.library.Good g : addVal.getMap().keySet()) {
	     valuations.put(g.ID, addVal.getMap().get(g));
	   }
	   return valuations;
	 }
	 
	 private double testConvergence(IndHistogram pp, IndHistogram temp){
		 Double error = Double.NEGATIVE_INFINITY;
		 Double totalError=0.0;
		 for(Good good: pp.getMap().keySet()){
			 for(Price price:pp.getMap().get(good).keySet()){
				 error=	Math.max(error, Math.abs(pp.getMap().get(good).get(price)-temp.getMap().get(good).get(price)));
			 }
			 totalError+=error;
		 }
		 totalError/=pp.getMap().size();
		 return totalError;
	 }
	 
  @Override
  public Map<Good, Price> getMeanPricePrediction() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public Map<Good, Price> getRandomPricePrediction() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public IndependentHistogram getPriceDistribution() {
    // TODO Auto-generated method stub
    return null;
  }
	
}
