package temp.maximizers.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.tradeable.ITradeable;
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
  }
  
  @Override
  public Map<ITradeable, Double> getBids(
      Map<Set<ITradeable>, Double> valuations,
      IDistributionPrediction prediction) {
    Set<Integer> favoriteBundle = new HashSet<Integer>(); 
    double favoriteValue = 0.0; 
    for(Set<Integer> aSet : this.eligibleGoods) {
      if (agent.queryValue(aSet) > favoriteValue) { 
        favoriteBundle = aSet; 
        favoriteValue = agent.queryValue(aSet);
      }
    }
    Map<ITradeable, Double> returnSet = new HashMap<ITradeable, Double>(); 
    int faveSize = favoriteBundle.size(); 
    for (Integer i : favoriteBundle) {
      returnSet.put(new SimpleTradeable(i), favoriteValue / (double) faveSize);
    }
    return returnSet;
  }
  
}