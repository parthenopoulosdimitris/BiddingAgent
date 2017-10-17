package temp.predictions;


import temp.representation.APriceRep;

public interface IDistributionPrediction extends IPricePrediction {
  
  public APriceRep getMeanPrediction();
  
  public APriceRep getRandomPrediction();
      
}