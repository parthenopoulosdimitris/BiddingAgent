package temp.predictions.library;

import java.util.Map;
import java.util.Set;

import brown.mechanism.tradeable.ITradeable;
import temp.predictions.IPointPrediction;
import temp.price.Price;
import temp.representation.PointRep;

public class SimplePointPrediction implements IPointPrediction {
  
  private Map<ITradeable, Price> prediction;
  
  public SimplePointPrediction(Map<ITradeable, Price> prediction) {
      this.prediction = prediction;  
  }
  
  @Override
  public PointRep getPrediction(Set<ITradeable> goods) {
    return new PointRep(this.prediction);
  }
  
  public Set<ITradeable> getGoods() {
    return this.prediction.keySet();
  }


  
}