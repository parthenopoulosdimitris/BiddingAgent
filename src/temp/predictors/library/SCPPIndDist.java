package temp.predictors.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.valuation.IValuation;
import brown.auction.value.valuation.library.AdditiveValuation;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.user.agent.library.AbsCombinatorialProjectAgentV2;
import temp.histograms.IndependentHistogram;
import temp.maximizers.IMaxDist;
import temp.predictions.library.SimpleIndPrediction;
import temp.predictors.IDistributionPredictor;
import temp.price.IndDist;

/**
 * produces self-confirming price predictions as 
 * histograms over prices.
 * @author andrew
 *
 */
public class SCPPIndDist implements IDistributionPredictor {
  
  private SimpleIndPrediction initial; 
  private IMaxDist strat; 
  private AdditiveValuationDistribution samplingDist;  
  private Integer numGoods; 
  private Map<ITradeable, Double> max;
  private List<ITradeable> tradeableList;
  private AbsCombinatorialProjectAgentV2 agent; 
  private boolean specValuation; 
  
  private Double MIN = 0.0;
  private Integer BINS = 10; 
  private Integer SIMPLAYERS = 10;
  private Double PDTHRESH = 0.05;
  private Integer NUMGAMES = 1;
  private Integer NUMITERATIONS = 1; 
  
  public SCPPIndDist(IMaxDist strat, Integer numGoods, AdditiveValuationDistribution sampling) {
    this.strat = strat; 
    this.samplingDist = sampling; 
    this.numGoods = numGoods; 
    this.specValuation = false; 
    initializePrediction();
  }
  
  public SCPPIndDist(IMaxDist strat, Integer numGoods, AbsCombinatorialProjectAgentV2 agent) {
    this.strat = strat; 
    this.numGoods = numGoods; 
    this.agent = agent; 
    this.specValuation = true; 
    initializePrediction();
  }
    
  private void initializePrediction() {
    Map<ITradeable, IndDist> initPred = new HashMap<ITradeable, IndDist>();
    // populate an initial histogram
    // calibrate
    this.max = new HashMap<ITradeable, Double>(); 
    this.tradeableList = new LinkedList<ITradeable>(); 
    for (int i = 1; i <= numGoods; i++) {
      tradeableList.add(new SimpleTradeable(i)); 
    }
    for (int i = 0; i < 1; i++) { 
      IValuation sampleValuation;
      if (!specValuation) {
        sampleValuation = this.samplingDist.sample();
      } else {
        Map<SimpleTradeable, Double> trimmedValuationMap = new HashMap<SimpleTradeable, Double>(); 
        for (int k = 1; k <= 98; k++) {
          Set<Integer> individualGood = new HashSet<Integer>(); 
          individualGood.add(k);
          trimmedValuationMap.put(new SimpleTradeable(k), agent.sampleValue(individualGood));
        }
        sampleValuation = new AdditiveValuation(trimmedValuationMap);
      }
      for(int j = 0; j < numGoods; j++) {
        System.out.println(j);
        double aVal = sampleValuation.getValuation(tradeableList.get(j)); 
        if (max.containsKey(tradeableList.get(j))) {
          if (aVal > max.get(tradeableList.get(j))) {
           max.put(tradeableList.get(j), aVal); 
          }
        } else {
          max.put(tradeableList.get(j), aVal);
        }
      }
    }
    // populate histogram
    IValuation initialValuation;
    if (!specValuation) { 
      initialValuation = this.samplingDist.sample();
    } else {
      Map<SimpleTradeable, Double> trimmedValuationMap = new HashMap<SimpleTradeable, Double>(); 
      for (int k = 1; k <= 98; k++) {
        Set<Integer> individualGood = new HashSet<Integer>(); 
        individualGood.add(k);
        trimmedValuationMap.put(new SimpleTradeable(k), agent.sampleValue(individualGood));
      }
      initialValuation = new AdditiveValuation(trimmedValuationMap);
    }
    for (int i = 1; i <= numGoods; i++) {
      IndependentHistogram ini = new IndependentHistogram(MIN, max.get(tradeableList.get(i - 1)), BINS); 
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
    for(int i = 0; i < NUMITERATIONS; i++) {
      SimpleIndPrediction aGuess = this.playSelf(returnPrediction);
      Map<ITradeable, IndDist> guessMap = aGuess.getPrediction(initGoods).rep;
      Map<ITradeable, IndDist> initMap = returnPrediction.getPrediction(initGoods).rep;
      for(ITradeable t : guessMap.keySet()) {
        Map<Integer, Integer> first = guessMap.get(t).rep.getHistogram();
        Map<Integer, Integer> second = initMap.get(t).rep.getHistogram();
        if (first.size() != second.size()) {
          System.out.println("Error: histogram size mismatch");
          return null;
        }
        for (int j = 0; j < first.size(); j++) {
          Double difference = (double) first.get(j)
              - (double) second.get(j);
          if (difference > this.PDTHRESH) {
            withinThreshold = false; 
            break;
          }
        }
      }
      if (withinThreshold) {
        return aGuess;
      }
      Double decay = (((double) NUMITERATIONS) - iterCount + 1.0) / ((double) NUMITERATIONS);
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

  private SimpleIndPrediction playSelf (SimpleIndPrediction aPrediction) {
    //add datatypes
    Map<ITradeable, Double> currentHighest = new HashMap<ITradeable, Double>();
    Map<ITradeable, IndDist> guess = new HashMap<ITradeable, IndDist>(); 
    //populate guess
    Map<ITradeable, IndDist> goods = aPrediction.getPrediction(aPrediction.getGoods()).rep;
    System.out.println(goods.keySet());
    for(ITradeable t : goods.keySet()) {
      IndependentHistogram possiblePrices = 
          new IndependentHistogram(MIN, max.get(t), BINS);
      guess.put(t, new IndDist(possiblePrices));
      currentHighest.put(t, 0.0);
    }
    //play self.
    for(int i = 0; i < NUMGAMES; i++) {
      for(int j = 0; j < SIMPLAYERS; j++) {
        // sample the distribution. 
        IValuation aVal; 
        if (!this.specValuation) {
          aVal = this.samplingDist.sample();
        } else { 
          Map<SimpleTradeable, Double> trimmedValuationMap = new HashMap<SimpleTradeable, Double>(); 
          for (int k = 1; k <= 98; k++) {
            Set<Integer> individualGood = new HashSet<Integer>(); 
            individualGood.add(k);
            trimmedValuationMap.put(new SimpleTradeable(k), agent.sampleValue(individualGood));
          }
          aVal = new AdditiveValuation(trimmedValuationMap);
        }
        // get the values of each tradeable.
        Map<ITradeable, Double> vals = new HashMap<ITradeable, Double>(); 
        for (ITradeable t : this.initial.getGoods()) {
          vals.put(t, aVal.getValuation(t)); 
        }
        Map<ITradeable, Double> aBid = strat.getBids(vals, aPrediction);
        for(ITradeable t : aBid.keySet()) {
          if (currentHighest.get(t) < aBid.get(t))
            currentHighest.put(t, aBid.get(t));
        }
      }
     //update
      for(ITradeable t : currentHighest.keySet()) {
        IndependentHistogram corresponding = guess.get(t).rep;
        corresponding.increment(currentHighest.get(t));
        guess.put(t, new IndDist(corresponding));
      } 
      for(ITradeable t : goods.keySet()) {
        currentHighest.put(t, 0.0);
      }
    }
    return new SimpleIndPrediction(guess);
  } 
}
