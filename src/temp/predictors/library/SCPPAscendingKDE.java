package temp.predictors.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.border.EtchedBorder;

import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.ComplexTradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.user.agent.library.AbsCombinatorialProjectAgentV2;
import temp.histograms.KDE;
import temp.maximizers.IMaxComplexDist;
import temp.predictions.IDistributionPrediction;
import temp.predictions.library.SimpleKDEPrediction;
import temp.predictors.IDistributionPredictor;

public class SCPPAscendingKDE implements IDistributionPredictor {

  private SimpleKDEPrediction initial; 
  private IMaxComplexDist strat; 
  private Integer numGames; 
  private Integer numIterations; 
  private AbsCombinatorialProjectAgentV2 agent;  
  private Integer SIMPLAYERS = 2; 
  private Integer SIZE = 2; 
  private Double EPSILON = 1.0; 
  private Set<Set<Integer>> workingTradeables; 
  private Set<ITradeable> actualTradeables; 
  
  /**
   * 
   * @param strat the strategy that agents will be using to place bids.
   * @param initial
   * @param numGames
   * @param numIterations
   * @param agent
   */
  public SCPPAscendingKDE(IMaxComplexDist strat, SimpleKDEPrediction initial, 
      Integer numGames, Integer numIterations, AbsCombinatorialProjectAgentV2 agent,
      Set<Set<Integer>> workingTradeables, Set<ITradeable> actualTradeables) {
    this.strat = strat; 
    this.initial = initial; 
    this.numGames = numGames; 
    this.numIterations = numIterations; 
    this.agent = agent; 
    this.workingTradeables = workingTradeables; 
    this.actualTradeables = actualTradeables;
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
    for (int i = 0; i < numGames; i++) { 
      List<Map<ITradeable, Double>> allBids = new LinkedList<Map<ITradeable, Double>>();
      for(int k = 0; k < simPlayers; k++) {
        Map<Set<Integer>, Double> simValuation = new HashMap<Set<Integer>, Double>(); 
        for (Set<Integer> t : workingTradeables) {
          double aVal = agent.sampleValue(t);
          simValuation.put(t, aVal);
        }
        System.out.println(simValuation);
        Map<ITradeable, Double> conversion =  new HashMap<ITradeable, Double>(); 
        for(Set<Integer> s : simValuation.keySet()) {
          Set<ITradeable> ts = new HashSet<ITradeable>(); 
          for (Integer a : s) {
            ITradeable t = new SimpleTradeable(a);
            ts.add(t);
          }
          conversion.put(new ComplexTradeable(0, ts), simValuation.get(s));
        }
        allBids.add(strat.getBids(conversion, aPrediction));
      }
      System.out.println(allBids);
      // run the auction.
      Map<ITradeable, Double> priceMap = new HashMap<ITradeable, Double>(); 
      for (ITradeable t : this.actualTradeables) { 
        priceMap.put(t, 0.0);
      }
      // while peoples bid sets are not empty
      while(!allBids.isEmpty()) {
        List<ITradeable> toIncrement = new LinkedList<ITradeable>(); 
        List<Map<ITradeable, Double>> toRemove = new LinkedList<Map<ITradeable, Double>>();
        for(Map<ITradeable, Double> pricing : allBids) {
          // one good
          for(ITradeable t : pricing.keySet()) {
            double goodPrice = 0.0; 
            for(ITradeable ft : t.flatten()) {
              goodPrice += priceMap.get(ft);
              toIncrement.add(ft);
            }
            if (goodPrice > pricing.get(t)) {
              toRemove.add(pricing);
            }
          }
        }
        allBids.removeAll(toRemove);
        if (!allBids.isEmpty()) {
          for(ITradeable t : toIncrement) { 
            priceMap.put(t, priceMap.get(t) + this.EPSILON); 
          }
        }
        toIncrement.clear(); 
      }
      Double[] prices = new Double[this.SIZE];
      for (ITradeable t : priceMap.keySet()) {
        prices[t.getID() - 1] = priceMap.get(t);
      }
      allWins.add(prices);
      for (int j = 0; j < this.SIZE; j++) {
        System.out.println(prices[j]);
      }
      
      System.out.println("one iteration");
      System.out.println(i);
    }
    KDE kdePrediction = new KDE(allWins);
    //TODO: get all the treadeables.
    
    return new SimpleKDEPrediction(this.actualTradeables, kdePrediction);
  }
  
}