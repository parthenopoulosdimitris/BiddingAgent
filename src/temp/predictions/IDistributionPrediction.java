package temp.predictions;


import java.util.Set;

import brown.tradeable.ITradeable;
import temp.representation.APriceRep;

public interface IDistributionPrediction extends IPricePrediction {
  
  public APriceRep getMeanPrediction(Set<ITradeable> goods);
  
  public APriceRep getRandomPrediction(Set<ITradeable> goods);
      
}