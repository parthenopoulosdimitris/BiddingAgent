package temp.finalproject; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.agent.library.T1CombAgent;
import brown.exceptions.AgentCreationException;

public class FinalProjectAgent extends AbsCombinatorialProjectAgentV2 {

  private static long initialLag = 1000;
  
  private Set<Integer> bundle = new HashSet<>();
  private double bundleValue = 0;
  private Map<Set<Integer>, Double> valuation = new HashMap<>();
  
  public FinalProjectAgent(String host, int port, String name)
      throws AgentCreationException {
    super(host, port, name);
  }

  @Override
  public Set<Integer> onBidRound() {
    long initTime = System.currentTimeMillis();   
    // bid for our bundle, if it's price isn't too high
    if (getBundlePrice(bundle) < bundleValue) {
      return bundle;
    } else {
      return new HashSet<>();
    }
  }

  @Override
  public void onBidResults(double[] allocations) {
    // do nothing
  }

  @Override
  public void onAuctionStart() {    
    // find best bundle, relative to value per item
    Map<Set<Integer>, Double> favorite = this.getFavoriteBundle(); 
    for (Set<Integer> s : favorite.keySet()) {
      bundle = s; 
      System.out.println("BUNDLE: " + bundle);
    }
    bundleValue = queryValue(bundle);
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