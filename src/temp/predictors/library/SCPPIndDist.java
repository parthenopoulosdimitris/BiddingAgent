package temp.predictors.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.valuation.IValuation;
import temp.histograms.IndependentHistogram;
import temp.maximizers.IMaxDist;
import temp.predictions.library.SimpleIndPrediction;
import temp.predictors.IDistributionPredictor;
import temp.price.IndDist;

//TODO: normalize, smooth? maybe? 
/**
 * produces self-confirming price predictions as 
 * histograms over prices.
 * @author andrew
 *
 */
public class SCPPIndDist implements IDistributionPredictor {
  
  private SimpleIndPrediction initial; 
  private IMaxDist strat; 
  private Integer numGames; 
  private Integer numIterations; 
  private Double pdThresh; 
  private AdditiveValuationDistribution samplingDist; 
  private Integer SIMPLAYERS = 10; 
  
  private Double MIN = 0.0;
  private Map<ITradeable, Double> MAX;
  private Integer BINS = 10; 
  private List<ITradeable> tradeableList;
  
  public SCPPIndDist(IMaxDist strat, Integer numGoods, 
      Integer numGames, Integer numIterations, Double pdThresh, AdditiveValuationDistribution sampling) {
    this.strat = strat; 
    this.numGames = numGames; 
    this.numIterations = numIterations; 
    this.pdThresh = pdThresh; 
    this.samplingDist = sampling; 
    Map<ITradeable, IndDist> initPred = new HashMap<ITradeable, IndDist>();
    // populate an initial histogram
    // calibrate
    this.MAX = new HashMap<ITradeable, Double>(); 
    this.tradeableList = new LinkedList<ITradeable>(); 
    for (int i = 0; i < numGoods; i++) {
      tradeableList.add(new SimpleTradeable(i)); 
    }
    for (int i = 0; i < 10000; i++) { 
      IValuation sampleValuation = this.samplingDist.sample();
      for(int j = 0; j < numGoods; j++) {
        double aVal = sampleValuation.getValuation(tradeableList.get(j)); 
        if (MAX.containsKey(tradeableList.get(j))) {
          if (aVal > MAX.get(tradeableList.get(j))) {
            MAX.put(tradeableList.get(j), aVal); 
          }
        } else {
          MAX.put(tradeableList.get(j), aVal);
        }
      }
    }
    // populate histogram
    IValuation initialValuation = this.samplingDist.sample();
    for (int i = 0; i < numGoods; i++) {
      IndependentHistogram ini = new IndependentHistogram(MIN, MAX.get(tradeableList.get(i)), BINS); 
      ini.increment(initialValuation.getValuation(new SimpleTradeable(i)));
      initPred.put(new SimpleTradeable(i), new IndDist(ini));
    }
    System.out.println(initPred);
    this.initial = new SimpleIndPrediction(initPred);
  }

  public SimpleIndPrediction getPrediction() {
    SimpleIndPrediction returnPrediction = initial;
    Set<ITradeable> initGoods = returnPrediction.getGoods();
    Map<ITradeable, IndDist> returnVector = returnPrediction.getPrediction(initGoods).rep;
    Boolean withinThreshold = true; 
    int iterCount = 0; 
    for(int i = 0; i < numIterations; i++) {
      System.out.println(i);
      SimpleIndPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      Map<ITradeable, IndDist> guessMap = aGuess.getPrediction(initGoods).rep;
      Map<ITradeable, IndDist> initMap = returnPrediction.getPrediction(initGoods).rep;
      for(ITradeable t : guessMap.keySet()) {
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
      if (withinThreshold) {
        //System.out.println("GUESS: " + aGuess.getPrediction(initGoods).rep);
        return aGuess;
      }
      Double decay = (((double) numIterations) - iterCount + 1.0) / ((double) numIterations);
      for(ITradeable t : guessMap.keySet()) {
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
    System.out.println(returnVector);
    return new SimpleIndPrediction(returnVector);
  }

  private SimpleIndPrediction playSelf (Integer numPlayers,
      SimpleIndPrediction aPrediction) {
    //add datatypes
    Map<ITradeable, Double> currentHighest = new HashMap<ITradeable, Double>();
    Map<ITradeable, IndDist> guess = new HashMap<ITradeable, IndDist>(); 
    //populate guess
    Map<ITradeable, IndDist> goods = aPrediction.getPrediction(aPrediction.getGoods()).rep;
    //System.out.println("GOODS:" + goods.keySet());
    for(ITradeable t : goods.keySet()) {
      IndependentHistogram possiblePrices = 
          new IndependentHistogram(MIN, MAX.get(t), BINS);
      guess.put(t, new IndDist(possiblePrices));
      //System.out.println("C");
      currentHighest.put(t, 0.0);
    }
    //play self.
    for(int i = 0; i < numGames; i++) {
      for(int j = 0; j < numPlayers; j++) {
        // sample the distribution. 
        IValuation aVal = this.samplingDist.sample();
        // get the values of each tradeable.
        Map<ITradeable, Double> vals = new HashMap<ITradeable, Double>(); 
        for (ITradeable t : this.initial.getGoods()) {
          vals.put(t, aVal.getValuation(t)); 
        }
        // use the bidding strategy.
        // problem is with the strategy
        //System.out.println("VALS: " + vals);
        Map<ITradeable, Double> aBid = strat.getBids(vals, aPrediction);
        //System.out.println("A BID: " + aBid);
        //System.out.println("CURRENT HIGHEST: " + currentHighest);
        for(ITradeable t : aBid.keySet()) {
          if (currentHighest.get(t) < aBid.get(t))
            currentHighest.put(t, aBid.get(t));
        }
      }
     //update
      for(ITradeable t : currentHighest.keySet()) {
        IndependentHistogram corresponding = guess.get(t).rep;
        corresponding.increment(currentHighest.get(t));
        //System.out.println("CURRENT HIGHEST" + currentHighest.get(t));
        //System.out.println("CORRESPONDING: " + corresponding);
        guess.put(t, new IndDist(corresponding));
      } 
      for(ITradeable t : goods.keySet()) {
        currentHighest.put(t, 0.0);
      }
    }
    //System.out.println("PLAYING SELF: " + guess);
    
    return new SimpleIndPrediction(guess);
  } 
}
