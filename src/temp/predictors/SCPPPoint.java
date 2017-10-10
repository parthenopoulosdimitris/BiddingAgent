package temp.predictors; 


import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import brown.generator.library.NormalGenerator;
import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;
import brown.valuation.library.AdditiveValuation;
import temp.MetaVal;
import temp.maximizers.IAddMaxPoint;
import temp.predictions.IPointPrediction;
import temp.predictions.SimplePointPrediction;

/*
 * implements self-confirming price predictions for point predictions. 
 */
public class SCPPPoint implements IPointPredictor {
   
  private IPointPrediction initial; 
  private Integer numGames; 
  private Integer numIterations; 
  private Double pdThresh; 
  private MetaVal metaVal;
  private Integer SIMPLAYERS = 10;
  private IAddMaxPoint strat;
  
  public SCPPPoint(IAddMaxPoint strat, IPointPrediction initial, Integer numGames, 
      Integer numIterations, Double pdThresh, MetaVal distInfo) {
    this.strat = strat; 
    this.initial = initial; 
    this.numGames = numGames;
    this.numIterations = numIterations;  
    this.pdThresh = pdThresh; 
    this.metaVal = distInfo;
  }
  
  public IPointPrediction getPrediction() {
    IPointPrediction returnPrediction = initial; 
    Map<Tradeable, Double> returnVector = returnPrediction.getPrediction();
    Boolean withinThreshold = true; 
    for(int i = 0; i < numIterations; i++) {
      IPointPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      Map<Tradeable, Double> guessVector = aGuess.getPrediction();
      for(Tradeable t : guessVector.keySet()) {
        if((guessVector.get(t) - returnVector.get(t)) > pdThresh) {
          withinThreshold = false; 
          break;
        }
      }
      if(withinThreshold)
        return new SimplePointPrediction(guessVector);
      withinThreshold = true;
      Double decay = (double) (numIterations - i + 1) / (double) numIterations;
      for(Tradeable t : returnVector.keySet()) {
        Double guessPrice = guessVector.get(t);
        Double newPrice = (decay * guessPrice) + ((1 - decay) * returnVector.get(t));
        returnVector.put(t, newPrice);
      }
    }
    return new SimplePointPrediction(returnVector);
  }
  
  private IPointPrediction playSelf(Integer simPlayers, 
      IPointPrediction aPrediction) {
    Map<Tradeable, Double> guess = new HashMap<Tradeable, Double>();
    Map<Tradeable, Double> currentHighest = new HashMap<Tradeable, Double>();
    for(int i = 0; i < numGames; i++) {
      for(Tradeable t : aPrediction.getPrediction().keySet()) {
        guess.put(t, 0.0);
        currentHighest.put(t, 0.0);
      }
      for(int j = 0; j < simPlayers; j++) {
        Set<Tradeable> b = this.initial.getPrediction().keySet();
        Map<Tradeable, Value> valuation = this.getValuation(b);
        Map<Tradeable, Double> aBid = strat.getBids(valuation, aPrediction);
      for(Tradeable t : aBid.keySet()) {
        if (currentHighest.get(t) < aBid.get(t))
          currentHighest.put(t, aBid.get(t));
      }
      }
      for(Tradeable t : currentHighest.keySet()) {
        Double other = guess.get(t);
        Double newPrice = (currentHighest.get(t) / numGames)
            + other;
        guess.put(t, newPrice);
      }
      currentHighest.clear();
    }
    IPointPrediction pointGuess = new SimplePointPrediction(guess); 
    return pointGuess; 
  }
  
  private Map<Tradeable, Value> getValuation(Set<Tradeable> goods) { 
    NormalGenerator norm = new NormalGenerator(metaVal.getValFunction(), metaVal.getScale());
    AdditiveValuation valuation = new AdditiveValuation(norm, goods);
    return valuation.getValuation(goods);
  }
}
  