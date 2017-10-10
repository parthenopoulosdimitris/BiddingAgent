package temp.agent;

import java.util.HashMap;
import java.util.Map;


import brown.exceptions.AgentCreationException;
import brown.markets.SimpleAuction;
import brown.messages.Registration;
import brown.messages.markets.GameReport;
import brown.registration.ValuationRegistration;
import brown.setup.Logging;
import brown.setup.Setup;
import brown.valuable.library.Tradeable;
import temp.MetaVal;
import temp.maximizers.IMaximizer;
import temp.predictors.IPredictor;

/**
 * this represents an actual implementation of a Maximizer Predictor Agent.
 * @author andrew
 *
 */
public class MaxPredictDemo extends MaxPredictAgent {

  protected Map<Tradeable, Double> myValuation;
  private IMaximizer maximizer; 
  private IPredictor predictor; 
  private MetaVal distInfo; 
  
  public MaxPredictDemo(String host, int port, Setup gameSetup,
      IMaximizer max, IPredictor pred) throws AgentCreationException {
    super(host, port, gameSetup, max, pred);
    this.myValuation = new HashMap<Tradeable, Double>();
    this.maximizer = max;
    this.predictor = pred;
  }
  
  public MaxPredictDemo(String host, int port, Setup gameSetup,
      IMaximizer max, IPredictor pred, MetaVal distInfo)
          throws AgentCreationException {
    super(host, port, gameSetup, max, pred);
    this.myValuation = new HashMap<Tradeable, Double>();
    this.maximizer = max;
    this.predictor = pred;
    this.distInfo = distInfo;
  }

  @Override
  public void onSimpleSealed(SimpleAuction arg0) {
    // fill in auction operation here!
  }
  
  @Override
  public void onSimpleOpenOutcry(SimpleAuction arg0) {
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
    ValuationRegistration valuationRegistration = (ValuationRegistration) registration;
    this.myValuation.putAll(valuationRegistration.getValues());
    Logging.log("[+] new XOR values: " + valuationRegistration.getValues());
  }
  
}