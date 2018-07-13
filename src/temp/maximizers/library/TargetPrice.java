package temp.maximizers.library; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.ComplexTradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import temp.maximizers.IMaxPoint;
import temp.predictions.IPointPrediction;
import temp.price.Price;
import temp.representation.PointRep;

/**
 * only bid if it will contribute to the bottom line.
 * bids at the predicted price. 
 * @author acoggins
 *
 */
public class TargetPrice implements IMaxPoint {

  // if the good is in the aquisition, include it.
  // to find aquisition: loop through every bundle, find its value. 
  // find the price of all its goods. The best one goes. 
  @Override
  public Map<ITradeable, Double> getBids(Map<ITradeable, Double> valuations,
      IPointPrediction aPrediction) {
    // some annoying conversions.
    Map<ITradeable, Price> pred = ((PointRep) aPrediction.getPrediction(valuations.keySet())).rep; 
    Map<ITradeable, Double> returnMap = new HashMap<ITradeable, Double>(); 
    // check that the valuation is higher than the prediction and bid if it is.
    double highest = 0.0; 
    List<SimpleTradeable> highestGoods = new LinkedList<SimpleTradeable>(); 
    for (Entry<ITradeable, Double> valuation : valuations.entrySet()) {
      ITradeable t = valuation.getKey(); 
      List<SimpleTradeable> stList = t.flatten(); 
      double priceSum = 0.0; 
      for (SimpleTradeable st : stList) { 
        if (pred.containsKey(st)) {
          priceSum += pred.get(st).rep; 
        }
      }
      if (valuation.getValue() - priceSum > highest) {
        highestGoods = stList; 
      }
    }
    for (SimpleTradeable st : highestGoods) {
      returnMap.put(st, pred.get(st).rep); 
    }
    return returnMap;
  }
}