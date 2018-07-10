package temp.predictions.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import brown.mechanism.tradeable.ITradeable;
import temp.histograms.IndependentHistogram;
import temp.predictions.IDistributionPrediction;
import temp.price.IndDist;
import temp.price.Price;
import temp.representation.IndRep;
import temp.representation.PointRep;

/**
 * an independent price prediction. Distriubtions over prices of
 * goods are independent.
 * @author andrew
 *
 */
public class SimpleIndPrediction implements IDistributionPrediction {
  
  private Map<ITradeable, IndDist> prediction; 
  
  public SimpleIndPrediction(Map<ITradeable, IndDist> prediction) {
    this.prediction = prediction;
  }

  @Override
  public PointRep getMeanPrediction(Set<ITradeable> goods) {
    Map<ITradeable, IndependentHistogram> predictions = new HashMap<ITradeable, IndependentHistogram>();
    for (ITradeable good : goods) {
      IndependentHistogram aHist = prediction.get(good).rep;
      predictions.put(good, aHist);
    }
    Map<ITradeable, Price> returnRep = new HashMap<ITradeable, Price>();
    // get the mean price over the whole histogram.
    for(Entry<ITradeable, IndependentHistogram> ent : predictions.entrySet()) {
      returnRep.put(ent.getKey(), new Price(ent.getValue().getMean())); 
    }
    return new PointRep(returnRep);
  }

  @Override
  public PointRep getRandomPrediction(Set<ITradeable> goods) {
    Map<ITradeable, IndependentHistogram> predictions = new HashMap<ITradeable, IndependentHistogram>();
    for (ITradeable good : goods) {
      IndependentHistogram aHist = prediction.get(good).rep;
      predictions.put(good, aHist); 
    }
    Map<ITradeable, Price> returnRep = new HashMap<ITradeable, Price>();
    // get the mean price over the whole histogram.
    for(Entry<ITradeable, IndependentHistogram> ent : predictions.entrySet()) {
      returnRep.put(ent.getKey(), new Price(ent.getValue().getMean())); 
    }
    return new PointRep(returnRep);
  }
  
  @Override
  public IndRep getPrediction(Set<ITradeable> goods) {
    Map<ITradeable, IndDist> predictions = new HashMap<ITradeable, IndDist>();
    for (ITradeable good : goods) {
      IndependentHistogram aHist = prediction.get(good).rep;
      predictions.put(good, new IndDist(aHist)); 
    }
    return new IndRep(predictions); 
  }

  public Set<ITradeable> getGoods() { 
    return this.prediction.keySet();
  }

}


