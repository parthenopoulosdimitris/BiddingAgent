package temp.predictors;

import temp.predictions.IJointPrediction;

public interface IJointPredictor extends IDistributionPredictor {
  
  public IJointPrediction getPrediction();
  
}