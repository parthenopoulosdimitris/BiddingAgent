package temp.representation;

import java.util.Map;

import brown.mechanism.tradeable.ITradeable;
import temp.price.Price;

public class PointRep extends APriceRep {
  
  public final Map<ITradeable, Price> rep; 
  
  public PointRep(Map<ITradeable, Price> rep) {
    this.rep = rep; 
  }

  @Override
  public String toString() {
    return "PointRep [rep=" + rep + "]";
  }
  
  
}