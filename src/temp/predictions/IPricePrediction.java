package temp.predictions;

import java.util.Set;

import brown.tradeable.ITradeable;
import temp.representation.APriceRep;

public interface IPricePrediction {
  
  public APriceRep getPrediction(Set<ITradeable> goods);
  
}