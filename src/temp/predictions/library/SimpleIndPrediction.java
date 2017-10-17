package temp.predictions.library;

import java.util.HashMap;
import java.util.Map;

import brown.valuable.library.Tradeable;
import temp.predictions.IDistributionPrediction;
import temp.price.IndDist;
import temp.price.Price;
import temp.representation.APriceRep;
import temp.representation.IndRep;
import temp.representation.PointRep;

public class SimpleIndPrediction implements IDistributionPrediction {
  
  private Map<Tradeable, IndDist> prediction; 
  
  public SimpleIndPrediction(Map<Tradeable, IndDist> prediction) {
    this.prediction = new HashMap<Tradeable, IndDist>();
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
  
  @Override
  public IndRep getPrediction() {
    return null; 
  }


}


