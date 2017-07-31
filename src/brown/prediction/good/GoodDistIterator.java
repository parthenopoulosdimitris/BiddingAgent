package brown.prediction.good; 

import java.util.Iterator;

/**
 * iterator for the GoodDist datatype within a GoodDistVector.
 * For now it is read-only so you will have to work with the 
 * PredictionVector itself to make changes with the iterator, 
 * since the iterator works by iterating over an array copy of 
 * the GoodDistVector datatype.
 * @author acoggins
 *
 */
public class GoodDistIterator implements Iterable<GoodDist>, Iterator<GoodDist> {
  
  private GoodDist[] priceArray;
  private int index;
  
  /**
   * iterator constuctor.
   * @param valSet
   * a GoodDistVector.
   */
  public GoodDistIterator(GoodDistVector valSet) {
    this.priceArray = valSet.toArray(); 
    this.index = 0; 
  }

  /**
   * determines whether or not there is a next element.
   */
  public boolean hasNext() {
    return index < priceArray.length;
  }
  
  /**
   * gets the next element in the array.
   */
  public GoodDist next() {
    return priceArray[index++];
  }
  
  /**
   * throws an exception.
   */
  public void remove() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * the iterator itself.
   */
  public Iterator<GoodDist> iterator() {
    return this;
  }

}
