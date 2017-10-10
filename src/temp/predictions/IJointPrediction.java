package temp.predictions;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import temp.JointDist;

public interface IJointPrediction {
  
  //should this be a double, or an array of doubles?
  public Map<Set<Tradeable>, Double> getMeanPrediction();
  
  public Map<Set<Tradeable>, Double> getRandomPrediction();
  
  public Map<Set<Tradeable>, JointDist> getDistPrediction();
}