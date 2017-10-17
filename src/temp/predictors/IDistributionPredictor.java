package temp.predictors;

import temp.predictions.IDistributionPrediction;

public interface IDistributionPredictor extends IPredictor {
  
  public IDistributionPrediction getPrediction();
  
}