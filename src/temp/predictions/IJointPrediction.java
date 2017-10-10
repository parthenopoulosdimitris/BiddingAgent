package temp.predictions;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import temp.histograms.JointHistogram;

public interface IJointPrediction {
  
  public Map<Set<Tradeable>, Double> getMeanPrediction();
  
  public Map<Set<Tradeable>, Double> getRandomPrediction();
  
  public Map<Set<Tradeable>, JointHistogram> getDistPrediction();
}