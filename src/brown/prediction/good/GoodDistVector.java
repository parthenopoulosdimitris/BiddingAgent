package brown.prediction.good; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import brown.prediction.histogram.IndependentHistogram;

public class GoodDistVector implements Iterable<GoodDist> {

   private Map<Good, IndependentHistogram> histMap;
    
    /**
     * empty constructor
     */
    public GoodDistVector() {
      this.histMap = new HashMap<Good, IndependentHistogram>();
    }
    
    /**
     * constructor with another GoodDistVector
     * @param p
     * an existing GoodDistVector
     */
    public GoodDistVector (GoodDistVector p) {
      this.histMap = new HashMap<Good, IndependentHistogram>();
      this.addAll(p);
    }
    
    /**
     * A Constructor with a hashmap of goods to IndependentHistogram. 
     * @param aMap
     * HashMap of Goods to IndependentHistogram.
     */
    public GoodDistVector(Map<Good, IndependentHistogram> aMap) {
      this.histMap = new HashMap<Good, IndependentHistogram>(aMap);
      }
    
    /**
     * adds a GoodDist to the vector.
     * @param val
     */
    public void add(GoodDist val) {
      histMap.put(val.getGood(), val.getDist());
    }
    
    /**
     * adds a good and a IndependentHistogram to the vector as a GoodDist
     * @param good
     * @param price
     */
    public void add(Good good, IndependentHistogram price) {
      histMap.put(good, price);
    }
    
    /**
     * clears the GoodDistVector.
     */
    public void clear() {
      histMap = new HashMap<Good, IndependentHistogram>();
    }
    
    /**
     * returns a contains for a Good
     * @param good
     * some good
     * @return
     * a Boolean for contains
     */
    public Boolean contains(Good good) {
      return histMap.containsKey(good);
    }
    
    /**
     * returns a contains for a GoodDist
     * @param aGood
     * a GoodDist
     * @return
     * a Boolean for contains
     */
    public Boolean contains(GoodDist aGood) {
      return histMap.containsKey(aGood.getGood());
    }
    
    /**
     * gets a GoodDist based on a good.
     * @param good
     * some good
     * @return
     * GoodDist associated with that good.
     */
    public GoodDist getGoodDist(Good good) {
      return new GoodDist(good, histMap.get(good));
    }
    
    /**
     * gets all goods
     * @return
     * all the goods in the vector in a set.
     */
    public Set<Good> getGoods() {
      return histMap.keySet();
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
    public IndependentHistogram getOrDefault(Good good, IndependentHistogram defVal) {
      if(histMap.containsKey(good)) {
        return histMap.get(good);
      }
      else {
        return defVal;
      }
    }
    
    /**
     * returns if the GoodDist is empty. 
     * @return
     * Boolean, if it is empty or not.
     */
    public Boolean isEmpty() {
      return (histMap.isEmpty());
    }
    
    /**
     * adds all goods and IndependentHistograms in a map to a vector
     * @param goods
     * a map from a good to a IndependentHistogram.
     */
    public void addAll(Map<Good, IndependentHistogram> goods) {
      histMap.putAll(goods);
    }
    
    /**
     * adds twoGoodDistVector together. 
     * @param predictions
     * aGoodDistVector. 
     */
    public void addAll(GoodDistVector predictions) {
      for(GoodDist p : predictions) {
        this.add(p);
      }
    }
    
    /**
     * removes a GoodDist
     * @param good
     * a GoodDist
     */
    public void remove(GoodDist good) {
      histMap.remove(good.getGood());
    }
    
    /**
     * gives the size of the GoodDistVector.
     * @return
     * the vector's size.
     */
    public Integer size() {
      return histMap.size();
    }
    
    /**
     * returns the GoodDistVector as an array of GoodDist
     * @return
     * an array of GoodDist
     */
    public GoodDist[] toArray() {
      GoodDist[] priceArray = new GoodDist[this.size()];
      int i = 0; 
      for(Good good : histMap.keySet()) {
        priceArray[i] = this.getGoodDist(good);
        i++;
      }
      return priceArray;
    }
    
    /**
     * iterator over GoodDistVector.
     */
    public Iterator<GoodDist> iterator() {
      GoodDistIterator v = new GoodDistIterator(this);
      return v;
  }
  
  
}