package brown.generatepredictions;

public class Price {
  
	private Double price;
	
	public Price(double amt) {
	  this.price = amt;
	}
	
	public Double getPrice() {
	  return price; 
	}
	public void setPrice(double newPrice){
		price=newPrice;
	}
	
}
