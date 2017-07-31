package brown.prediction.priceprediction;


import brown.prediction.good.GoodDistVector;




public class SimpleDistPrediction implements IIndependentPrediction {

  private GoodDistVector predictions;
  
  
  public SimpleDistPrediction() {
    this.predictions = new GoodDistVector();
  }
  
  public SimpleDistPrediction(GoodDistVector p) {
    this.predictions = p;
  }
  
  @Override
  public GoodDistVector getPrediction() {
    return predictions;
  }
  
  @Override
  public void setPrediction(GoodDistVector aPrediction) {
    this.predictions = aPrediction;
  }

}
