package temp.predictions.library;

import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import temp.predictions.IDistributionPrediction;
import temp.price.JointDist;
import temp.representation.JointRep;
import temp.representation.VecPointRep;

public class SimpleJointPrediction implements IDistributionPrediction {
   
  private Map<Set<ITradeable>, JointDist> prediction; 
  
  public SimpleJointPrediction(Map<Set<ITradeable>, JointDist> prediction) {
    this.prediction = prediction;
  }

  @Override
  public JointRep getPrediction(Set<ITradeable> goods) {
    // TODO Auto-generated method stub
    return null;
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
}