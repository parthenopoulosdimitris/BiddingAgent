package brown.prediction;

import java.util.Map;

import brown.generatepredictions.Price;
import brown.interfaces.IPointPrediction;



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
  public void setPrediction(GoodPrice aPrediction) {
    this.predictions.add(aPrediction);
  }


  
}
