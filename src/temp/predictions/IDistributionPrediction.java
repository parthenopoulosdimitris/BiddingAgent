package temp.predictions;


import java.util.Set;

import brown.tradeable.library.Tradeable;
import temp.representation.APriceRep;

public interface IDistributionPrediction extends IPricePrediction {
  
  public APriceRep getMeanPrediction(Set<Tradeable> goods);
  
  public APriceRep getRandomPrediction(Set<Tradeable> goods);
      
}