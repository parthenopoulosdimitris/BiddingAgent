package temp.predictions.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import temp.predictions.IDistributionPrediction;
import temp.price.IndDist;
import temp.representation.IndRep;
import temp.representation.PointRep;

public class SimpleIndPrediction implements IDistributionPrediction {
  
  private Map<ITradeable, IndDist> prediction; 
  
  public SimpleIndPrediction(Map<ITradeable, IndDist> prediction) {
    this.prediction = new HashMap<ITradeable, IndDist>();
  }

  @Override
  public PointRep getMeanPrediction(Set<ITradeable> goods) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PointRep getRandomPrediction(Set<ITradeable> goods) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public IndRep getPrediction(Set<ITradeable> goods) {
    return null; 
  }

  public Set<ITradeable> getGoods() { 
    return this.prediction.keySet();
  }

}


