package temp.representation;

import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import temp.price.JointDist;

public class JointRep extends APriceRep{
  
  public final Map<Set<ITradeable>, JointDist> rep; 
  
  public JointRep(Map<Set<ITradeable>, JointDist> val) {
    this.rep = val;
  }
}