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
  private Double pdThresh; 
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
      Integer numIterations, Double pdThresh, MetaVal distInfo) {
    this.strat = strat; 
    this.initial = initial; 
    this.numGames = numGames;
    this.numIterations = numIterations;  
    this.pdThresh = pdThresh; 
    
    this.vPort = new ValuePort(distInfo, initial.getPrediction().getGoods());
  }

  
  @Override
  public GoodPriceVector getPrediction() {
    //following the SCPP algorithm specified in the paper:
    //set an initial point prediction
    IPointPrediction returnPrediction = initial; 
    //usable prediction. 
    GoodPriceVector returnVector = returnPrediction.getPrediction();
    Boolean withinThreshold = true; 
    //main loop over input number of iterations.
    for(int i = 0; i < numIterations; i++) {
      //get a point prediction as a result of simulating games against self.
      IPointPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      //usable version
      GoodPriceVector guessVector = aGuess.getPrediction();
      //check for whether each bid in the updating version is within theshold of the
      //game vector.
      for(GoodPrice g : guessVector) {
        if(g.getPrice() - returnVector.getGoodPrice(g.getGood()).getPrice() > pdThresh)
          //if this condition is not met for every good, then we continue to iterate
          withinThreshold = false; 
      }
      //if within threshold, we exit loop.
      if(withinThreshold)
        return guessVector;
      //otherwise, set threshold condition back to true, introduce an updated
      //vector according to the decay schedule and reiterate.
      withinThreshold = true;
      Double decay = (double)(numIterations - i + 1) / (double) numIterations;
      for(GoodPrice g : returnVector) {
        Double guessPrice = guessVector.getGoodPrice(g.getGood()).getPrice();
        Double newPrice = decay * guessPrice + (1 - decay) * g.getPrice();
        returnVector.add(new GoodPrice(g.getGood(), newPrice));
      }
    }
    return returnVector;
  }

  /**
   * method that simulates a game against oneself, with an input number of 
   * simulated players.
   * @param simPlayers
   * number of Simulated Players.
   * @param aPrediction
   * an input prediction
   * @return
   * a point prediction that is the average result of winning auctions against 
   * self.
   */
  private IPointPrediction playSelf(Integer simPlayers, 
      IPointPrediction aPrediction) {
    //the final guess vector
    GoodPriceVector guess = new GoodPriceVector();
    GoodPriceVector currentHighest = new GoodPriceVector();
    //main loop; over G, number of games
    for(int i = 0; i < numGames; i++) {
      //initialize and populate a goodPriceVector
      for(GoodPrice g : aPrediction.getPrediction()) {
        guess.add(new GoodPrice(g.getGood(), 0.0));
      }
      //for each simulated player submit a simulated bid based on the 
      //valuation distributions and the bidding strategy
      for(int j = 0; j < simPlayers; j++) {
        //in this case, get a total valuation over a normal distribution. 
        //CAN CHANGE THIS.
       IValuation aValuation = vPort.getAllNormal();
      GoodPriceVector aBid = strat.getPrediction(aPrediction, aValuation);
      //determine a winner of the auction.
      for(GoodPrice g : aBid) {
        if (currentHighest.getGoodPrice(g.getGood()).getPrice() < g.getPrice())
          currentHighest.add(g);
      }
      }
      //submit averaged version to guess (?? iffy about this if this step 
      //is what is needed)
      for(GoodPrice g : currentHighest) {
        GoodPrice other = guess.getGoodPrice(g.getGood());
        Double newPrice = (g.getPrice() / numGames) + other.getPrice();
        guess.add(new GoodPrice(g.getGood(), newPrice));
      }
      currentHighest.clear();
    }
    //convert to a price prediction.
    IPointPrediction pointGuess = new SimplePointPrediction(); 
    pointGuess.setPrediction(guess);
    return pointGuess; 
  }
  
  @Override
  public void setPrediction(GoodPriceVector aPrediction) {
    // TODO Auto-generated method stub
    
  }
}