package temp.representation;

import java.util.Map;
import java.util.Set;

import brown.mechanism.tradeable.ITradeable;
import temp.price.Price;

public class VecPointRep extends APriceRep {
  
  public final Map<Set<ITradeable>, Price> rep; 
  
  public VecPointRep(Map<Set<ITradeable>, Price> rep) {
    this.rep = rep; 
  }
  
}