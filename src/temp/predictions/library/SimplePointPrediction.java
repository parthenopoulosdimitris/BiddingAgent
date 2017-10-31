package temp.predictions.library;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import temp.predictions.IPointPrediction;
import temp.price.Price;
import temp.representation.PointRep;

public class SimplePointPrediction implements IPointPrediction {
  
  private Map<Tradeable, Price> prediction;
  
  public SimplePointPrediction(Map<Tradeable, Price> prediction) {
      this.prediction = prediction;  
  }
  
  @Override
  public PointRep getPrediction(Set<Tradeable> goods) {
    return new PointRep(this.prediction);
  }
  
  public Set<Tradeable> getGoods() {
    return this.prediction.keySet();
  }


  
}