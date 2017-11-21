package temp.predictions.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.tradeable.library.Tradeable;
import temp.predictions.IDistributionPrediction;
import temp.price.IndDist;
import temp.representation.IndRep;
import temp.representation.PointRep;

public class SimpleIndPrediction implements IDistributionPrediction {
  
  private Map<Tradeable, IndDist> prediction; 
  
  public SimpleIndPrediction(Map<Tradeable, IndDist> prediction) {
    this.prediction = new HashMap<Tradeable, IndDist>();
  }

  @Override
  public PointRep getMeanPrediction(Set<Tradeable> goods) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PointRep getRandomPrediction(Set<Tradeable> goods) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public IndRep getPrediction(Set<Tradeable> goods) {
    return null; 
  }

  public Set<Tradeable> getGoods() { 
    return this.prediction.keySet();
  }

}


