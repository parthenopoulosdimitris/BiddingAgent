package temp.histograms;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
    //TODO: need to normalize. 
    //System.out.println("INCREMENT TRYING TO OCCUR");
      if(value > minimumValue && value < maximumValue) {
        binLocation = (int) (value / this.binSize);
        Integer val = bins.get(binLocation);
        bins.put(binLocation, val + 1);
        //System.out.println("INCREMENT OCCURING");
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
  
  /**
   * gets the mean of the histogram.
   * @return
   */
  public double getMean() {
    double runningBinAmt = this.getMin(); 
    double midBin = this.binSize / 2.0; 
    double totalCount = 0.0; 
    int totalBins = 0; 
    for (int i = 0; i < this.bins.size(); i++) { 
      totalBins += this.bins.get(i);
      totalCount += ((double) this.bins.get(i)) * (runningBinAmt + midBin); 
      runningBinAmt += this.binSize;
    }
    if (totalBins > 0) {
      return totalCount / ((double) totalBins); 
    } else {
      return 0.0; 
    }
  }
  
  /**
   * samples randomly from the histogram.
   * @return
   */
  public double sample() {
    int totalBins = 0; 
    for(int i = 0; i  < this.bins.size(); i++) {
      totalBins += this.bins.get(i); 
    }
    int randomNum = ThreadLocalRandom.current().nextInt(0, totalBins);
    for(int i = 0; i < this.bins.size(); i++) {
      totalBins -= this.bins.get(i); 
      randomNum -= this.bins.get(i); 
      if (randomNum < 0) {
        // return the value stored at the bin. 
        return this.getMin() + ((double) i * (double) this.binSize) + (this.binSize / 2.0); 
      }
    }
    // should never happen. 
    return 0.0; 
  }
  
  @Override
  public String toString() {
    return "IndependentHistogram [minimumValue=" + minimumValue
        + ", maximumValue=" + maximumValue + ", binSize=" + binSize
        + ", numBins=" + numBins + ", bins=" + bins + "]";
  }
  
}