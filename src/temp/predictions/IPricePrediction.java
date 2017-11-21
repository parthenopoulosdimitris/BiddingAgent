package temp.predictions;

import java.util.Set;

import brown.tradeable.library.Tradeable;
import temp.representation.APriceRep;

public interface IPricePrediction {
  
  public APriceRep getPrediction(Set<Tradeable> goods);
  
}