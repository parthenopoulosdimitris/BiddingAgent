package brown.prediction.goodprice;

import brown.prediction.histogram.IndependentHistogram;

public class Dist implements IPrice { 
	
	public IndependentHistogram dist; 
	
	public Dist(IndependentHistogram dist) { 
		this.dist = dist; 
	}
	
}