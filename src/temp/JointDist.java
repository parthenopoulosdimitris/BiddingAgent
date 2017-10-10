package temp;

import temp.histograms.JointHistogram;

/**
 * an Independent histogram is wrapped to form an independent 
 * distribution of prices.
 * @author andrew
 *
 */
public class JointDist {
  
  public final JointHistogram dist;
  
  public JointDist(JointHistogram dist) {
    this.dist = dist; 
  }
}