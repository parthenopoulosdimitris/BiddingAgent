package brown.prediction.good;

import java.util.AbstractMap.SimpleEntry;

import brown.prediction.histogram.IndependentHistogram;

/**
 * maps a good to a distribution over its possible prices, in the
 * form of a histogram.
 * @author acoggins
 *
 */
public class GoodDist {
  
  private SimpleEntry<Good, IndependentHistogram> entry; 
  
  /**
   * constructor for GoodDist. 
   * @param good
   * a good. 
   * @param hist
   * an Independent Histogram.
   */
  public GoodDist(Good good, IndependentHistogram hist) {
    this.entry = new SimpleEntry<Good, IndependentHistogram>(good, hist);
  }
  
  /**
   * get the good. 
   * @return the good
   */
  public Good getGood() {
    return entry.getKey();
  }
  
  /**
   * get the price distribution for the good.
   * @return the distribution as a histogram. 
   */
  public IndependentHistogram getDist() {
    return entry.getValue();
  }
  
  /**
   * set the distribution to an input histogram. 
   * @param newPrice
   * a new price.
   */
  public void setDist(IndependentHistogram newHist) {
    entry.setValue(newHist);
  }

  @Override
  public String toString() {
    return "GoodDist [entry=" + entry + "]";
  }

  
}