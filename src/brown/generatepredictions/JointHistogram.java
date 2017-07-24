package brown.generatepredictions;

import java.util.Map;

import brown.interfaces.IJointPrediction;
import brown.prediction.Good;
import brown.prediction.GoodPrice;
import brown.prediction.PredictionVector;

public class JointHistogram implements IJointPrediction {

	@Override
	public Double[] getDistPerGood(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double[] getMeanPerGood() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getRandomSample(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double[] getPricePrediction() {
		// TODO Auto-generated method stub
		return null;
	}

  @Override
  public PredictionVector getPrediction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setPrediction(GoodPrice aPrediction) {
    // TODO Auto-generated method stub
    
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
