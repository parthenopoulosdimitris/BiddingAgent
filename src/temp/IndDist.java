package temp;

import temp.histograms.IndependentHistogram;

/**
 * an Independent histogram is wrapped to form an independent 
 * distribution of prices.
 * @author andrew
 *
 */
public class IndDist {
  
  public final IndependentHistogram dist;
  
  public IndDist(IndependentHistogram dist) {
    this.dist = dist; 
  }
}