package brown.prediction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GoodPriceVector implements Iterable<GoodPrice> {
  
  private Map<Good, Double> priceMap;
  
  public GoodPriceVector() {
    this.priceMap = new HashMap<Good, Double>();
  }
  
  
  public GoodPriceVector (GoodPriceVector p) {
    this.priceMap = new HashMap<Good, Double>();
    this.addAll(p);
  }
  
  public GoodPriceVector(Map<Good, Double> aMap) {
    this.priceMap = new HashMap<Good, Double>(aMap);
    }
  
  public void add(GoodPrice val) {
    priceMap.put(val.getGood(), val.getPrice());
  }
  
  public void add(Good good, Double price) {
    priceMap.put(good, price);
  }
  
  public void clear() {
    priceMap = new HashMap<Good, Double>();
  }
  
  public Boolean contains(Good good) {
    return priceMap.containsKey(good);
  }
  
  public Boolean contains(GoodPrice aGood) {
    return priceMap.containsKey(aGood.getGood());
  }
  
  public GoodPrice getGoodPrice(Good good) {
    return new GoodPrice(good, priceMap.get(good));
  }
  
  public Double getOrDefault(Good good, Double defVal) {
    if(priceMap.containsKey(good)) {
      return priceMap.get(good);
    }
    else {
      return defVal;
    }
  }
  
  public Boolean isEmpty() {
    return (priceMap.isEmpty());
  }
  
  public void addAll(Map<Good, Double> goods) {
    priceMap.putAll(goods);
  }
  
  public void addAll(GoodPriceVector predictions) {
    for(GoodPrice p : predictions) {
      this.add(p);
    }
  }
  
  public void remove(GoodPrice good) {
    priceMap.remove(good.getGood());
  }
  
  public Integer size() {
    return priceMap.size();
  }
  
  public GoodPrice[] toArray() {
    GoodPrice[] priceArray = new GoodPrice[this.size()];
    int i = 0; 
    for(Good good : priceMap.keySet()) {
      priceArray[i] = this.getGoodPrice(good);
      i++;
    }
    return priceArray;
  }
  
  public Iterator<GoodPrice> iterator() {
    GoodPriceIterator v = new GoodPriceIterator(this);
    return v;
}

  @Override
  public String toString() {
    return "PredictionVector [priceMap=" + priceMap + "]";
  }
  

  
  
}
