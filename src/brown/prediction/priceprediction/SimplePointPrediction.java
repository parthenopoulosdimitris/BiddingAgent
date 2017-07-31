package brown.prediction.priceprediction;

import java.util.Map;

import brown.generatepredictions.Price;
import brown.prediction.good.GoodPrice;
import brown.prediction.good.GoodPriceVector;



public class SimplePointPrediction implements IPointPrediction {

  private GoodPriceVector predictions;
  
  
  public SimplePointPrediction() {
    this.predictions = new GoodPriceVector();
  }
  
  public SimplePointPrediction(GoodPriceVector p) {
    this.predictions = p;
  }
  
  @Override
  public GoodPriceVector getPrediction() {
    return predictions;
  }
  
  @Override
  public void setPrediction(GoodPriceVector aPrediction) {
    this.predictions = aPrediction;
  } 
  


  
}
