package temp.predictions.library;

import java.util.Set;

import brown.tradeable.ITradeable;
import temp.predictions.IDistributionPrediction;
import temp.price.JointDist;
import temp.representation.JointRep;
import temp.representation.VecPointRep;

public class SimpleJointPrediction implements IDistributionPrediction {
   
  private Set<ITradeable> tradeables; 
  JointDist prediction; 
  
  public SimpleJointPrediction(Set<ITradeable> tradeables, JointDist prediction) {
    this.prediction = prediction;
    this.tradeables = tradeables; 
  }

  @Override
  public JointRep getPrediction(Set<ITradeable> goods) {
    // TODO Auto-generated method stub
    return new JointRep(this.prediction);
  }

  @Override
  public VecPointRep getMeanPrediction(Set<ITradeable> goods) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public VecPointRep getRandomPrediction(Set<ITradeable> goods) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Set<ITradeable> getGoods() {
    return this.tradeables; 
  }
  
  
}