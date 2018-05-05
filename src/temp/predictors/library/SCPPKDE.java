package temp.predictors.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import temp.histograms.KDE;
import temp.maximizers.IMaxComplexDist;
import temp.predictions.IDistributionPrediction;
import temp.predictions.library.SimpleKDEPrediction;
import temp.predictors.IDistributionPredictor;

public class SCPPKDE implements IDistributionPredictor {

  private SimpleKDEPrediction initial; 
  private IMaxComplexDist strat; 
  private Integer numGames; 
  private Integer numIterations; 
  private AbsCombinatorialProjectAgentV2 agent;  
  private Integer SIMPLAYERS = 10; 
  private Integer SIZE = 5; 
  
  /**
   * 
   * @param strat the strategy that agents will be using to place bids.
   * @param initial
   * @param numGames
   * @param numIterations
   * @param agent
   */
  public SCPPKDE(IMaxComplexDist strat, SimpleKDEPrediction initial, 
      Integer numGames, Integer numIterations, AbsCombinatorialProjectAgentV2 agent) {
    this.strat = strat; 
    this.initial = initial; 
    this.numGames = numGames; 
    this.numIterations = numIterations; 
    this.agent = agent; 
  }
  
  @Override
  public IDistributionPrediction getPrediction() {
    SimpleKDEPrediction returnPrediction = initial; 
    Set<ITradeable> initGoods = returnPrediction.getGoods();
    for (int i = 0; i < numIterations; i++) {
      SimpleKDEPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      KDE aKDE = aGuess.getPrediction(initGoods).rep.rep;
      KDE otherKDE = returnPrediction.getPrediction(initGoods).rep.rep; 
      for (Double[] d : aKDE.getObservations())
        otherKDE.addObservation(d);
     returnPrediction =  new SimpleKDEPrediction(initGoods, otherKDE);
    }
    return returnPrediction;
  }
  
  private SimpleKDEPrediction playSelf(int simPlayers, SimpleKDEPrediction aPrediction) {
  //add datatypes
    List<Double[]> allWins = new ArrayList<Double[]>(); 
    Map<ITradeable, Double> currentHighest = new HashMap<ITradeable, Double>();
    for (int i = 0; i < numGames; i++) { 
      for(int k = 0; k < simPlayers; k++) {
        Map<Set<Integer>, Double> simValuation = new HashMap<Set<Integer>, Double>(); 
        //what are we trying to predict, exactly? 
        //may need to change this to affect the scope of the prediction.
        Set<Set<Integer>> bids = this.agent.queryXORs(1000, this.SIZE, 0).keySet();
        for (Set<Integer> bid : bids) {
          double someValue = this.agent.sampleValue(bid);
          simValuation.put(bid, someValue);
        }
        Map<Set<ITradeable>, Double> conversion=  new HashMap<Set<ITradeable>, Double>(); 
        for(Set<Integer> s : simValuation.keySet()) {
          Set<ITradeable> ts = new HashSet<ITradeable>(); 
          for (Integer a : s) {
            ITradeable t = new SimpleTradeable(a);
            ts.add(t);
          }
          conversion.put(ts, simValuation.get(s));
        }
        // assumption: ascending auction produces a result near that of a one-shot
        Map<ITradeable, Double> aBid = strat.getBids(conversion, aPrediction);
        for (ITradeable t : aBid.keySet()) {
          if (currentHighest.get(t) < aBid.get(t))
            currentHighest.put(t, aBid.get(t));
        }
      }
      Double[] prices = new Double[this.SIZE];
      for (ITradeable t : currentHighest.keySet()) {
        prices[t.getID()] = currentHighest.get(t);
      }
      allWins.add(prices);
      currentHighest.clear(); 
    }
    KDE kdePrediction = new KDE(allWins);
    return new SimpleKDEPrediction(currentHighest.keySet(), kdePrediction);
  }
  
}