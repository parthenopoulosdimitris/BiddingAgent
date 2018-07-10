package temp.predictions;


import java.util.Set;

import brown.mechanism.tradeable.ITradeable;
import temp.representation.APriceRep;

public interface IDistributionPrediction extends IPricePrediction {
  
  /**
   * gets the mean prediction over a set of input goods. 
   * These means will be produced from distributions
   * over the good price preductions.
   * @param goods
   * a set of tradeables
   * @return
   * a representation of the mean prediction for the input goods.
   */
  public APriceRep getMeanPrediction(Set<ITradeable> goods);
  
  /**
   * gets a random prediction over the set of input goods. 
   * These random predictions will be produced from distributions
   * over the good price predictions.
   * @param goods
   * a set of tradeables. 
   * @return
   * a representation of the mean prediction for the input goods.
   */
  public APriceRep getRandomPrediction(Set<ITradeable> goods);  
}