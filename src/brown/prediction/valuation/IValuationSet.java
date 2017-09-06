package brown.prediction.valuation; 

import java.util.Iterator;
import java.util.Map;

import brown.valuable.IValuable;

public interface IValuationSet {
  
  public void add(IValuation val);
  
  public void add(IValuable item, Double price);
  
  public void clear();
  
  public Boolean contains(IValuable item);
  
  public IValuation getValuation(IValuable item);
  
  public Double getOrDefault(IValuable item, Double defVal);
  
  public Boolean isEmpty();
  
  public void addAll(IValuationSet vals);
  
  public void remove(IValuation val);
  
  public void remove(IValuable item);
  
  public Integer size();
  
  public IValuation[] toArray();
  
  public Iterator<IValuation> iterator();
  
}