package brown.prediction.priceprediction.SCPP; 


import java.util.HashSet;
import java.util.Set;

import brown.prediction.goodprice.Price;
import brown.generator.library.NormalGenerator;
import brown.prediction.goodprice.Bundle;
import brown.prediction.goodprice.Dist;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPrice;
import brown.prediction.goodprice.GoodPriceVector;
import brown.prediction.goodprice.IGood;
import brown.prediction.histogram.Bin;
import brown.prediction.histogram.IndependentHistogram;
import brown.prediction.priceprediction.IIndependentPrediction;
import brown.prediction.priceprediction.SimpleDistPrediction;
import brown.prediction.strategies.IPredictionStrategy;
import brown.prediction.valuation.MetaVal;
import brown.valuation.library.AdditiveValuation;

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
  private MetaVal metaVal; 
  private Integer totalCount;
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
  }
  
  

//gets the predictions according to self-confirming price predictions. 
  
  @Override
  public GoodPriceVector<Good, Dist> getPrediction() {
    //set totalCount to 0
    totalCount = 0; 
    IIndependentPrediction returnPrediction = initial;
    GoodPriceVector<Good, Dist> returnVector = returnPrediction.getPrediction();
    Boolean withinThreshold = true; 
    //a count to be used in updating the histograms.
    int iterCount = 0; 
    for(int i = 0; i < iterations; i++) {
      //simulate a series of games to produce a distribution of price predictions per good.
      IIndependentPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      //check the threshold condition. 
      //update returnPrediction based on the decay factor.
      //need to compute KS statistic for each good.
      //that is, the point on the distribution that is the greatest distance between the two. 
      
      for(GoodPrice<Good, Dist> gp : aGuess.getPrediction()) {
        GoodPrice<Good, Dist> comparison = returnVector.getGoodPrice(gp.getGood());
        //retrieve histograms
        IndependentHistogram first = ((Dist) gp.getPrice()).dist;
        IndependentHistogram second = ((Dist) comparison.getPrice()).dist;
        //check for an error. 
        if (first.getNumBins() != second.getNumBins()) {
          System.out.println("Error: histogram size mismatch");
          return null;
        }
        //check threshold condition, which in this case is the greatest difference
        //in bin size per good (approx. of KS statistic). 
        //TODO: normalize
        for (int j = 0; j < first.getNumBins(); j++) {
          Double difference = (double) first.getBinByID(j).frequency() 
              - (double) second.getBinByID(j).frequency();
          if (difference > this.threshold) {
            withinThreshold = false; 
            break;
          }
        }
      }
      //if the threshold condition is met, return the current prediction. 
      if (withinThreshold)
        return aGuess.getPrediction();
      //otherwise, update and iterate. 
      //update using the decay schedule parameter. 
      Double decay = (((double) iterations) - iterCount + 1.0) / ((double) iterations);
      //have to update each bin in each histogram.
      for(GoodPrice<Good, Dist> gp : aGuess.getPrediction()) {
        GoodPrice<Good, Dist> initGp = returnVector.getGoodPrice(gp.getGood());
        IndependentHistogram current = ((Dist) gp.getPrice()).dist;
        IndependentHistogram initial = ((Dist) initGp.getPrice()).dist;
        IndependentHistogram returnHist = new IndependentHistogram(
            current.getMinimum(), current.getMaximum(), current.getNumBins());
        for(Bin b : current.getAllBins()) {
          Bin corresponding = initial.getBinByID(b.getID());
          double newFreq =  (decay * (double) b.frequency())
              + ((1.0 - decay) * (double) corresponding.frequency());
          Integer newFreqInt = (int) newFreq; 
          Bin returnBin = new Bin(b);
          returnBin.setFrequency(newFreqInt);
          returnHist.setBin(returnBin);
        }
        gp.setPrice(new Dist(returnHist));
      }
      iterCount++;
    }
    return returnVector;
  }

  
  private IIndependentPrediction playSelf (Integer numPlayers,
      IIndependentPrediction aPrediction) {
    //add datatypes
    GoodPriceVector<Good, Price> currentHighest = new GoodPriceVector<Good, Price>();
    IIndependentPrediction result = new SimpleDistPrediction(); 
    GoodPriceVector<Good, Dist> guess = new GoodPriceVector<Good, Dist>(); 
    //populate guess
    Set<IGood> iGoodSet = aPrediction.getPrediction().getGoods();
    Set<Good> goodSet = (Set) iGoodSet;
    for(Good g : goodSet) {
      IndependentHistogram possiblePrices = 
          new IndependentHistogram(MIN, MAX, BINS);
      Dist ppDist = new Dist(possiblePrices);
      guess.add(new GoodPrice<Good, Dist>(g, ppDist));
    }
    
    //play self.
    for(int i = 0; i < numGames; i++) {
      for(int j = 0; j < numPlayers; j++) {
       //get valuations
        //wtf is a groupvaluationset
        GoodPriceVector<Bundle, Price> indBundle = new GoodPriceVector<Bundle, Price>();
        for(Good g : goodSet) {
          //get an individual valuation of this guy.
         GoodPrice<Bundle, Price> aGoodPrice = this.getValuation(g);
         indBundle.add(aGoodPrice);
        }
        GoodPriceVector<Good, Price> aBid = strat.getPrediction(aPrediction, indBundle);
        for(GoodPrice<Good, Price> g : aBid) {
          if (((Price) currentHighest.getGoodPrice(g.getGood()).getPrice()).price
              < ((Price) g.getPrice()).price)
            currentHighest.add(g);
        }
      }
      //here we update the prediction by adding the winning vector to the histogram. 
      //takes a few lines with the datatypes I have here.
      for(GoodPrice<Good, Price> g : currentHighest) {
        GoodPrice<Good, Dist> corresponding = guess.getGoodPrice(g.getGood());
        IndependentHistogram hist = ((Dist) corresponding.getPrice()).dist;
        hist.add(((Price) g.getPrice()).price);
        //increase the total count of 
        corresponding.setPrice(new Dist(hist));
        guess.add(corresponding);
      }
      //increase the total count of goods in a single distribution
      totalCount++;
      //now that we have a winning vector, add it to the goodDist. 
      currentHighest.clear();
    }
    result.setPrediction(guess); 
    return result;
  }

  //valuation getting code. uses datatypes from the value generator.
  private GoodPrice<Bundle, Price> getValuation(Good aGood) {
    brown.valuable.library.Good newGood = new brown.valuable.library.Good(aGood.ID);
    NormalGenerator norm = new NormalGenerator(metaVal.getValFunction(),
        metaVal.getMonotonic(), metaVal.getScale());
    AdditiveValuation addVal = norm.getSingleValuation(newGood);
    Price newPrice = new Price(addVal.getPrice());
    Bundle newBundle = new Bundle(new HashSet<Good>());
    newBundle.bundle.add(aGood);
    GoodPrice<Bundle, Price> returnGp = new GoodPrice<Bundle, Price>(newBundle, newPrice);
    return returnGp;
  }
  
  @Override
  public void setPrediction(GoodPriceVector inputPrediction) {
    // TODO Auto-generated method stub
  }
  
}