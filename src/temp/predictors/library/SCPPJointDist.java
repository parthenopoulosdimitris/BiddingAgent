package temp.predictors.library;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import brown.valuation.library.BundleValuation;
import temp.MetaVal;
import temp.maximizers.IMaxDist;
import temp.predictions.IDistributionPrediction;
import temp.predictions.library.SimpleJointPrediction;
import temp.predictors.IDistributionPredictor;
import temp.price.JointDist;

/**
 * SCPP predictor algorithm, for the case of goods with exposure
 * @author andrew
 *
 */
public class SCPPJointDist implements IDistributionPredictor {
  
  private SimpleJointPrediction initial; 
  private IMaxDist strat; 
  private Integer numGames; 
  private Integer numIterations; 
  private Double pdThresh; 
  private BundleValuation distInfo; 
  private Integer SIMPLAYERS = 10; 
  
  private Double MIN = 0.0; 
  
  public SCPPJointDist(IMaxDist strat, SimpleJointPrediction initial, 
      Integer numGames, Integer numIterations, Double pdThresh, BundleValuation distInfo) {
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
  public SimpleJointPrediction getPrediction() {
     SimpleJointPrediction returnPrediction = initial; 
    return null;
  }
}