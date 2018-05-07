package temp.maximizers.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;
import temp.maximizers.IMaxComplexDist;
import temp.predictions.IDistributionPrediction;
import temp.representation.PointRep;

public class LocalBidComplexKDE implements IMaxComplexDist {

  private int ITERATIONS = 100; 
  private IMaxComplexDist baseStrategy; 
  AbsCombinatorialProjectAgentV2 agent; 
  private Set<Set<Integer>> allSets; 
  
  public LocalBidComplexKDE(IMaxComplexDist strategy, AbsCombinatorialProjectAgentV2 agent, Set<Set<Integer>> allSets) {
    this.baseStrategy = strategy; 
    this.agent = agent; 
    this.allSets = allSets; 
  }
  
  @Override
  public Map<ITradeable, Double> getBids(
      Map<ITradeable, Double> valuations, IDistributionPrediction prediction) {
    
    // from the prediction, get the "mean price" with KDE. 
    // then, bid by bid, improve. 
    
    Map<ITradeable, Double> vals = new HashMap<ITradeable, Double>(); 
    for (Set<Integer> ints : this.allSets) {
      double val = agent.queryValue(ints); 
      Set<ITradeable> complex = new HashSet<ITradeable>(); 
      for(Integer anId : ints) {
        ITradeable s = new SimpleTradeable(anId); 
        complex.add(s);
      }
      ITradeable c = new ComplexTradeable(0, complex);
      vals.put(c, val);
    }
    Map<ITradeable, Double> init = baseStrategy.getBids(vals, prediction);
    for (int i= 0; i < ITERATIONS; i++) {
      // sample a point prediction for prices. 
      PointRep points = (PointRep) prediction.getRandomPrediction(init.keySet());
      for (ITradeable t : init.keySet()) {
        //"it is a dominant strategy to bid the marginal value of a good."
        // 1. find the value of bidding this at the current price prediction
        // 2. find the value of bidding this without good p at the current price prediction
        // 3. take the difference of these. 
        // 4. take the expectation of this, somehow. 
        double marginalValue = 0.0; 
        for (Set<Integer> intSet : this.allSets) {
          if (intSet.contains(t.getID())) {
            // now, we have a set that contains it. Get the value with and without. 
            double withValue = agent.queryValue(intSet);
            double withPrice = 0.0; 
            double withoutPrice = 0.0; 
            for (Integer aGood : intSet) {
              withPrice += points.rep.get(new SimpleTradeable(aGood)).rep;
              if (aGood != t.getID()) {
                withoutPrice += points.rep.get(new SimpleTradeable(aGood)).rep;
              }
            }
            Set<Integer> intSetCopy = new HashSet<Integer>(intSet); 
            intSetCopy.remove(t); 
            double withoutValue = agent.queryValue(intSetCopy); 
            marginalValue = (withValue - withPrice) - (withoutValue - withoutPrice);
          }
        }
        init.put(t, marginalValue); 
      }
    }
    return init;
  }
  
}