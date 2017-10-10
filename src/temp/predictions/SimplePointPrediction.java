package temp.predictions;

import java.util.Map;


import brown.valuable.library.Tradeable;

public class SimplePointPrediction implements IPointPrediction {
  
  private Map<Tradeable, Double> prediction;
  
  public SimplePointPrediction(Map<Tradeable, Double> prediction) {
      this.prediction = prediction;  
  }
  
  public Map<Tradeable, Double> getPrediction() {
    return this.prediction;
  }


  
}