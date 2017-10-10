package temp.agent;



import brown.agent.Agent;
import brown.exceptions.AgentCreationException;
import brown.markets.ContinuousDoubleAuction;
import brown.messages.Ack;
import brown.messages.BankUpdate;
import brown.messages.auctions.BidRequest;
import brown.messages.trades.NegotiateRequest;
import brown.setup.Logging;
import brown.setup.Setup;
import temp.MetaVal;
import temp.maximizers.IMaximizer;
import temp.predictors.IPredictor;

/**
 * a class of 'smart' agent that uses a maximizer and a predictor. 
 * to implement, extend this agent with another agent that implements
 * onSimpleSealed and onOpenOutcry. Implementation of the agents'
 * core logic should be left to maximizers and predictors.
 * @author andrew
 *
 */
public abstract class MaxPredictAgent extends Agent {

  /**
   * constructor without distributional info.
   * @param host
   * @param port
   * @param gameSetup
   * @param max
   * @param pred
   * @throws AgentCreationException
   */
  public MaxPredictAgent(String host, int port, Setup gameSetup,
      IMaximizer max, IPredictor pred)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  /**
   * constructor with distributional info. 
   * @param host
   * @param port
   * @param gameSetup
   * @param max
   * @param pred
   * @param distInfo
   * @throws AgentCreationException
   */
  public MaxPredictAgent(String host, int port, Setup gameSetup,
      IMaximizer max, IPredictor pred, MetaVal distInfo)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  @Override
  public void onAck(Ack message) {
    if (message.REJECTED) {
      Logging.log("[x] rej: " + message.failedBR);
    }
  }

  @Override
  public void onBankUpdate(BankUpdate bankUpdate) {
    Logging.log("[-] bank " + bankUpdate.newAccount.monies);
    if (bankUpdate.newAccount.tradeables.size() > 0) {
      Logging.log("[+] victory!");
    }
  }

  @Override
  public void onContinuousDoubleAuction(ContinuousDoubleAuction arg0) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onTradeRequest(BidRequest arg0) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onNegotiateRequest(NegotiateRequest arg0) {
    // TODO Auto-generated method stub 
  }

}