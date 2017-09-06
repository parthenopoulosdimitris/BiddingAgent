package brown.prediction.goodprice;

import java.util.AbstractMap.SimpleEntry;

import brown.generatepredictions.Price;

/**
 * the basic datatype for a good attached to a price
 * that is used for the backend bidding logic.
 * @author acoggins
 *
 */
public class GoodPrice<T, U> {
  
  private SimpleEntry<IGood, IPrice> entry;
  
  /**
   * Constructor with a good and a price. 
   * @param good
   * the good that is to be entered into the GoodPrice.
   * @param price
   * that good's price.
   */
  public GoodPrice(IGood good, IPrice price) {
    this.entry = new SimpleEntry<IGood, IPrice>(good, price);
  }


  public IPrice getPrice() {
	  return entry.getValue();
  }

  public void setPrice(IPrice newPrice) {
	  this.entry.setValue(newPrice);
  }

  public IGood getGood() {
	  return entry.getKey();
  }

@Override
public String toString() {
  return "GoodPrice [" + entry + "]";
}
  
  
}