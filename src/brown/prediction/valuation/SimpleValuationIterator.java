package brown.prediction.valuation; 

import java.util.Iterator;

/**
 * Iterator for ValuationBundle class. is currently read-only, meaning that it
 * creates an array copy and iterates over that. 
 * @author acoggins
 *
 */
public class SimpleValuationIterator implements 
Iterable<SimpleValuation>, Iterator<SimpleValuation>{
  
  private SimpleValuation[] valArray;
  private int index;
  
  /**
   * Valuation iterator constructor.
   * @param valSet
   * a ValuationBundle to be iterated over. 
   */
  public SimpleValuationIterator(SimpleValuationBundle valSet) {
    this.valArray = valSet.toArray(); 
    this.index = 0; 
  }

  /**
   * determines whether the value array has a next element. 
   */
  public boolean hasNext() {
    return index < valArray.length;
  }
  
  /**
   * returns the next element in valArray.
   */
  public SimpleValuation next() {
    return valArray[index++];
  }
  
  /**
   * throws an exception.(?)
   */
  public void remove() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * calls the valuation iterator for use on ValuationBundle.
   */
  public Iterator<SimpleValuation> iterator() {
    return this;
  }

}