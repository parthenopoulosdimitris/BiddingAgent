package brown.prediction.priceprediction; 


import brown.prediction.goodprice.Price;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPriceVector;

public interface IPointPrediction extends IPricePrediction {
  
  public GoodPriceVector<Good, Price> getPrediction();
  
  public void setPrediction(GoodPriceVector<Good, Price> aPrediction);
  
}