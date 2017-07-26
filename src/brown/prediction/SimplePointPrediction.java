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

  @Override
  public Map<Good, Price> getMeanPricePrediction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<Good, Price> getRandomPricePrediction() {
    // TODO Auto-generated method stub
    return null;
  }

  
}
