package temp.agent;


import brown.channels.library.SimpleAuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.Registration;
import brown.messages.ValuationRegistration;
import brown.messages.markets.GameReport;
import brown.setup.Logging;
import brown.setup.Setup;
import brown.valuation.library.AdditiveValuation;
import brown.valuationrepresentation.SimpleValuation;
import temp.MetaVal;
import temp.maximizers.IMaximizer;
import temp.predictors.IPredictor;

/**
 * this represents an actual implementation of a Maximizer Predictor Agent.
 * @author andrew
 *
 */
public class MaxPredictDemo extends MaxPredictAgent {
  protected SimpleValuation myValuation;
  protected AdditiveValuation allValuations;
  
  private IMaximizer maximizer; 
  private IPredictor predictor; 
  private MetaVal distInfo; 
  
  public MaxPredictDemo(String host, int port, Setup gameSetup,
      IMaximizer max, IPredictor pred) throws AgentCreationException {
    super(host, port, gameSetup, max, pred);
    this.maximizer = max;
    this.predictor = pred;
  }

  

  @Override
  public void onSimpleSealed(SimpleAuctionChannel arg0) {
    // fill in auction operation here
  }
  
  @Override
  public void onSimpleOpenOutcry(SimpleAuctionChannel arg0) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void onMarketUpdate(GameReport arg0) {
    //TODO: log market outcome
    Logging.log("Market outcome:"); 
  }
  
  @Override
  public void onRegistration(Registration registration) {
    super.onRegistration(registration);
    if(registration instanceof ValuationRegistration) {
      ValuationRegistration valuationRegistration = (ValuationRegistration) registration;
      this.myValuation = (SimpleValuation) valuationRegistration.getValuation();
      this.allValuations = (AdditiveValuation) valuationRegistration.getDistribution();
      Logging.log("[+] new values: " + valuationRegistration.getValuation());
    }
    else {
      Logging.log("ERROR: Expected valuation registration");
    }
  }
  
}