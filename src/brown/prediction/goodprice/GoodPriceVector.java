package brown.prediction.goodprice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Datatype for a vector of goods associated with prices. 
 * Is used for price prediction strategies as well as 
 * actual bidding strategies.
 * @author acoggins
 *TODO: make more secure.
 */
public class GoodPriceVector<T, U> implements Iterable<GoodPrice> {
  
  private Map<IGood, IPrice> priceMap;
  
  /**
   * empty constructor
   */
  public GoodPriceVector() {
    this.priceMap = new HashMap<IGood, IPrice>();
  }
  
  /**
   * constructor with another GoodpriceVector
   * @param p
   * an existing GoodPriceVector
   */
  public GoodPriceVector (GoodPriceVector<T, U> p) {
    this.priceMap = new HashMap<IGood, IPrice>();
    this.addAll(p);
  }
  
  /**
   * A Constructor with a hashmap of goods to Double. 
   * @param aMap
   * HashMap of Goods to Double.
   */
  public GoodPriceVector(Map<IGood, IPrice> aMap) {
    this.priceMap = new HashMap<IGood, IPrice>(aMap);
    }
  
  /**
   * adds a goodprice to the vector.
   * @param val
   */
  public void add(GoodPrice<T, U> val) {
    priceMap.put(val.getGood(), val.getPrice());
  }
  
  /**
   * adds a good and a double to the vector as a GoodPrice
   * @param good
   * @param price
   */
  public void add(IGood good, IPrice price) {
    priceMap.put(good, price);
  }
  
  /**
   * clears the GoodPriceVector.
   */
  public void clear() {
    priceMap = new HashMap<IGood, IPrice>();
  }
  
  /**
   * returns a contains for a Good
   * @param good
   * some good
   * @return
   * a Boolean for contains
   */
  public Boolean contains(IGood good) {
    return priceMap.containsKey(good);
  }
  
  /**
   * returns a contains for a GoodPrice
   * @param aGood
   * a GoodPrice
   * @return
   * a Boolean for contains
   */
  public Boolean contains(GoodPrice<T, U> aGood) {
    return priceMap.containsKey(aGood.getGood());
  }
  
  /**
   * gets a goodPrice based on a good.
   * @param good
   * some good
   * @return
   * GoodPrice associated with that good.
   */
  public GoodPrice<T, U> getGoodPrice(IGood good) {
    return new GoodPrice<T, U>(good, priceMap.get(good));
  }
  
  /**
   * gets all goods
   * @return
   * all the goods in the vector in a set.
   */
  public Set<IGood> getGoods() {
    return priceMap.keySet();
  }
  
  /**
   * gets a good or defaults to a default value
   * @param good
   * some good
   * @param defVal
   * some default value. 
   * @return
   * a price associated with a good
   */
  public IPrice getOrDefault(IGood good, IPrice defVal) {
    if(priceMap.containsKey(good)) {
      return priceMap.get(good);
    }
    else {
      return defVal;
    }
  }
  
  /**
   * returns if the GoodPrice is empty. 
   * @return
   * Boolean, if it is empty or not.
   */
  public Boolean isEmpty() {
    return (priceMap.isEmpty());
  }
  
  /**
   * adds all goods and Doubles in a map to a vector
   * @param goods
   * a map from a good to a double.
   */
  public void addAll(Map<IGood, IPrice> goods) {
    priceMap.putAll(goods);
  }
  
  /**
   * adds twoGoodPriceVector together. 
   * @param predictions
   * aGoodPriceVector. 
   */
  public void addAll(GoodPriceVector<T, U> predictions) {
    for(GoodPrice<T, U> p : predictions) {
      this.add(p);
    }
  }
  
  /**
   * removes a GoodPrice
   * @param good
   * a GoodPrice
   */
  public void remove(GoodPrice good) {
    priceMap.remove(good.getGood());
  }
  
  /**
   * gives the size of the GoodPriceVector.
   * @return
   * the vector's size.
   */
  public Integer size() {
    return priceMap.size();
  }
  
  /**
   * returns the GoodPriceVector as an array of goodPrice
   * @return
   * an array of GoodPrice
   */
  public GoodPrice<T, U>[] toArray() {
    GoodPrice<T, U>[] priceArray = new GoodPrice[this.size()];
    int i = 0; 
    for(IGood good : priceMap.keySet()) {
      priceArray[i] = this.getGoodPrice(good);
      i++;
    }
    return priceArray;
  }
  
  /**
   * iterator over GoodPriceVector.
   */
  public Iterator<GoodPrice> iterator() {
    GoodPriceIterator v = new GoodPriceIterator(this);
    return v;
}

  @Override
  public String toString() {
    return "PredictionVector [priceMap=" + priceMap + "]";
  }
  

  
  
}
