package temp.maximizers.library;

import java.util.Map;

import brown.tradeable.ITradeable;
import temp.maximizers.IMaxDist;
import temp.predictions.IDistributionPrediction;
import temp.price.Price;
import temp.representation.PointRep;

public class LocalBidIndDist implements IMaxDist {

  private IMaxDist baseStrategy; 
  private Integer NUMITERATIONS = 100; 
  
  public LocalBidIndDist(IMaxDist baseStrategy) {
    this.baseStrategy = baseStrategy; 
  }
  
  @Override
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IDistributionPrediction prediction) {
    Map<ITradeable, Double> bidVector = this.baseStrategy.getBids(valuations, prediction);
    for (int i = 0; i < NUMITERATIONS; i++) {
      for (ITradeable t : valuations.keySet()) {
        bidVector = individualMaximize(t, prediction, bidVector, valuations);
      }
    }
    return bidVector;
  }
  
  private Map<ITradeable, Double> individualMaximize(ITradeable tradeable, IDistributionPrediction prediction,
      Map<ITradeable, Double> bidVector, Map<ITradeable, Double> valuations) {
    Map<ITradeable, Price> meanPrediction = ((PointRep) prediction.getMeanPrediction(bidVector.keySet())).rep;
    
    return null; 
    
  }
}