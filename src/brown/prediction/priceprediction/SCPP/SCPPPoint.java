package brown.prediction.priceprediction.SCPP;

import brown.prediction.good.GoodPrice;
import brown.prediction.good.GoodPriceVector;
import brown.prediction.priceprediction.IPointPrediction;
import brown.prediction.priceprediction.SimplePointPrediction;
import brown.prediction.strategies.IPredictionStrategy;
import brown.prediction.valuation.IValuation;
import brown.prediction.valuation.MetaVal;
import brown.prediction.valuation.ValuePort;

/*
 * implements self-confirming price predictions for point predictions. 
 */
public class SCPPPoint implements IPointPrediction {
  
  private IPredictionStrategy strat; 
  private IPointPrediction initial; 
  private Integer numGames; 
  private Integer numIterations; 
  private Double decay;
  private Double pdThresh; 
  private MetaVal distInfo;
  private ValuePort vPort;
  
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
   * @param distInfo
   * information about the underlying valuation distribution.
   */
  public SCPPPoint(IPredictionStrategy strat, IPointPrediction initial, Integer numGames, 
      Integer numIterations, Double decay, Double pdThresh, MetaVal distInfo) {
    this.strat = strat; 
    this.initial = initial; 
    this.numGames = numGames;
    this.numIterations = numIterations; 
    this.decay = decay; 
    this.pdThresh = pdThresh; 
    this.distInfo = distInfo; 
    
    this.vPort = new ValuePort(distInfo, initial.getPrediction());
  }

  
  @Override
  public GoodPriceVector getPrediction() {
    
    IPointPrediction returnPrediction = initial; 
    for(int i = 0; i < numIterations; i++) {
      IPointPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
    }
    return null;
  }

  /**
   * method that simulates a game against oneself, with an input number of 
   * simulated players.
   * @return
   */
  private IPointPrediction playSelf(Integer simPlayers, 
      IPointPrediction aPrediction) {
    //what do we need here... 
    //we need a meta valuation so we can re-generate some 
    //values according to the data given. 
    //maybe we can give the meta values, but for now we'll
    //just use the already existing value generator.
    GoodPriceVector allGoods = initial.getPrediction();
    for(GoodPrice g : allGoods) {
      
    }
    for(int i = 0; i < numGames; i++) {
      for(int j = 0; j < SIMPLAYERS; j++) {
       IValuation aValuation = vPort.getValuation(distInfo);
      GoodPriceVector aBid = strat.getPrediction(aPrediction, aValuation);
      }
    }
    return null; 
  }
  
  
  @Override
  public void setPrediction(GoodPrice aPrediction) {
    // TODO Auto-generated method stub
    
  }
}