package brown.prediction.priceprediction.SCPP; 


import brown.prediction.histogram.IndependentHistogram;
import brown.prediction.priceprediction.IIndependentPrediction;
import brown.prediction.strategies.IPredictionStrategy;
import brown.prediction.valuation.MetaVal;

/**
 * Gives self-confirming price predictions for a distributional price prediction, 
 * under the assumption of independent valuations for goods (i.e. no supplementary, 
 * complementary goods).
 *
 * with the current value generator this seems like a bit of a trivial case, 
 * because there is no variance in the valuation of goods.
 * but worth implementing because it's a simpler version of the dependent 
 * case, and there may be a version where there is independent variance among 
 * goods but the bundles are additive
 * 
 * @author acoggins
 *
 */
public class SCPPIndependentDist implements IIndependentPrediction {

  private IPredictionStrategy strat; 
  private Integer games; 
  private Integer iterations; 
  private IIndependentPrediction initial; 
  private Double threshold; 
  private MetaVal distInfo;
  
  /**
   * Constructor for SCPPIndependentDist. Here is where inputs for the algorithm
   * are entered.
   * @param strat
   * A Prediction Strategy: given a price prediction, how does the agent bid? 
   * @param games
   * Number of games per iteration
   * @param iterations
   * How many times the simulation is run
   * @param decay
   * Decay schedule for simulations
   * @param initial
   * An initial price prediction to be imperoved upon
   * @param threshold
   * KS threshold.
   */
  public SCPPIndependentDist(IPredictionStrategy strat, Integer games, Integer iterations, 
      IIndependentPrediction initial, Double threshold, MetaVal distInfo) {
    this.strat = strat; 
    this.games = games; 
    this.iterations = iterations;  
    this.initial = initial; 
    this.threshold = threshold; 
    this.distInfo = distInfo; 
  }
  
  
  //what do we need to do here: 
  //1. create a price prediction strategy
  //2. create an initial price prediction.
  //give the 
  @Override
  public IndependentHistogram getPriceDistribution() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
}