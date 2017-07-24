package brown.prediction;

import java.util.AbstractMap.SimpleEntry;

/**
 * the basic datatype for a good attached to a price
 * that is used for the backend bidding logic.
 * @author acoggins
 *
 */
public class GoodPrice {
  
  private SimpleEntry<Good, Double> entry;
  
  /**
   * Constructor with a good and a price. 
   * @param good
   * the good that is to be entered into the GoodPrice.
   * @param price
   * that good's price.
   */
  public GoodPrice(Good good, Double price) {
    this.entry = new SimpleEntry<Good, Double>(good, price);
  }
  
  /**
   * get the good. 
   * @return the good
   */
  public Good getGood() {
    return entry.getKey();
  }
  
  /**
   * get the price
   * @return the price as a double. 
   */
  public Double getPrice() {
    return entry.getValue();
  }
  
  /**
   * set the price to an input price. 
   * @param newPrice
   * a new price.
   */
  public void setPrice(Double newPrice) {
    entry.setValue(newPrice);
  }

  @Override
  public String toString() {
    return "GoodPrice [entry=" + entry + "]";
  }
  
  
}