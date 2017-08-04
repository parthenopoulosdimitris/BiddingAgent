package brown.prediction.priceprediction.SCPP; 


import brown.prediction.good.Good;
import brown.prediction.good.GoodDist;
import brown.prediction.good.GoodDistVector;
import brown.prediction.good.GoodPrice;
import brown.prediction.good.GoodPriceVector;
import brown.prediction.histogram.IndependentHistogram;
import brown.prediction.priceprediction.IIndependentPrediction;
import brown.prediction.priceprediction.SimpleDistPrediction;
import brown.prediction.strategies.IPredictionStrategy;
import brown.prediction.valuation.MetaVal;
import brown.prediction.valuation.SimpleValuationBundle;
import brown.prediction.valuation.ValuePort;

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
  private Integer numGames; 
  private Integer iterations; 
  private IIndependentPrediction initial; 
  private Double threshold; 
  private ValuePort vPort; 
  private MetaVal metaVal; 
  private Integer SIMPLAYERS = 10;
  
  private Double MIN = 0.0;
  private Double MAX = 5.0 * metaVal.getScale();
  private Integer BINS = 100; 
    
  
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
   * An initial price prediction to be improved upon
   * @param threshold
   * KS threshold.
   */
  public SCPPIndependentDist(IPredictionStrategy strat, Integer games, Integer iterations, 
      IIndependentPrediction initial, Double threshold, MetaVal distInfo) {
    this.strat = strat; 
    this.numGames = games; 
    this.iterations = iterations;  
    this.initial = initial; 
    this.threshold = threshold; 
    this.metaVal = distInfo; 
    this.vPort = new ValuePort(distInfo, initial.getPrediction().getGoods()); 
  }
  
  

  //here it would be by far most efficient to treat the goods as totally 
  //independent.
  //that means going to the TP valuation and making a valuation for 
  //each individual good and then sending it over as a simple valuation
  //bundle with numGoods observations where each observation is just a 
  //good.
  //repeatedly sample each good with a montecarlo simulation.
  //fill up histograms with the information.
  
  @Override
  public GoodDistVector getPrediction() {
    // TODO Auto-generated method stub
    IIndependentPrediction returnPrediction = initial;
    GoodDistVector returnVector = returnPrediction.getPrediction();
    Boolean withinThreshold = true; 
    for(int i = 0; i < iterations; i++) {
      IIndependentPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
    }
    return null;
  }

  
  private IIndependentPrediction playSelf(Integer numPlayers,
      IIndependentPrediction aPrediction) {
    //add datatypes
    GoodPriceVector currentHighest = new GoodPriceVector();
    IIndependentPrediction result = new SimpleDistPrediction(); 
    GoodDistVector guess = new GoodDistVector(); 
    //populate guess
    for(Good g : aPrediction.getPrediction().getGoods()) {
      IndependentHistogram possiblePrices = 
          new IndependentHistogram(MIN, MAX, BINS);
      guess.add(new GoodDist(g, possiblePrices));
    }
    
    //play self.
    for(int i = 0; i < numGames; i++) {
      for(int j = 0; j < numPlayers; j++) {
       //get valuations
        SimpleValuationBundle indBundle = new SimpleValuationBundle();
        for(Good g : aPrediction.getPrediction().getGoods()) {
         indBundle.add(vPort.getIndependentNormal(g));
        }
        GoodPriceVector aBid = strat.getPrediction(aPrediction, indBundle);
        for(GoodPrice g : aBid) {
          if (currentHighest.getGoodPrice(g.getGood()).getPrice() < g.getPrice())
            currentHighest.add(g);
        }
      }
      for(GoodPrice g : currentHighest) {
        GoodDist corresponding = guess.getGoodDist(g.getGood());
        IndependentHistogram hist = corresponding.getDist();
        hist.add(g.getPrice());
        corresponding.setDist(hist);
        guess.add(corresponding);
      }
      //now that we have a winning vector, add it to the goodDist. 
      currentHighest.clear();
    }
    result.setPrediction(guess); 
    return result;
  }

  @Override
  public void setPrediction(GoodDistVector inputPrediction) {
    // TODO Auto-generated method stub
  }
  
  
  
  
  
  
  
}