package temp.price;

import temp.histograms.JointHistogram;

/**
 * an Independent histogram is wrapped to form an independent 
 * distribution of prices.
 * @author andrew
 *
 */
public class JointDist {
  
  public final JointHistogram rep;
  
  public JointDist(JointHistogram dist) {
    this.rep = dist; 
  }
}