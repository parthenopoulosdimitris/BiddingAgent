package temp.predictions;

import java.util.Map;

import brown.valuable.library.Tradeable;

public interface IPointPrediction {
  
  public Map<Tradeable, Double> getPrediction();

}