package brown.prediction.priceprediction;


import brown.prediction.goodprice.Price;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPriceVector;



public class SimplePointPrediction implements IPointPrediction {

  private GoodPriceVector<Good, Price> predictions;
  
  
  public SimplePointPrediction() {
    this.predictions = new GoodPriceVector<Good, Price>();
  }
  
  public SimplePointPrediction(GoodPriceVector<Good, Price> p) {
    this.predictions = p;
  }
  
  @Override
  public GoodPriceVector<Good, Price> getPrediction() {
    return predictions;
  }
  
  @Override
  public void setPrediction(GoodPriceVector<Good, Price> aPrediction) {
    this.predictions = aPrediction;
  }

  


  
}
