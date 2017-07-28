package brown.prediction.valuation; 

import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import brown.prediction.good.Good;


public class SimpleValuation {
  
  
  private SimpleEntry<Set<Good>, Double> entry;
  
  
  /**
   * constructor for a valuation over a set of goods.
   * @param goods
   * a set of fulltype. 
   * @param price
   * a price for this set.
   */
  public SimpleValuation(Set<Good> goods, Double price) {
    this.entry = new SimpleEntry<Set<Good>, Double>(goods, price);
  }
  
  /**
   * gets the goods associated with this valuation. 
   * @return
   * a set of FullType.
   */
  public Set<Good> getGoods() {
    return entry.getKey();
  }
  
  /**
   * get the price for a valuation.
   * @return
   * Double representing a price.
   */
  public Double getPrice() {
    return entry.getValue(); 
  }
  
  /**
   * set the price for a valuation
   * @param newPrice
   */
  public void setPrice(Double newPrice) {
      entry.setValue(newPrice);
  }
  
  /**
   * does the valuation contain the good?
   * @param good
   * @return
   * the FullType good.
   */
  public Boolean contains(Good good) {
    return this.getGoods().contains(good);
  }
  
  /**
   * returns the number of goods being valued.
   * @return
   * the number of goods being valued in this valuation.
   */
  public Integer size() {
    return entry.getKey().size();
  }
  

  @Override
  public String toString() {
      return "Valuation [entry=" + entry + "]";
  }
  
  
}