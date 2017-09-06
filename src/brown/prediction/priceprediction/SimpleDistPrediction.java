package brown.prediction.priceprediction;


import brown.prediction.goodprice.Dist;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPriceVector;




public class SimpleDistPrediction implements IIndependentPrediction {

  private GoodPriceVector<Good, Dist> predictions;
  
  
  public SimpleDistPrediction() {
    this.predictions = new GoodPriceVector<Good, Dist>();
  }
  
  public SimpleDistPrediction(GoodPriceVector<Good, Dist> p) {
    this.predictions = p;
  }
  
  @Override
  public GoodPriceVector<Good, Dist> getPrediction() {
    return predictions;
  }
  
  @Override
  public void setPrediction(GoodPriceVector<Good, Dist> aPrediction) {
    this.predictions = aPrediction;
  }

}
