package temp.representation;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import temp.price.JointDist;

public class JointRep extends APriceRep{
  
  public final Map<Set<Tradeable>, JointDist> rep; 
  
  public JointRep(Map<Set<Tradeable>, JointDist> val) {
    this.rep = val;
  }
}