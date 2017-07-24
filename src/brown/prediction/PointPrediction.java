package brown.prediction;

import java.util.Map;

import brown.generatepredictions.Price;
import brown.interfaces.IPricePrediction;



public class PointPrediction implements IPricePrediction {

  private PredictionVector predictions;
  
  
  public PointPrediction() {
    this.predictions = new PredictionVector();
  }
  
  public PointPrediction(PredictionVector p) {
    this.predictions = p;
  }
  
  @Override
  public PredictionVector getPrediction() {
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
