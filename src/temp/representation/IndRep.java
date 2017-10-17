package temp.representation;

import java.util.Map;

import brown.valuable.library.Tradeable;
import temp.price.IndDist;

public class IndRep extends APriceRep {
  
  public final Map<Tradeable, IndDist> rep; 
  
  public IndRep(Map<Tradeable, IndDist> rep) {
    this.rep = rep; 
  }
}