package temp.representation;

import java.util.Map;

import brown.tradeable.library.Tradeable;
import temp.price.Price;

public class PointRep extends APriceRep {
  
  public final Map<Tradeable, Price> rep; 
  
  public PointRep(Map<Tradeable, Price> rep) {
    this.rep = rep; 
  }
}