package brown.prediction.valuation; 

import brown.prediction.goodprice.IGood;
import brown.prediction.goodprice.IPrice;
import brown.valuable.IValuable;

/**
 * interface for 
 * @author acoggins
 *
 */
public interface IValuation {
  
  public IGood getValuable();
  
  public IPrice getPrice();
  
  public void setPrice(IPrice newPrice);
  
  
}