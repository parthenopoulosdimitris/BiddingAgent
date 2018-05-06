package temp.maximizers.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;
import temp.maximizers.IMaxComplexDist;
import temp.predictions.IDistributionPrediction;

/**
 * the strategy of the tier 1 bot, adapted slightly for a one-shot auction.
 * @author andrew
 *
 */
public class FavoriteBundleBid implements IMaxComplexDist {

  private AbsCombinatorialProjectAgentV2 agent; 
  private Set<Set<Integer>> eligibleGoods; 
  
  public FavoriteBundleBid(AbsCombinatorialProjectAgentV2 agent, Set<Set<Integer>> eligibleGoods) {
    this.agent = agent; 
    this.eligibleGoods = eligibleGoods; 
  }
  
  @Override
  public Map<ITradeable, Double> getBids(
      Map<ITradeable, Double> valuations,
      IDistributionPrediction prediction) {
    double maxValuePerItem = 0;
    double maxValue = 0; 
    Set<Integer> maxBundle = new HashSet<Integer>();
    for (Set<Integer> entry : this.eligibleGoods) {
        double numItems = (double) entry.size();
        double sample = agent.sampleValue(entry);
        if (agent.sampleValue(entry) / numItems > maxValuePerItem) {
          maxValuePerItem = sample / numItems;
          maxBundle = entry;
          maxValue = sample; 
        }
     }
    Set<ITradeable> returnSet = new HashSet<ITradeable>(); 
    for (Integer i : maxBundle) {
      returnSet.add(new SimpleTradeable(i));
    }
    Map<ITradeable, Double> returnMap = new HashMap<ITradeable, Double>(); 
    returnMap.put(new ComplexTradeable(0, returnSet), maxValue);
    return returnMap;
  }
  
  public Set<Set<Integer>> getEligible() {
    return this.eligibleGoods; 
  }
  
}