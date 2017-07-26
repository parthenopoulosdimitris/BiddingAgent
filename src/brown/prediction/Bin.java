package brown.prediction; 

/**
 * a bin within a histogram
 * @author acoggins
 *
 */
public class Bin {
  
  private Integer ID; 
  private Double minVal; 
  private Double maxVal;
  private Integer frequency; 
  
  /**
   * a bin has an ID, a minimum value, a maximum value, and a frequency. 
   * @param ID
   * @param min
   * @param max
   */
  public Bin(Integer ID, Double min, Double max) {
    this.ID = ID; 
    this.minVal = min;
    this.maxVal = max; 
    this.frequency = 0; 
  }
  
  /**
   * gets the bin's id
   * @return
   */
  public Integer getID() { 
    return ID;
  }
  
  /**
   * gets the bin's lower bound
   * @return
   */
  public Double getMin() {
    return minVal; 
  }
  
  /**
   * gets the bin's upper bound.
   * @return
   */
  public Double getMax() {
    return maxVal; 
  }
  
  /**
   * gets the interval of the bin
   * @return
   */
  public Double interval() {
    return maxVal - minVal; 
  }
  
  /**
   * returns the number of items in the bin.
   * @return
   */
  public Integer frequency() {
    return frequency; 
  }
  
  /**
   * increments the bin
   */
  public void increment() {
    frequency++;
  }
  
  /**
   * decrements the bin
   */
  public void decrement() {
    frequency--; 
  }
  
  /**
   * clears the bin
   */
  public void clear() {
    frequency = 0; 
  }

  @Override
  public String toString() {
    return "Bin [ID=" + ID + ", minVal=" + minVal + ", maxVal=" + maxVal
        + ", frequency=" + frequency + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
    result = prime * result + ((maxVal == null) ? 0 : maxVal.hashCode());
    result = prime * result + ((minVal == null) ? 0 : minVal.hashCode());
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
    Bin other = (Bin) obj;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    if (frequency == null) {
      if (other.frequency != null)
        return false;
    } else if (!frequency.equals(other.frequency))
      return false;
    if (maxVal == null) {
      if (other.maxVal != null)
        return false;
    } else if (!maxVal.equals(other.maxVal))
      return false;
    if (minVal == null) {
      if (other.minVal != null)
        return false;
    } else if (!minVal.equals(other.minVal))
      return false;
    return true;
  }
  
  
  
}