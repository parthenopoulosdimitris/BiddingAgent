package temp.predictions.library;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import temp.predictions.IDistributionPrediction;
import temp.price.JointDist;
import temp.representation.APriceRep;
import temp.representation.JointRep;
import temp.representation.PointRep;

public class SimpleJointPrediction implements IDistributionPrediction {
   
  private Map<Set<Tradeable>, JointDist> prediction; 
  
  public SimpleJointPrediction(Map<Set<Tradeable>, JointDist> prediction){
    this.prediction = prediction;
  }

  @Override
  public JointRep getPrediction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PointRep getMeanPrediction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PointRep getRandomPrediction() {
    // TODO Auto-generated method stub
    return null;
  }
}