
package brown.prediction.good;

import brown.assets.value.FullType;
import brown.assets.value.TradeableType;

/**
 * The good to be used in the back-end bidding logic. 
 * Consists only of the private ID of the agent.
 * @author acoggins
 *
 */
public class Good {
  
  public final Integer ID; 
  
  /**
   * empty constructor.
   */
  public Good() {
    this.ID = null; 
  }
  
  /**
   * constructor with ID.
   * @param id
   */
  public Good(Integer id) {
    this.ID = id; 
  }
  
  /**
   * converts to trading platform datatype FullType.
   * For agents to convert backend datatypes to TP datatypes. 
   * In some future where the trading platform becomes agnostic, 
   * this would be useless. 
   * @return
   */
  public FullType toFullType() {
    return new FullType(TradeableType.Good, ID);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Good other = (Good) obj;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Good [ID=" + ID + "]";
  }

  
  
  
}