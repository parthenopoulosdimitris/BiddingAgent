package temp.finalproject; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.agent.library.T1CombAgent;
import brown.exceptions.AgentCreationException;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import temp.histograms.KDE;
import temp.maximizers.library.FavoriteBundleBid;
import temp.maximizers.library.LocalBidComplexKDE;
import temp.predictions.library.SimpleKDEPrediction;
import temp.predictors.library.SCPPKDE;

public class FinalProjectAgent extends AbsCombinatorialProjectAgentV2 {

  private static long initialLag = 1000;
  
  private Map<Integer, Double> bundle = new HashMap<Integer, Double>();
  private double bundleValue = 0;
  private Map<Set<Integer>, Double> valuation = new HashMap<>();
  
  public FinalProjectAgent(String host, int port, String name)
      throws AgentCreationException {
    super(host, port, name);
  }

  @Override
  public Set<Integer> onBidRound() { 
    // bid for our bundle, if it's price isn't too high
    Set<Integer> biddingThisRound = new HashSet<Integer>(); 
    for(Integer i : bundle.keySet()) {
      if (bundle.get(i) > getBundlePrice(new HashSet<Integer>(i))) {
        biddingThisRound.add(i); 
      }
    }
    return biddingThisRound; 
  }

  @Override
  public void onBidResults(double[] allocations) {
    // do nothing
  }

  // TODO:
  // Everything!
  @Override
  public void onAuctionStart() { 
    // the initial bundle of goods that we're testing on. will change later. 
    Map<Set<Integer>, Double> testMap = this.queryXORs(1, 5, 0);
    Set<ITradeable> testBundle = new HashSet<ITradeable>();
    Set<Set<Integer>> testBundlePowerSet = new HashSet<Set<Integer>>();
    for (Set<Integer> thing : testMap.keySet()) {
      for (Integer i : thing) {
        testBundle.add(new SimpleTradeable(i)); 
      }
      testBundlePowerSet = powerSet(thing);
      break;
    }
    // initial KDE for SCPP
    KDE initialKernelDensity = new KDE(); 
    initialKernelDensity.addObservation(new Double[5]);
    // need a helper method to get all subsets.
    SimpleKDEPrediction initialPrediction = new SimpleKDEPrediction(testBundle, initialKernelDensity);
    // strategy for SCPP playing self.
    FavoriteBundleBid dummyAgentStrategy = new FavoriteBundleBid(this, testBundlePowerSet); 
    SCPPKDE predictionOptimizer = new SCPPKDE(dummyAgentStrategy, initialPrediction, 100, 100, this);
    
    SimpleKDEPrediction kdePred = (SimpleKDEPrediction) predictionOptimizer.getPrediction();
    //localbid.
    LocalBidComplexKDE strategyOptimizer = new LocalBidComplexKDE(null, this);
    Map<ITradeable, Double> tradeableBundle = strategyOptimizer.getBids(null, kdePred);
    Map<Integer, Double> bundle = new HashMap<Integer, Double>(); 
    for (ITradeable t : tradeableBundle.keySet()) {
      bundle.put(t.getID(), tradeableBundle.get(t));
    }
    this.bundle = bundle; 
  }
  
  public static Set<Set<Integer>> powerSet(Set<Integer> originalSet) {
    Set<Set<Integer>> sets = new HashSet<Set<Integer>>();
    if (originalSet.isEmpty()) {
        sets.add(new HashSet<Integer>());
        return sets;
    }
    List<Integer> list = new ArrayList<Integer>(originalSet);
    Integer head = list.get(0);
    Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size()));
    for (Set<Integer> set : powerSet(rest)) {
        Set<Integer> newSet = new HashSet<Integer>();
        newSet.add(head);
        newSet.addAll(set);
        sets.add(newSet);
        sets.add(set);
    }
    return sets;
}

  @Override
  public void onAuctionEnd(Set<Integer> finalBundle) {
    // reset local variables
    valuation.clear();
    bundle.clear();
    bundleValue = 0;
  }

  public static void main(String[] args) throws AgentCreationException {
    new FinalProjectAgent("localhost", 2121, "agent1");
    new T1CombAgent("localhost", 2121, "agent2");
    new T1CombAgent("localhost", 2121, "agent3");
    new T1CombAgent("localhost", 2121, "agent4");
    new T1CombAgent("localhost", 2121, "agent5");
    new T1CombAgent("localhost", 2121, "agent6");
    while (true) {}
  }
  
}