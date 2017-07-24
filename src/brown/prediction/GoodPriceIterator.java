package brown.prediction; 

import java.util.Iterator;

/**
 * iterator for the GoodPrice datatype within a PredictionVector.
 * For now it is read-only so you will have to work with the 
 * PredictionVector itself to make changes with the iterator, 
 * since the iterator works by iterating over an array copy of 
 * the PredictionVector datatype.
 * @author acoggins
 *
 */
public class GoodPriceIterator implements Iterable<GoodPrice>, Iterator<GoodPrice> {
  
  private GoodPrice[] priceArray;
  private int index;
  
  /**
   * iterator constuctor.
   * @param valSet
   * a PredictionVector.
   */
  public GoodPriceIterator(PredictionVector valSet) {
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
  public GoodPrice next() {
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
  public Iterator<GoodPrice> iterator() {
    return this;
  }

}
