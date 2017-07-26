package brown.prediction.SCPP; 

import java.util.Map;

import brown.generatepredictions.Price;
import brown.interfaces.IHistogram;
import brown.interfaces.IIndependentPrediction;
import brown.interfaces.IJointPrediction;
import brown.interfaces.IPredictionStrategy;
import brown.prediction.Good;
import brown.prediction.GoodPriceVector;

/**
 * Gives self-confirming price predictions for a distributional price prediction, 
 * with dependent valuations for goods.
 * 
 * @author acoggins
 *
 */
public class SCPPDependentDist implements IIndependentPrediction {

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
  public GoodPriceVector getPrediction() {
    // TODO Auto-generated method stub
    return null;
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


  @Override
  public IHistogram getPriceDistribution() {
    // TODO Auto-generated method stub
    return null;
  }

  
}