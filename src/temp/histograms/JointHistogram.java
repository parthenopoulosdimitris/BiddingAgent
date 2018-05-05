package temp.histograms;

import java.util.HashMap;
import java.util.Map;

/**
 * Joint Histogram class creates a histogram of possible prices for all goods, where
 * prices among goods are dependent.
 * @author andrew
 *
 */
public class JointHistogram implements IHistogram  {
  
  private Integer numGoods;
  private Double[] minimumValue; 
  private Double[] maximumValue; 
  private Integer[] numBins;
  private Map<Integer[], Integer> bins;
  
  /**
   * a joint histogram with symmetric fields.
   * @param numGoods
   * the number of goods in the histogram. 
   * @param minVal
   * the minimum recorded value of any given good
   * @param maxVal
   * the maximum recorded value of any given good. 
   * @param binSize
   * the size of a bin. 
   * @param numBins
   * the number of bins along an axis. 
   */
  public JointHistogram(Integer numGood, Double minVal, Double maxVal, 
      Integer numBins) {
    this.numGoods = numGood; 
    this.minimumValue = new Double[numGoods];
    this.maximumValue = new Double[numGoods];
    this.numBins = new Integer [numGoods];
    for(int i = 0; i < numGoods; i++) {
      this.minimumValue[i] = minVal;
      this.maximumValue[i] = maxVal;
      this.numBins[i] = numBins;
    }
   this.bins = new HashMap<>();
  }
  
  /**
   * a joint histogram with custom fields. Only restriction is that all input arrays must
   * of length numGoods (dimensions)
   * @param numGoods
   * @param minVal
   * @param maxVal
   * @param binSize
   * @param numBins
   */
  public JointHistogram(Integer numGood, Double[] minVal, Double[] maxVal, Double[] binSize, 
      Integer[] numBins) {
   try {
     this.numGoods = numGood; 
     this.minimumValue = new Double[numGoods];
     this.maximumValue = new Double[numGoods];
     this.numBins = new Integer [numGoods];
     for(int i = 0; i < numGoods; i++) {
       this.minimumValue[i] = minVal[i];
       this.maximumValue[i] = maxVal[i];
       this.numBins[i] = numBins[i];
     }
     this.bins = new HashMap<>();
   }
   catch (ArrayIndexOutOfBoundsException e){ 
     System.out.println("Error: input array out of bounds. " + e);
   } 
  }
  
  /**so the histogram already exists virtually, we can just fill in a bin
   *  when it is needed. 
   * eureka: it is okay to access the hashmap. 
   */
  public void increment(Double[] value) {
    Integer[] position = new Integer[this.numGoods];
    for(int i = 0; i < numGoods; i++) {
      Double dimValue = value[i];
      Double size = (maximumValue[i] - minimumValue[i]) / (double) numBins[i];
      if(dimValue > minimumValue[i] && dimValue < maximumValue[i]) {
        Integer binLocation = (int) (dimValue / size);
        position[i] = binLocation; 
      }
      else {  
        position[i] = -1;
      }
      if (bins.containsKey(position)) {
        Integer val = bins.get(position);
        bins.put(position, val + 1);
      }
      else {
        bins.put(position, 1);
      }
    }
  }
  
  public Map<Integer[], Integer> getHistogram() {
    return this.bins;
  }
  
}