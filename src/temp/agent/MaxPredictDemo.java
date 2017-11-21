package temp.agent;


import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.GameReport;
import brown.messages.library.Registration;
import brown.messages.library.ValuationRegistration;
import brown.setup.ISetup;
import brown.setup.Logging;
import brown.value.valuation.library.AdditiveValuation;
import brown.value.valuationrepresentation.library.SimpleValuation;
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
  
  public MaxPredictDemo(String host, int port, ISetup gameSetup,
      IMaximizer max, IPredictor pred) throws AgentCreationException {
    super(host, port, gameSetup, max, pred);
    this.maximizer = max;
    this.predictor = pred;
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



  @Override
  public void onMarketUpdate(GameReport arg0) {
    // TODO Auto-generated method stub
    
  }



  @Override
  public void onSimpleOpenOutcry(SimpleAgentChannel arg0) {
    // TODO Auto-generated method stub
    
  }



  @Override
  public void onSimpleSealed(SimpleAgentChannel arg0) {
    // TODO Auto-generated method stub
    
  }
  
}