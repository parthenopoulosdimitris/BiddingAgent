package temp.predictors;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import temp.JointDist;
import temp.MetaVal;
import temp.maximizers.IBundleMaximizer;
import temp.predictions.IIndependentPrediction;
import temp.predictions.IJointPrediction;

/**
 * SCPP predictor algorithm, for the case of goods with exposure
 * @author andrew
 *
 */
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
   * this is where I need to get creative.
   */
  public IJointPrediction getPrediction() {
    IJointPrediction returnPrediction = initial; 
    Map<Set<Tradeable>, JointDist> returnVector = returnPrediction.getDistPrediction();
    return null;
  }
}