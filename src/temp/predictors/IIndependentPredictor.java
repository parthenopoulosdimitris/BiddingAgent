package temp.predictors;

import temp.predictions.IIndependentPrediction;


public interface IIndependentPredictor extends IDistributionPredictor {
 
  
  public IIndependentPrediction getPrediction();
}