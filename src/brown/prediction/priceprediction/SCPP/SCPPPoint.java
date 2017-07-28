package brown.prediction.priceprediction.SCPP;

import brown.prediction.good.GoodPrice;
import brown.prediction.good.GoodPriceVector;
import brown.prediction.priceprediction.IPointPrediction;
import brown.prediction.priceprediction.SimplePointPrediction;
import brown.prediction.strategies.IPredictionStrategy;

/*
 * implements self-confirming price predictions for point predictions. 
 */
public class SCPPPoint implements IPointPrediction{
  
  private IPredictionStrategy strat; 
  private IPointPrediction initial; 
  private Integer numGames; 
  private Integer numIterations; 
  private Double decay;
  private Double pdThresh; 
  
  private Integer SIMPLAYERS = 10;
  
  /**
   * SCPPPoint implements self confirming price predictions for a set of point predictions. 
   * @param strat
   * a prediction strategy by which to behave.
   * @param initial
   * an initial point prediction. 
   * @param numGames
   * a number of games to run per iteration
   * @param numIterations
   * a number of iterations by which to improve the initial price prediction.
   * @param decay
   * a decay factor
   * @param pdThresh
   * price-distance threshold- determines when the updated prediction is close enough to an 
   * ideal prediction produced during an iteration of this process. 
   */
  public SCPPPoint(IPredictionStrategy strat, IPointPrediction initial, Integer numGames, 
      Integer numIterations, Double decay, Double pdThresh) {
    this.strat = strat; 
    this.initial = initial; 
    this.numGames = numGames;
    this.numIterations = numIterations; 
    this.decay = decay; 
    this.pdThresh = pdThresh; 
  }

  
  @Override
  public GoodPriceVector getPrediction() {
    
    IPointPrediction returnPrediction = initial; 
    for(int i = 0; i < numIterations; i++) {
      IPointPrediction aGuess = this.playSelf(this.SIMPLAYERS);
    }
    return null;
  }

  /**
   * method that simulates a game against oneself, with an input number of 
   * simulated players.
   * @return
   */
  private IPointPrediction playSelf(Integer simPlayers) {
    
    return null; 
  }
  
  @Override
  public void setPrediction(GoodPrice aPrediction) {
    // TODO Auto-generated method stub
    
  }
}