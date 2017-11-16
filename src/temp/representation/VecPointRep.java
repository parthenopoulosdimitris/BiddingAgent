package temp.representation;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import temp.price.Price;

public class VecPointRep extends APriceRep {
  
  public final Map<Set<Tradeable>, Price> rep; 
  
  public VecPointRep(Map<Set<Tradeable>, Price> rep) {
    this.rep = rep; 
  }
  
}