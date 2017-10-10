package temp.predictors;

import temp.predictions.IPointPrediction;

public interface IPointPredictor extends IPredictor {
  
  public IPointPrediction getPrediction();
  
}