package temp.predictors.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.mechanism.tradeable.ITradeable;
import brown.user.agent.library.AbsCombinatorialProjectAgentV2;
import temp.histograms.JointHistogram;
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
  private AbsCombinatorialProjectAgentV2 agent;  
  private Integer SIMPLAYERS = 10; 
  private Integer SIZE = 5; 
  private Double MIN = 0.0; 
  
  public SCPPJointDist(IMaxDist strat, SimpleJointPrediction initial, 
      Integer numGames, Integer numIterations, Double pdThresh, AbsCombinatorialProjectAgentV2 agent) {
    this.strat = strat; 
    this.initial = initial; 
    this.numGames = numGames; 
    this.numIterations = numIterations; 
    this.pdThresh = pdThresh; 
    this.agent = agent; 
  }
  
  /**
   */
  public SimpleJointPrediction getPrediction() {
    SimpleJointPrediction returnPrediction = initial; 
    Set<ITradeable> initGoods = returnPrediction.getGoods();
    JointDist returnDist = returnPrediction.getPrediction(initGoods).rep;
    Boolean withinThreshold = true; 
    for (int i = 0; i < numIterations; i++) {
      SimpleJointPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
    }
    return null;
  }
  
  private SimpleJointPrediction playSelf(Integer numPlayers, 
      SimpleJointPrediction aPrediction) {
    //add datatypes
    Map<ITradeable, Double> currentHighest = new HashMap<ITradeable, Double>(); 
    JointHistogram guess = new JointHistogram(this.SIZE, 0.0, 1.0, 100); 
    return null; 
    
  }
}