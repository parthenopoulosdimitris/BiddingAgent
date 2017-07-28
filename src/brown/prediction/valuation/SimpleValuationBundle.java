package brown.prediction.valuation; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import brown.prediction.good.Good;


/**
 * Valuation bundle class. A group of Valuations. Is iterable
 * and has properties of both a hashmap and a set.
 * @author acoggins
 *
 */
public class SimpleValuationBundle implements Iterable<SimpleValuation> {
  
  private Map<Set<Good>, Double> valMap; 
  
  /**
   * constructs an empty valuation bundle.
   */
  public SimpleValuationBundle() {
    this.valMap = new HashMap<Set<Good>, Double>();
  }
  
  public SimpleValuationBundle(SimpleValuationBundle b) {
    this.valMap = new HashMap<Set<Good>, Double>();
    this.addAll(b);
  }
  
  public SimpleValuationBundle(Map<Set<Good>, Double> aMap) {
    this.valMap = new HashMap<Set<Good>, Double>(aMap);
    }
  
  /**
   * adds a valuation to the bundle. 
   * @param val
   * a valuation.
   */
  public void add(SimpleValuation val) {
    valMap.put(val.getGoods(), val.getPrice());
  }
  
  /**
   * adds a valuation to the bundle. 
   * @param goods
   * a set of fulltype, goods.
   * @param price
   * the price of the goods.
   */
  public void add(Set<Good> goods, Double price) {
    valMap.put(goods, price);
  }
  
  
  /**
   * empties the bundle.
   */
  public void clear() {
    valMap = new HashMap<Set<Good>, Double>();
  }
  
  /**
   * checks for the existence of a set of fulltype.
   * @param goods
   * a set of fulltype.
   * @return
   * true if the set is contained, false otherwise.
   */
  public Boolean contains(Set<Good> goods) {
    return valMap.containsKey(goods);
  }
  
  
  public Boolean contains(SimpleValuation aValuation) {
    return valMap.containsKey(aValuation.getGoods());
  }
  
  /**
   * gets a valuation, given a set of goods. Can be used to find a set of goods'
   * price. 
   * @param  private Map<FullType, Double> singleValMap; goods
   * a set of fulltype.
   * @return
   * the valuation associated with that type.
   */
  public SimpleValuation getValuation(Set<Good> goods) {
    return new SimpleValuation(goods, valMap.get(goods));
  }
  
  public Double getOrDefault(Set<Good> goods, Double defVal) {
    if(valMap.containsKey(goods)) {
      return valMap.get(goods);
    }
    else {
      return defVal;
    }
  }

  
  /**
   * 
   * @return
   * is the bundle empty or not.
   */
  public Boolean isEmpty() {
    return (valMap.isEmpty());
  }
  
  /**
   * adds a map from sets of fulltype to double to the valuation bundle.
   * @param vals
   * a map from sets of fulltype to double.
   */
  public void addAll(Map<Set<Good>, Double> vals) {
    valMap.putAll(vals);
  }
  
  
  /**
   * combines valuation bundle with input bundle. 
   * @param vals
   * another valuation bundle.
   */
  public void addAll(SimpleValuationBundle vals) {
    for(SimpleValuation v : vals) {
      this.add(v);
    }
  }
  
  /**
   * removes a specified valuation. 
   * @param val
   * a valuation
   */
  public void remove(SimpleValuation val) {
    valMap.remove(val.getGoods());
  }
  
  /**
   * returns the size of the bundle.
   * @return
   * the size of the bundle.
   */
  public Integer size() {
    return valMap.size();
  }
  
  /**
   * converts the bundle to an array. 
   * @return
   * an array of valuations.
   */
  public SimpleValuation[] toArray() {
    SimpleValuation[] valArray = new SimpleValuation[this.size()];
    int i = 0; 
    for(Set<Good> goods : valMap.keySet()) {
      valArray[i] = this.getValuation(goods);
      i++;
    }
    return valArray;
  }
  
  /**
   * iterator for valuationbundle
   * read-only, apparently. May need to fix this along the way somewhere. 
   */
  public Iterator<SimpleValuation> iterator() {
    SimpleValuationIterator v = new SimpleValuationIterator(this);
    return v;
}

  @Override
  public String toString() {
    return "SimpleValuationBundle " + valMap;
  }



}