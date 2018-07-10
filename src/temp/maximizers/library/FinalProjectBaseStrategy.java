package temp.maximizers.library;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.mechanism.tradeable.ITradeable;
import temp.maximizers.IMaxComplexDist;
import temp.predictions.IDistributionPrediction;
import temp.price.Price;
import temp.representation.PointRep;

public class FinalProjectBaseStrategy implements IMaxComplexDist {

  private Set<ITradeable> allTradeables; 
  
  public FinalProjectBaseStrategy(Set<ITradeable> allTradeables) {
    this.allTradeables = allTradeables; 
  }
  
  @Override
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IDistributionPrediction prediction) {
    // what do I want to do... 
    // maybe bid the marginal value of the good. 
    
    PointRep points = (PointRep) prediction.getRandomPrediction(allTradeables);
    //given this, maximize our value. 
    Map<ITradeable, Price> rep =  points.rep; 
    for (ITradeable t : valuations.keySet()) {
      Set<ITradeable> tSet = new HashSet<ITradeable>(t.flatten()); 
      
    }
    
    return null;
  }
  
}