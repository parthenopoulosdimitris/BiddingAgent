package brown.agent.pointprediction; 


import java.util.HashMap;
import java.util.Map;

import brown.agent.library.SimpleAgent;
import brown.assets.value.FullType;
import brown.exceptions.AgentCreationException;
import brown.markets.SimpleAuction;
import brown.prediction.good.Good;
import brown.prediction.good.GoodPrice;
import brown.prediction.good.GoodPriceVector;
import brown.prediction.priceprediction.SimpleDistPrediction;
import brown.valuation.Valuation;
import brown.valuation.ValuationBundle;

/**
 * agent that bids each good in its optimal bundle at its marginal vlaue. 
 * since we don't always have complete information on every possible bundle's
 * value, marginal value is described as the difference between
 * the utility of an optimal bundle where the good is free, and the most optimal 
 * bundle that does not contain the good (if it exists- if not, this is just zero). 
 * 
 * @author acoggins
 *
 */
public class TargetMVBidder extends SimpleAgent {
  
  private SimpleDistPrediction aPrediction;
  
  /**
   * constructor for auction 
   * @param host
   * @param port
   * @throws AgentCreationException
   */
  public TargetMVBidder(String host, int port) throws AgentCreationException {
    super(host, port); 
  }
  
  @Override
  public void onSimpleOpenOutcry(SimpleAuction market) {
    //populate point prediction-- for now, predictions pulled from the ether
    aPrediction = new SimpleDistPrediction();
    GoodPriceVector prediction = aPrediction.getPrediction();
    for(FullType f : this.allGoods) {
      Good g = new Good(f.ID);
      GoodPrice good = new GoodPrice(g, 3.0);
      //ideally: 
      // create new PP strategy object, and then
      // generate PP with SCPP- 
      //bid based on this PP strategy. 
      aPrediction.setPrediction(good);
    }
    //initialize map of the goods to be bid. 
    Map<FullType, Double> toBid = new HashMap<FullType, Double>();
    //acquisition bundle returns all optimal bundles.
    ValuationBundle acq = this.getAcquisition(prediction);
    //the acquisition should really only have one value, but iterate and
    //grab the first (since the utilities of all optimal bid bundles are
    //the same, we're indifferent.)
    for (Valuation types : acq) {
      //for every good in the prediction vector.
      for (GoodPrice p : aPrediction.getPrediction()) {
        if(types.contains(p.getGood().toFullType())) {
          //calculate the marginal value of the good.
          Double biddingPrice = calculateMarginalValue(p, prediction);
          toBid.put(p.getGood().toFullType(), biddingPrice);
        }
        else {
          //if not, put negligibly small bid (hack: may change this to 0)
          toBid.put(p.getGood().toFullType(), 0.000001);
        }
      }
      //we only need one
      //this is a bit hacky, could sort instead? Although this is less big-O complex.
      break;
      }
    //bid in the market. 
    System.out.println(toBid);
    market.bid(this, toBid);
  }
  
  /**
   * Helper function for acquiring the set of optimal bid bundles.
   * @param aPrediction
   * a price prediction vector- a good mapped to a projected going price in
   * the auction
   * @return
   * the aquisition, or the set of optimal bid bundles- packaged
   * in a valuation bundle, so they come with their valuations, 
   * for convenience of use.
   */
  private ValuationBundle getAcquisition(GoodPriceVector aPrediction) {
    //make a copy for editing.
    ValuationBundle copy = new ValuationBundle(this.myValuation);
    ValuationBundle acquisition = new ValuationBundle();
    //max value to be determined
    Double maxValue = 0.0;
    //for each of the agent's private valuations run this block
    for (Valuation aVal : copy) {
      //get the price of the bundle
      Double value = aVal.getPrice();
      //for every good in the prediction vector run this block
      for(GoodPrice p : aPrediction) {
        //if the bundle contains this particular good, subtract the 
        //value of the good from value. Upon iteration this will give a 
        //utility for the given bundle.
        if(aVal.contains(p.getGood().toFullType())) 
        value -= p.getPrice();
      }
      copy.add(aVal.getGoods(), value);
      //determine the maximum utility. 
      maxValue = (value > maxValue) ? value : maxValue; 
    }
    //now that we have the max utility, for all bundles with this utility, 
    //add to the acquisition.
    for(Valuation types : copy) {
      if (types.getPrice() >= maxValue) {
        acquisition.add(types);
      }
    }
    //return
    System.out.println("acquisition " + acquisition);
    return acquisition;
  }
  
  /**
   * Given a valuation, a point price prediction, and a good, 
   * calculates the marginal value of good. That is, the difference in 
   * utility between optimal bundles with and without the good.
   * @param optimal
   * @param aVec
   * @param good
   * @return
   */
  private Double calculateMarginalValue (GoodPrice good, GoodPriceVector aVec) {  
    //create a copy with the specified good free of charge. 
    GoodPriceVector goodFree = new GoodPriceVector(aVec);
    goodFree.add(good.getGood(), 0.0);
    //and now one where the good is unavailable.
    GoodPriceVector goodUnavailable = new GoodPriceVector(aVec);
    goodUnavailable.add(good.getGood(), Double.POSITIVE_INFINITY);
    //get the acquisition for each of these cases.
    ValuationBundle acqFree = this.getAcquisition(goodFree);
    ValuationBundle acqUnavailable = this.getAcquisition(goodUnavailable);

    //collect an optimal bundle from each of these acquisitions.
    Valuation optimalSansGood = new Valuation(null, null);
    for(Valuation v : acqUnavailable) {
      optimalSansGood = v;
      break;
    }
    Valuation optimalFreeGood = new Valuation(null, null);
    for(Valuation v : acqFree) {
      optimalFreeGood = v;
      break;
    }
    //get the utility of both and take the difference- this is 
    //the marginal value. 
    Double optimalVal = optimalFreeGood.getPrice();
    Double optimalVal2 = optimalSansGood.getPrice();
    for(GoodPrice g : aVec) {
      if (optimalFreeGood.contains(g.getGood().toFullType())) {
        optimalVal -= g.getPrice();
      }
      if (optimalSansGood.contains(g.getGood().toFullType())) {
        optimalVal2 -= g.getPrice();
      }
    }
    return (optimalVal - optimalVal2);
  }
  
  
  public static void main(String[] args) throws AgentCreationException {
    new TargetMVBidder("localhost", 2122);
    while(true){}
  }
  
}