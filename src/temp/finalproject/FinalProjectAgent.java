package temp.finalproject; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.agent.library.T1CombAgent;
import brown.exceptions.AgentCreationException;
import brown.tradeable.ITradeable;
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

  @Override
  public void onAuctionStart() {    
    SCPPKDE predictionOptimizer = new SCPPKDE(null, null, 100, 100, this);
    // so assume that this is in fact a good price prediction. 
    // what now? 
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