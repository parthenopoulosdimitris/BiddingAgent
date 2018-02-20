package temp.predictors.library; 


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.valuation.IValuation;
import brown.value.valuation.library.AdditiveValuation;
import temp.maximizers.IMaxPoint;
import temp.predictions.library.SimpleIndPrediction;
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
  private AdditiveValuationDistribution samplingDist;
  private Integer SIMPLAYERS = 10;
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
      Integer numIterations, Double pdThresh, AdditiveValuationDistribution sampling) {
    this.strat = strat; 
    this.numGames = numGames;
    this.numIterations = numIterations;  
    this.pdThresh = pdThresh; 
    this.samplingDist = sampling;
    Map<ITradeable, Price> initPrediction =  new HashMap<ITradeable, Price>();
    for(int i = 0; i < numGoods; i++)
      initPrediction.put(new SimpleTradeable(i), new Price(0.0));
    this.initial = new SimplePointPrediction(initPrediction); 
  }
  
  
  public SimplePointPrediction getPrediction() {
    SimplePointPrediction returnPrediction = initial; 
    //this can be the initial. 
    Set<ITradeable> initGoods = returnPrediction.getGoods(); 
    Map<ITradeable, Price> returnVector = returnPrediction.getPrediction(initGoods).rep;
    Boolean withinThreshold = true; 
    for(int i = 0; i < numIterations; i++) {
      SimplePointPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      Map<ITradeable, Price> guessVector = aGuess.getPrediction(initGoods).rep;
      for(ITradeable t : guessVector.keySet()) {
        if((guessVector.get(t).rep - returnVector.get(t).rep) > pdThresh) {
          withinThreshold = false; 
          break;
        }
      }
      if(withinThreshold)
        return new SimplePointPrediction(guessVector);
      withinThreshold = true;
      Double decay = (double) (numIterations - i + 1) / (double) numIterations;
      for(ITradeable t : returnVector.keySet()) {
        Double guessPrice = guessVector.get(t).rep;
        Double newPrice = (decay * guessPrice) + ((1 - decay) * returnVector.get(t).rep);
        returnVector.put(t, new Price(newPrice));
      }
    }
    return new SimplePointPrediction(returnVector);
  }
  
  private SimplePointPrediction playSelf(Integer simPlayers, 
      SimplePointPrediction aPrediction) {
    Map<ITradeable, Price> guess = new HashMap<ITradeable, Price>();
    Map<ITradeable, Double> currentHighest = new HashMap<ITradeable, Double>();
    for(int i = 0; i < numGames; i++) {
      for(ITradeable t : aPrediction.getPrediction(aPrediction.getGoods()).rep.keySet()) {
        guess.put(t, new Price(0.0));
        currentHighest.put(t, 0.0);
      }
      for(int j = 0; j < simPlayers; j++) {
        Set<ITradeable> b = this.initial.getPrediction(aPrediction.getGoods()).rep.keySet();
        IValuation val = this.samplingDist.sample(); 
        Map<ITradeable, Double> valuation = new HashMap<ITradeable, Double>();
        for (ITradeable t : this.initial.getGoods()) {
          valuation.put(t, val.getValuation(t)); 
        }
        Map<ITradeable, Double> aBid = strat.getBids(valuation, aPrediction);
      for(ITradeable t : aBid.keySet()) {
        if (currentHighest.get(t) < aBid.get(t))
          currentHighest.put(t, aBid.get(t));
      }
      }
      for(ITradeable t : currentHighest.keySet()) {
        Double other = guess.get(t).rep;
        Double newPrice = (currentHighest.get(t) / numGames)
            + other;
        guess.put(t, new Price(newPrice));
      }
      currentHighest.clear();
    }
    SimplePointPrediction pointGuess = new SimplePointPrediction(guess); 
    return pointGuess; 
  }
}
  