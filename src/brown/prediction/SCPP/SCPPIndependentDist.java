package brown.prediction.SCPP; 


import brown.interfaces.IIndependentPrediction;
import brown.interfaces.IPredictionStrategy;
import brown.prediction.IndependentHistogram;

/**
 * Gives self-confirming price predictions for a distributional price prediction, 
 * under the assumption of independent valuations for goods (i.e. no supplementary, 
 * complementary goods).
 * 
 * @author acoggins
 *
 */
public class SCPPIndependentDist implements IIndependentPrediction {

  private IPredictionStrategy strat; 
  private Integer games; 
  private Integer iterations; 
  private Double decay; 
  private IIndependentPrediction initial; 
  private Double threshold; 
  
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
      Double decay, IIndependentPrediction initial, Double threshold) {
    this.strat = strat; 
    this.games = games; 
    this.iterations = iterations; 
    this.decay = decay; 
    this.initial = initial; 
    this.threshold = threshold; 
  }
  

  @Override
  public IndependentHistogram getPriceDistribution() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
}