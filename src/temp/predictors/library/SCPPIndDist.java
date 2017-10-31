package temp.predictors.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;
import brown.valuation.library.AdditiveValuation;
import temp.histograms.IndependentHistogram;
import temp.maximizers.IMaxDist;
import temp.predictions.library.SimpleIndPrediction;
import temp.predictors.IDistributionPredictor;
import temp.price.IndDist;

//TODO: normalize, smooth? put the generator in the metaval, change the MAX thing? (slash scale?)
public class SCPPIndDist implements IDistributionPredictor {
  
  private SimpleIndPrediction initial; 
  private IMaxDist strat; 
  private Integer numGames; 
  private Integer numIterations; 
  private Double pdThresh; 
  private AdditiveValuation distInfo; 
  private Integer SIMPLAYERS = 10; 
  
  private Double MIN = 0.0;
  private Double MAX = 5.0;
  private Integer BINS = 100; 
  
  public SCPPIndDist(IMaxDist strat, SimpleIndPrediction initial,
      Integer numGames, Integer numIterations, Double pdThresh, AdditiveValuation distInfo) {
    this.strat = strat; 
    this.initial = initial;
    this.numGames = numGames; 
    this.numIterations = numIterations; 
    this.pdThresh = pdThresh; 
    this.distInfo = distInfo; 
  }
  
  public SimpleIndPrediction getPrediction() {
    SimpleIndPrediction returnPrediction = initial;
    Set<Tradeable> initGoods = returnPrediction.getGoods();
    Map<Tradeable, IndDist> returnVector = returnPrediction.getPrediction(initGoods).rep;
    Boolean withinThreshold = true; 
    int iterCount = 0; 
    for(int i = 0; i < numIterations; i++) {
      SimpleIndPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      Map<Tradeable, IndDist> guessMap = aGuess.getPrediction(initGoods).rep;
      Map<Tradeable, IndDist> initMap = returnPrediction.getPrediction(initGoods).rep;
      for(Tradeable t : guessMap.keySet()) {
        Map<Integer, Integer> first = guessMap.get(t).rep.getHistogram();
        Map<Integer, Integer> second = initMap.get(t).rep.getHistogram();
        if (first.size() != second.size()) {
          System.out.println("Error: histogram size mismatch");
          return null;
        }
        //TODO: normalize
        for (int j = 0; j < first.size(); j++) {
          Double difference = (double) first.get(j)
              - (double) second.get(j);
          if (difference > this.pdThresh) {
            withinThreshold = false; 
            break;
          }
        }
      }
      if (withinThreshold)
        return aGuess;
      Double decay = (((double) numIterations) - iterCount + 1.0) / ((double) numIterations);
      for(Tradeable t : guessMap.keySet()) {
        IndependentHistogram init = initMap.get(t).rep;
        IndependentHistogram guess = guessMap.get(t).rep;
        IndependentHistogram returnHist = new IndependentHistogram(
            init.getMin(), init.getMax(), init.getHistogram().size());
        for(Integer bin : init.getHistogram().keySet()) {
          double newFreq =  (decay * (double) init.getHistogram().get(bin))
              + ((1.0 - decay) * (double) guess.getHistogram().get(bin));
          returnHist.setBin(bin, (int) newFreq);
        }
        returnVector.put(t, new IndDist(returnHist));
      }
      iterCount++;
    }
    return new SimpleIndPrediction(returnVector);
  }

  private SimpleIndPrediction playSelf (Integer numPlayers,
      SimpleIndPrediction aPrediction) {
    //add datatypes
    Map<Tradeable, Double> currentHighest = new HashMap<Tradeable, Double>();
    Map<Tradeable, IndDist> guess = new HashMap<Tradeable, IndDist>(); 
    //populate guess
    Map<Tradeable, IndDist> goods = aPrediction.getPrediction(aPrediction.getGoods()).rep;
    for(Tradeable t : goods.keySet()) {
      IndependentHistogram possiblePrices = 
          new IndependentHistogram(MIN, MAX, BINS);
      guess.put(t, new IndDist(possiblePrices));
      currentHighest.put(t, 0.0);
    }
    //play self.
    for(int i = 0; i < numGames; i++) {
      for(int j = 0; j < numPlayers; j++) {
        Map<Tradeable, Value> aVal = this.distInfo.getValuation(goods.keySet()).vals;
        //this is localbid?
        Map<Tradeable, Double> aBid = strat.getBids(aVal, aPrediction);
        for(Tradeable t : aBid.keySet()) {
          if (currentHighest.get(t) < aBid.get(t));
            currentHighest.put(t, aBid.get(t));
        }
      }
     //update
      for(Tradeable t : currentHighest.keySet()) {
        IndependentHistogram corresponding = guess.get(t).rep;
        corresponding.increment(currentHighest.get(t));
        guess.put(t, new IndDist(corresponding));
      } 
      currentHighest.clear();
    }
    return new SimpleIndPrediction(guess);
  } 
}
