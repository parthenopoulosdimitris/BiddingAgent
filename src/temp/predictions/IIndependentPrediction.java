package temp.predictions;

import java.util.Map;

import brown.valuable.library.Tradeable;
import temp.IndDist;

public interface IIndependentPrediction {
  
  public Map<Tradeable, Double> getMeanPrediction();
  
  public Map<Tradeable, Double> getRandomPrediction();
  
  public Map<Tradeable, IndDist> getDistPrediction();
  
}