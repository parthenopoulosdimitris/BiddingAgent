package temp.maximizers.library;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.tradeable.ITradeable;
import temp.maximizers.IMaxComplexDist;
import temp.predictions.IDistributionPrediction;
import temp.representation.PointRep;

public class LocalBidComplexKDE implements IMaxComplexDist {

  private int ITERATIONS = 100; 
  private IMaxComplexDist baseStrategy; 
  AbsCombinatorialProjectAgentV2 agent; 
  
  public LocalBidComplexKDE(IMaxComplexDist strategy, AbsCombinatorialProjectAgentV2 agent) {
    this.baseStrategy = strategy; 
    this.agent = agent; 
  }
  
  @Override
  public Map<ITradeable, Double> getBids(
      Map<Set<ITradeable>, Double> valuations, IDistributionPrediction prediction) {
    // TODO Auto-generated method stub
    
    // from the prediction, get the "mean price" with KDE. 
    // then, bid by bid, improve. 
    
    Map<ITradeable, Double> init = baseStrategy.getBids(valuations, prediction);
    for (int i= 0; i < ITERATIONS; i++) {
      // sample a point prediction for prices. 
      PointRep points = (PointRep) prediction.getRandomPrediction(init.keySet());
      for (ITradeable t : init.keySet()) {
        //"it is a dominant strategy to bid the marginal value of a good."
        
        // get some goods.
        Map<Set<Integer>, Double> xors = agent.queryXORs(1000, 20, 5);
        for (Set<Integer> xor : xors.keySet()) {
          if (!xor.contains(t)) {
            xors.remove(t);
          }
        }
        int count = 0; 
        double totalVal = 0.0;
        for (Set<Integer> bundle : xors.keySet()) {
          double originalVal = xors.get(bundle);
          Set<Integer> bundleCopy = new HashSet<Integer>(bundle);
          bundleCopy.remove(t);
          double newVal = agent.queryValue(bundleCopy);
          originalVal -= newVal; 
          totalVal += originalVal; 
          count++; 
        }
        double newPrice = totalVal / (double) count;
        init.put(t, newPrice);
      }
    }
    return init;
  }
  
}