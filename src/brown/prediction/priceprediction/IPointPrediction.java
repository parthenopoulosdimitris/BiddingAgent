package brown.prediction.priceprediction; 

import java.util.Map;

import brown.generatepredictions.Price;
import brown.prediction.good.Good;
import brown.prediction.good.GoodPrice;
import brown.prediction.good.GoodPriceVector;

public interface IPointPrediction extends IPricePrediction {
  
  public GoodPriceVector getPrediction();
  
  public void setPrediction(GoodPriceVector aPrediction);
  
}