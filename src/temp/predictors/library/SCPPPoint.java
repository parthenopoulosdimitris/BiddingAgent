package temp.predictors.library; 


import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;
import brown.valuation.library.AdditiveValuation;
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
  private AdditiveValuation metaVal;
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
      Integer numIterations, Double pdThresh, AdditiveValuation distInfo) {
    this.strat = strat; 
    this.numGames = numGames;
    this.numIterations = numIterations;  
    this.pdThresh = pdThresh; 
    this.metaVal = distInfo;
    Map<Tradeable, Price> initPrediction =  new HashMap<Tradeable, Price>();
    for(int i = 0; i < numGoods; i++)
      initPrediction.put(new Tradeable(i), new Price(0.0));
    this.initial = new SimplePointPrediction(initPrediction); 
  }
  
  public SimplePointPrediction getPrediction() {
    SimplePointPrediction returnPrediction = initial; 
    //this can be the initial. 
    Map<Tradeable, Price> returnVector = returnPrediction.getPrediction().rep;
    Boolean withinThreshold = true; 
    for(int i = 0; i < numIterations; i++) {
      SimplePointPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      Map<Tradeable, Price> guessVector = aGuess.getPrediction().rep;
      for(Tradeable t : guessVector.keySet()) {
        if((guessVector.get(t).rep - returnVector.get(t).rep) > pdThresh) {
          withinThreshold = false; 
          break;
        }
      }
      if(withinThreshold)
        return new SimplePointPrediction(guessVector);
      withinThreshold = true;
      Double decay = (double) (numIterations - i + 1) / (double) numIterations;
      for(Tradeable t : returnVector.keySet()) {
        Double guessPrice = guessVector.get(t).rep;
        Double newPrice = (decay * guessPrice) + ((1 - decay) * returnVector.get(t).rep);
        returnVector.put(t, new Price(newPrice));
      }
    }
    return new SimplePointPrediction(returnVector);
  }
  
  private SimplePointPrediction playSelf(Integer simPlayers, 
      SimplePointPrediction aPrediction) {
    Map<Tradeable, Price> guess = new HashMap<Tradeable, Price>();
    Map<Tradeable, Double> currentHighest = new HashMap<Tradeable, Double>();
    for(int i = 0; i < numGames; i++) {
      for(Tradeable t : aPrediction.getPrediction().rep.keySet()) {
        guess.put(t, new Price(0.0));
        currentHighest.put(t, 0.0);
      }
      for(int j = 0; j < simPlayers; j++) {
        Set<Tradeable> b = this.initial.getPrediction().rep.keySet();
        Map<Tradeable, Value> valuation = this.metaVal.getValuation(b).vals;
        Map<Tradeable, Double> aBid = strat.getBids(valuation, aPrediction);
      for(Tradeable t : aBid.keySet()) {
        if (currentHighest.get(t) < aBid.get(t))
          currentHighest.put(t, aBid.get(t));
      }
      }
      for(Tradeable t : currentHighest.keySet()) {
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
  