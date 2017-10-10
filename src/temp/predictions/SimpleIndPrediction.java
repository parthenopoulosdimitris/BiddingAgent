package temp.predictions;

import java.util.Map;

import brown.valuable.library.Tradeable;
import temp.IndDist;

public class SimpleIndPrediction implements IIndependentPrediction {
  
  private Map<Tradeable, IndDist> prediction; 
  
  public SimpleIndPrediction(Map<Tradeable, IndDist> prediction) {
    this.prediction = prediction;
  }
  
  public Map<Tradeable, Double> getMeanPrediction() {
      return null; 
  }
  
  public Map<Tradeable, Double> getRandomPrediction() { 
    return null;
  }
  
  public Map<Tradeable, IndDist> getDistPrediction() {
     return null;
  }
}