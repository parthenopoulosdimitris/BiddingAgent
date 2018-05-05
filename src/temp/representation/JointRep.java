package temp.representation;

import temp.price.JointDist;

public class JointRep extends APriceRep{
  
  public final JointDist rep; 
  
  public JointRep(JointDist val) {
    this.rep = val;
  }
}