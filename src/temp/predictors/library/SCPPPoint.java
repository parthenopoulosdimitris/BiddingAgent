package temp.predictors.library; 


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.IBundleValuation;
import brown.auction.value.valuation.IValuation;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.ComplexTradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import temp.maximizers.IMaxPoint;
import temp.predictions.library.SimplePointPrediction;
import temp.predictors.IPointPredictor;
import temp.price.Price;

/*
 * implements self-confirming price predictions for point predictions. 
 */
public class SCPPPoint implements IPointPredictor {
   
  private SimplePointPrediction initial; 
  private Integer numGames; 
  private Integer numIterations; 
  private Double pdThresh; 
  private IValuationDistribution samplingDist;
  private Integer SIMPLAYERS = 8;
  private IMaxPoint strat;
  
  /**
   * null prediction? 
   * @param strat
   * @param initial
   * @param numGames
   * @param numIterations
   * @param pdThresh
   * @param distInfo
   */
  public SCPPPoint(IMaxPoint strat, Integer numGoods, Integer numGames, 
      Integer numIterations, Double pdThresh, IValuationDistribution sampling) {
    this.strat = strat; 
    this.numGames = numGames;
    this.numIterations = numIterations;  
    this.pdThresh = pdThresh; 
    this.samplingDist = sampling;
    Map<ITradeable, Price> initPrediction =  new HashMap<ITradeable, Price>();
    // add to the initial vector.
    for(int i = 0; i < numGoods; i++) {
      // initial prediction is random.
      initPrediction.put(new SimpleTradeable(i), new Price(Math.random()));      
    }
    this.initial = new SimplePointPrediction(initPrediction); 
  }
  
  
  public SimplePointPrediction getPrediction() {
    int index = 0; 
    SimplePointPrediction returnPrediction = initial; 
    //this can be the initial. 
    Set<ITradeable> initGoods = returnPrediction.getGoods(); 
    Map<ITradeable, Price> returnVector = returnPrediction.getPrediction(initGoods).rep;
    Boolean withinThreshold = true; 
    for(int i = 0; i < numIterations; i++) {
      SimplePointPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      Map<ITradeable, Price> guessVector = aGuess.getPrediction(initGoods).rep;
      for(ITradeable t : guessVector.keySet()) {
        if((Math.abs(guessVector.get(t).rep - returnVector.get(t).rep)) > pdThresh) {
          withinThreshold = false; 
          break;
        }
      }
      if(withinThreshold) {
        System.out.println("guess: " + guessVector);
        System.out.println("return " + returnVector);
        System.out.println("exit");
        System.out.println(i);
        return new SimplePointPrediction(guessVector);      
      }
      index++;
      withinThreshold = true;
      Double decay = (double) (numIterations - i + 1) / (double) numIterations;
      for(ITradeable t : returnVector.keySet()) {
        Double guessPrice = guessVector.get(t).rep;
        Double newPrice = (decay * guessPrice) + ((1 - decay) * returnVector.get(t).rep);
        returnVector.put(t, new Price(newPrice));
      }
    }
    //System.out.println("guess: " + guessVector);
    System.out.println("return " + returnVector);
    System.out.println("timeout");
    return new SimplePointPrediction(returnVector);
  }
  
  private SimplePointPrediction playSelf(Integer simPlayers, 
      SimplePointPrediction aPrediction) {
    Map<ITradeable, Price> guess = new HashMap<ITradeable, Price>();
    Map<ITradeable, Double> currentHighest = new HashMap<ITradeable, Double>();
    // populate the guess with low bids
    for(ITradeable t : aPrediction.getPrediction(aPrediction.getGoods()).rep.keySet()) {
      guess.put(t, new Price(0.0));
    }
    // play a number of games against self.
    for(int i = 0; i < numGames; i++) {
      // populate the currentHighest with low bids
      for(ITradeable t : aPrediction.getPrediction(aPrediction.getGoods()).rep.keySet()) {
        currentHighest.put(t, 0.0);
      }
      // submit bids for simulated players
      for(int j = 0; j < simPlayers; j++) {
        // get a valuation
        IBundleValuation val = (IBundleValuation) this.samplingDist.sample(); 
        Map<ITradeable, Double> valuations = new HashMap<ITradeable, Double>(); 
        for (ComplexTradeable t : val.getAllValuations().keySet()) {
          valuations.put(t, val.getValuation(t)); 
        }
        Map<ITradeable, Double> aBid = strat.getBids(valuations, aPrediction);
      // record highest bid.
      for(ITradeable t : aBid.keySet()) {
        if (currentHighest.get(t) < aBid.get(t))
          currentHighest.put(t, aBid.get(t));     
        }
      }
      // hmm... average over the number of games. 
      // so that'd be the average over all runs at a current moment. 
      // so you'd like to add a number to an existing avg... 
      // 
      for(ITradeable t : currentHighest.keySet()) {
        Double existing = guess.get(t).rep;
        Double newPrice = (((existing * (double) i) + currentHighest.get(t)) / ((double) (i + 1)));       
        guess.put(t, new Price(newPrice));            
      }
      currentHighest.clear();
    }
    SimplePointPrediction pointGuess = new SimplePointPrediction(guess); 
    return pointGuess; 
  }
}
  