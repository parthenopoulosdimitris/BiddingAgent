package temp.price;

import temp.histograms.KDE;

/**
 * an Independent histogram is wrapped to form an independent 
 * distribution of prices.
 * @author andrew
 *
 */
public class JointDistKDE {
  
  public final KDE rep;
  
  public JointDistKDE(KDE dist) {
    this.rep = dist; 
  }  
}