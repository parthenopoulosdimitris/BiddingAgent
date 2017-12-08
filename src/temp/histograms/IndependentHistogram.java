package temp.histograms;

import java.util.HashMap;
import java.util.Map;

/*
 * the bins implementation may cause errors.
 */
public class IndependentHistogram implements IHistogram {
  
  private Double minimumValue; 
  private Double maximumValue; 
  private Double binSize;
  private Integer numBins;
  private Map<Integer, Integer> bins;
  //TODO: normalize
  
  public IndependentHistogram(Double minVal, Double maxVal,
      Integer numBins) {
    this.minimumValue = minVal;
    this.maximumValue = maxVal;
    this.binSize = (maxVal - minVal) / numBins;
    this.numBins = numBins;
    this.bins = new HashMap<Integer, Integer>();
    for(int i = 0; i < numBins; i++) {
      bins.put(i, 0);
    }
  }
  
  public void increment(Double value) {
    Integer binLocation;
      if(value > minimumValue && value < maximumValue) {
        binLocation = (int) (value / this.binSize);
        Integer val = bins.get(binLocation);
        bins.put(binLocation, val + 1);
      }
    }
  
  public void setBin(Integer bin, Integer newVal) {
    if(bin < 0 || bin > this.numBins) {
      System.out.println("HISTOGRAM ERROR: out-of-bounds bin");
    }
    else{ 
      this.bins.put(bin, newVal);
    }
  }
  
  public Double getMin() {
    return this.minimumValue;
  }
  
  public Double getMax() {
    return this.maximumValue;
  }
  
  public Map<Integer, Integer> getHistogram() {  
    return this.bins;
  }
  
}