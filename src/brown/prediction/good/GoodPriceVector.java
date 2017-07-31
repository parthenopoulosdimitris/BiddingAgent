package brown.prediction.good;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Datatype for a vector of goods associated with prices. 
 * Is used for price prediction strategies as well as 
 * actual bidding strategies.
 * @author acoggins
 *
 */
public class GoodPriceVector implements Iterable<GoodPrice> {
  
  private Map<Good, Double> priceMap;
  
  /**
   * empty constructor
   */
  public GoodPriceVector() {
    this.priceMap = new HashMap<Good, Double>();
  }
  
  /**
   * constructor with another GoodpriceVector
   * @param p
   * an existing GoodPriceVector
   */
  public GoodPriceVector (GoodPriceVector p) {
    this.priceMap = new HashMap<Good, Double>();
    this.addAll(p);
  }
  
  /**
   * A Constructor with a hashmap of goods to Double. 
   * @param aMap
   * HashMap of Goods to Double.
   */
  public GoodPriceVector(Map<Good, Double> aMap) {
    this.priceMap = new HashMap<Good, Double>(aMap);
    }
  
  /**
   * adds a goodprice to the vector.
   * @param val
   */
  public void add(GoodPrice val) {
    priceMap.put(val.getGood(), val.getPrice());
  }
  
  /**
   * adds a good and a double to the vector as a GoodPrice
   * @param good
   * @param price
   */
  public void add(Good good, Double price) {
    priceMap.put(good, price);
  }
  
  /**
   * clears the GoodPriceVector.
   */
  public void clear() {
    priceMap = new HashMap<Good, Double>();
  }
  
  /**
   * returns a contains for a Good
   * @param good
   * some good
   * @return
   * a Boolean for contains
   */
  public Boolean contains(Good good) {
    return priceMap.containsKey(good);
  }
  
  /**
   * returns a contains for a GoodPrice
   * @param aGood
   * a GoodPrice
   * @return
   * a Boolean for contains
   */
  public Boolean contains(GoodPrice aGood) {
    return priceMap.containsKey(aGood.getGood());
  }
  
  /**
   * gets a goodPrice based on a good.
   * @param good
   * some good
   * @return
   * GoodPrice associated with that good.
   */
  public GoodPrice getGoodPrice(Good good) {
    return new GoodPrice(good, priceMap.get(good));
  }
  
  /**
   * gets all goods
   * @return
   * all the goods in the vector in a set.
   */
  public Set<Good> getGoods() {
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
  public Double getOrDefault(Good good, Double defVal) {
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
  public void addAll(Map<Good, Double> goods) {
    priceMap.putAll(goods);
  }
  
  /**
   * adds twoGoodPriceVector together. 
   * @param predictions
   * aGoodPriceVector. 
   */
  public void addAll(GoodPriceVector predictions) {
    for(GoodPrice p : predictions) {
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
  public GoodPrice[] toArray() {
    GoodPrice[] priceArray = new GoodPrice[this.size()];
    int i = 0; 
    for(Good good : priceMap.keySet()) {
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
