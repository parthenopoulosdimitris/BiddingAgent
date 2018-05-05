package temp.representation;

import temp.price.JointDistKDE;

public class KDERep extends APriceRep {
  
  public final JointDistKDE rep; 
  
  public KDERep(JointDistKDE val) {
    this.rep = val;
  }
}