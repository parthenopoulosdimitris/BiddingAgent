package temp.price; 

public class Price {

  public final double rep;
  
  public Price(Double value) {
    this.rep = value; 
  }

  @Override
  public String toString() {
    return "" + rep;
  }
  
}