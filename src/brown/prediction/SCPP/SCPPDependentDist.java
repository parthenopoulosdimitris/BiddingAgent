package brown.prediction.SCPP; 


import brown.interfaces.IJointPrediction;
import brown.interfaces.IPredictionStrategy;
import brown.prediction.JointHistogram;

/**
 * Gives self-confirming price predictions for a distributional price prediction, 
 * with dependent valuations for goods.
 * 
 * @author acoggins
 *
 */
public class SCPPDependentDist implements IJointPrediction {

  private IPredictionStrategy strat; 
  private Integer games; 
  private Integer iterations; 
  private Double decay; 
  private IJointPrediction initial; 
  private Double threshold; 
  
  /**
   * Constructor for SCPPDependentDist. Here is where inputs for the algorithm
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
   * An initial price prediction to be improved upon
   * @param threshold
   * KS threshold.
   */
  public SCPPDependentDist(IPredictionStrategy strat, Integer games, Integer iterations, 
      Double decay, IJointPrediction initial, Double threshold) {
    this.strat = strat; 
    this.games = games; 
    this.iterations = iterations; 
    this.decay = decay; 
    this.initial = initial; 
    this.threshold = threshold; 
  }


  @Override
  public JointHistogram getPriceDistribution() {
    // TODO Auto-generated method stub
    return null;
  }

  
}