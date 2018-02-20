package temp.agent; 

import temp.predictors.library.SCPPPoint;
import brown.channels.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.ValuationInformationMessage;
import brown.setup.ISetup;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;

public class SCPPPointAgent extends MaxPredictAgent {

  public SCPPPointAgent(String host, int port, ISetup gameSetup) throws AgentCreationException {
    super(host, port, gameSetup);
  }

  @Override
  public void onSimpleSealed(AuctionChannel channel) {
  // TODO: decide how to bid.
  
    
  }
  
  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {   
    if (privateInfo instanceof ValuationInformationMessage) {
      Logging.log("[-] Valuation Info Received");
      this.tradeables = ((ValuationInformationMessage) privateInfo).getTradeables();
      this.valuation = ((ValuationInformationMessage) privateInfo).getPrivateValuation();
      this.vDistribution = ((ValuationInformationMessage) privateInfo).getAllValuations();
      this.maximizer = null; 
      this.predictor = new SCPPPoint(null, this.tradeables.size(), 100, 100, 0.1, 
          (AdditiveValuationDistribution) this.vDistribution); 
      for (ITradeable t: this.tradeables){
        Logging.log("Agent " + this.ID + ", Good: " + t.toString() + ", Value: " +this.valuation.getValuation(t));
      }      
    } else {
      Logging.log("[x] AbsSSSPAgent: Wrong Kind of PrivateInformation Received");
    }
  } 
  
  public static void main(String[] args) throws AgentCreationException {
      // predictor and maximizer rely on private information.
      new SCPPPointAgent("localhost", 2121, new SSSPSetup()); 
  }
  
  
}