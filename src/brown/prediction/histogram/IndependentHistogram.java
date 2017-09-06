package brown.prediction.histogram; 

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * an independent histogram stores data in bins. used for
 * the case where goods' values are independent of one another
 * @author acoggins
 *
 */
public class IndependentHistogram implements IHistogram {
  
  private Double minimumValue; 
  private Double maximumValue; 
  private Double binSize;
  private Integer numBins;
  private Map<Integer, Bin> bins; 
  
  /**
   * an Independent histogram is initialized with a minimum value, 
   * a maximum value, and a number of bins. 
   * @param min
   * minimum value of the histogram
   * @param max
   * maximum value of the histogram
   * @param numBins
   * number of bins inside the histogram. 
   */
  public IndependentHistogram(Double min, Double max, Integer numBins) {
    this.minimumValue = min; 
    this.maximumValue = max; 
    this.binSize = (max - min) / (double)numBins;
    this.numBins = numBins;
    this.bins = new HashMap<>();
    
    this.create();
  }
  
  /**
   * creates the histogram.
   */
  private void create() {
    Double upperBound; 
    Integer binID = 0;
    Double placeHolder = this.minimumValue;
    Double spaceBetween = 1.0 / Double.MAX_VALUE; 
    for(int i = 0; i < numBins; i++) { 
      upperBound = placeHolder + binSize - spaceBetween; 
      Bin aBin = new Bin(binID, placeHolder, upperBound);
      bins.put(binID, aBin);
      placeHolder += binSize; 
      binID++;
    }
  }
  
  /**
   * given a value, returns the corresponding bin ID
   * @param value
   * @return
   */
  private Integer corresponding(Double value) {
    if(value < this.minimumValue || value > this.maximumValue) {
      return -1; 
    }
    Integer id = (int) (value / binSize);
    return id;
  }
  
  /**
   * gets the minimum value of the histogram.
   * @return
   */
  public Double getMinimum() {
    return this.minimumValue;
  }
  
  /**
   * gets the maximum value of the histogram.
   * @return
   */
  public Double getMaximum() {
    return this.maximumValue; 
  }
  
  /**
   * gets the number of bins in the histogram.
   * @return
   */
  public Integer getNumBins() {
    return this.numBins; 
  }
  
  /**
   * gets the size of the bins.
   * @return
   */
  public Double getBinSize() {
    return this.binSize;
  }
  
  /**
   * gets a bin by its ID
   * @param ID
   * @return
   */
  public Bin getBinByID(Integer ID) {
    return bins.get(ID);
  }
  
  /**
   * gets a bin by its value.
   * @param value
   * a value corresponding to a bin value.
   * @return
   */
  public Bin getBinByValue(Double value) {
    return bins.get(corresponding(value));
  }
  
  /**
   * gets all bins in the histogram and returns them in
   * a sorted ArrayList. 
   * @return
   * Sorted list of bins.
   */
  public List<Bin> getAllBins() {
    List<Bin> allBins = new ArrayList<>();
    for(Bin b : bins.values()) {
      allBins.add(b);
    }
    Collections.sort(allBins, new SortID());
    return allBins;
  }
  
  /**
   * increments the bin corresponding to the input value. 
   * @param value
   */
  public void add(Double value) {
    if (value >= this.minimumValue && value < this.maximumValue) {
    Bin changed = bins.get(corresponding(value));
    changed.increment();
    bins.put(corresponding(value), changed); 
    }
  }
  
  /**
   * decrements the bin corresponding to the input value. 
   * @param value
   */
  public void remove(Double value) {
    if (value >= this.minimumValue && value < this.maximumValue) {
    Bin changed = bins.get(corresponding(value));
    changed.decrement();
    bins.put(corresponding(value), changed);
    }
  }
  
  public void setBin(Bin b) {
    bins.put(b.getID(), b);
  }
  
  public void clear() {
    this.bins = new HashMap<Integer, Bin>();
  }
  
  

}