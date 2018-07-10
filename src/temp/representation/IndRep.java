package temp.representation;

import java.util.Map;

import brown.mechanism.tradeable.ITradeable;
import temp.price.IndDist;

public class IndRep extends APriceRep {
  
  public final Map<ITradeable, IndDist> rep; 
  
  public IndRep(Map<ITradeable, IndDist> rep) {
    this.rep = rep; 
  }
}