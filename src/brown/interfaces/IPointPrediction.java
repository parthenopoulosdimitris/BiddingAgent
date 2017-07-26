package brown.interfaces; 

import java.util.Map;

import brown.generatepredictions.Price;
import brown.prediction.Good;
import brown.prediction.GoodPrice;
import brown.prediction.GoodPriceVector;

public interface IPointPrediction extends IPricePrediction {
  
  public GoodPriceVector getPrediction();
  
  public void setPrediction(GoodPrice aPrediction);
  
}