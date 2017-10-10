package temp.predictors;

import temp.MetaVal;
import temp.maximizers.IBundleMaximizer;
import temp.predictions.IIndependentPrediction;
import temp.predictions.IJointPrediction;

public class SCPPJointDist implements IJointPredictor {
  
  private IJointPrediction initial; 
  private IBundleMaximizer strat; 
  private Integer numGames; 
  private Integer numIterations; 
  private Double pdThresh; 
  private MetaVal distInfo; 
  private Integer SIMPLAYERS = 10; 
  
  private Double MIN = 0.0; 
  
  public SCPPJointDist(IBundleMaximizer strat, IJointPrediction initial, 
      Integer numGames, Integer numIterations, Double pdThresh, MetaVal distInfo) {
    this.strat = strat; 
    this.initial = initial; 
    this.numGames = numGames; 
    this.numIterations = numIterations; 
    this.pdThresh = pdThresh; 
    this.distInfo = distInfo; 
    
  }
  
  /**
   * this is where the user needs to get creative.
   */
  public IJointPrediction getPrediction() {
    return null;
  }
}