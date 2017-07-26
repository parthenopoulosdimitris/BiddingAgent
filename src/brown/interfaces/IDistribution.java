package brown.interfaces;

import java.util.Map;

import brown.generatepredictions.Price;
import brown.prediction.Good;


public interface IDistribution extends IPricePrediction {


  public IHistogram getPriceDistribution();
  
  
		/*public Double[] getDistPerGood(int i);
		public Double[] getMeanPerGood();
		public Double   getRandomSample(int i);
		*/
}
