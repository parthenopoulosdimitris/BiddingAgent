package brown.prediction.priceprediction.SCPP;


import java.util.HashSet;
import java.util.Set;

import brown.generator.library.NormalGenerator;
import brown.prediction.goodprice.Bundle;
import brown.prediction.goodprice.Good;
import brown.prediction.goodprice.GoodPrice;
import brown.prediction.goodprice.GoodPriceVector;
import brown.prediction.goodprice.Price;
import brown.prediction.priceprediction.IPointPrediction;
import brown.prediction.priceprediction.SimplePointPrediction;
import brown.prediction.strategies.IPredictionStrategy;
import brown.prediction.valuation.MetaVal;
import brown.valuation.IValuation;
import brown.valuation.library.BundleValuationSet;

/*
 * implements self-confirming price predictions for point predictions. 
 */
public class SCPPPoint implements IPointPrediction {
  
  private IPredictionStrategy strat; 
  private IPointPrediction initial; 
  private Integer numGames; 
  private Integer numIterations; 
  private Double pdThresh; 
  private MetaVal metaVal;
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
    this.metaVal = distInfo;
  }

  
  @Override
  public GoodPriceVector<Good, Price> getPrediction() {
    //following the SCPP algorithm specified in the paper:
    //set an initial point prediction
    IPointPrediction returnPrediction = initial; 
    //usable prediction. 
    GoodPriceVector<Good, Price> returnVector = returnPrediction.getPrediction();
    Boolean withinThreshold = true; 
    //main loop over input number of iterations.
    for(int i = 0; i < numIterations; i++) {
      //get a point prediction as a result of simulating games against self.
      IPointPrediction aGuess = this.playSelf(this.SIMPLAYERS, returnPrediction);
      //usable version
      GoodPriceVector<Good, Price> guessVector = aGuess.getPrediction();
      //check for whether each bid in the updating version is within theshold of the
      //game vector.
      for(GoodPrice<Good, Price> g : guessVector) {
        if(((Price) g.getPrice()).price
        - ((Price)returnVector.getGoodPrice(g.getGood()).getPrice()).price
         > pdThresh) {
          //if this condition is not met for every good, then we continue to iterate
          withinThreshold = false; 
          break;
        }
      }
      //if within threshold, we exit loop.
      if(withinThreshold)
        return guessVector;
      //otherwise, set threshold condition back to true, introduce an updated
      //vector according to the decay schedule and reiterate.
      withinThreshold = true;
      Double decay = (double) (numIterations - i + 1) / (double) numIterations;
      for(GoodPrice<Good, Price> g : returnVector) {
        Double guessPrice = ((Price) guessVector
        		.getGoodPrice(((Good) g.getGood())).getPrice()).price;
        Double newPrice = decay * guessPrice + (1 - decay)
        		* ((Price)g.getPrice()).price;
        returnVector.add(new GoodPrice<Good, Price>(
        		(Good) g.getGood(), new Price(newPrice)));
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
    GoodPriceVector<Good, Price> guess = new GoodPriceVector<Good, Price>();
    GoodPriceVector<Good, Price> currentHighest = new GoodPriceVector<Good, Price>();
    //main loop; over G, number of games
    for(int i = 0; i < numGames; i++) {
      //initialize and populate a goodPriceVector
      for(GoodPrice<Good, Price> g : aPrediction.getPrediction()) {
        guess.add(new GoodPrice<Good, Price>(g.getGood(), new Price(0.0)));
      }
      //for each simulated player submit a simulated bid based on the 
      //valuation distributions and the bidding strategy
      for(int j = 0; j < simPlayers; j++) {
        //in this case, get a total valuation over a normal distribution. 
        //CAN CHANGE THIS.
    	//for the point scenario, will need to get an additive valuation bundle. 
    	///VALUATION
        Set<Good> b = (Set<Good>) ((Good) this.initial.getPrediction().getGoods());
    	  GoodPriceVector<Bundle, Price> valuation = this.getValuation(b);
    	  GoodPriceVector<Good, Price> aBid = strat.getPrediction(aPrediction, valuation);
      //determine a winner of the auction.
      for(GoodPrice<Good, Price> g : aBid) {
        //casting hell: if the highest is lower than the newest, then the newest is
        //the new highest.
        if (((Price) currentHighest.getGoodPrice(((Good)g.getGood())).getPrice()).price 
        		< ((Price)g.getPrice()).price)
          currentHighest.add(g);
      }
      }
      //submit averaged version to guess (?? iffy about this if this step 
      //is what is needed)
      for(GoodPrice<Good, Price> g : currentHighest) {
        GoodPrice<Good, Price> other = guess.getGoodPrice(g.getGood());
        Double newPrice = (((Price) g.getPrice()).price / numGames)
        		+ ((Price) other.getPrice()).price;
        guess.add(new GoodPrice<Good, Price>(g.getGood(), new Price(newPrice)));
      }
      currentHighest.clear();
    }
    //convert to a price prediction.
    IPointPrediction pointGuess = new SimplePointPrediction(); 
    pointGuess.setPrediction(guess);
    return pointGuess; 
  }
  
  /**
   * valuation-getting code.
   * has to access areas of the valueGenerator.
   * @return
   */
  private GoodPriceVector<Bundle, Price> getValuation(Set<Good> goods) {
    //set of Goods of the type in the value generator.
    Set<brown.valuable.library.Good> goodSet = new HashSet<brown.valuable.library.Good>();
    //converting a set of these goods to a set of those goods.
    for(Good g : goods) {
      goodSet.add(new brown.valuable.library.Good(g.ID));
    }
    //creating that kind of bundle. 
    brown.valuable.library.Bundle bundle = new brown.valuable.library.Bundle(goodSet);
    NormalGenerator norm = new NormalGenerator(metaVal.getValFunction(),
        metaVal.getMonotonic(), metaVal.getScale());
    //get a valuation set from the generator.
    BundleValuationSet bund = norm.getAllBundleValuations(bundle);
    //convert a bundle valuation set to a GoodPriceVector
    GoodPriceVector<Bundle, Price> returnVector = new GoodPriceVector<Bundle, Price>();
    for(IValuation aBundle : bund) {
      //get price
      Price newPrice = new Price(((brown.valuation.library.BundleValuation)
          aBundle).getPrice());
      //get bundle
      brown.valuable.library.Bundle newBundle = ((brown.valuable.library.Bundle)
          ((brown.valuation.library.BundleValuation) aBundle).getValuable());
      Set<Good> newGoodSet = new HashSet<Good>();
      for(brown.valuable.library.Good someGood : newBundle.bundle) {
        newGoodSet.add(new Good(someGood.ID));
      }
      Bundle returnBundle = new Bundle(newGoodSet);
      GoodPrice<Bundle, Price> newGoodPrice =
          new GoodPrice<Bundle, Price>(returnBundle, newPrice);
      returnVector.add(newGoodPrice);
    }
	  return returnVector;
  }
  
  @Override
  public void setPrediction(GoodPriceVector<Good, Price> aPrediction) {
    // TODO Auto-generated method stub
    
  }
  
}