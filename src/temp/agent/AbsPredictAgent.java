package temp.agent;

import java.util.List;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.IValuation;
import brown.logging.library.Logging;
import brown.mechanism.channel.library.SealedBidChannel;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.messages.library.BankUpdateMessage;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.PrivateInformationMessage;
import brown.platform.messages.library.ValuationInformationMessage;
import brown.system.setup.ISetup;
import brown.user.agent.library.AbsLab02Agent;
import temp.maximizers.IMaximizer;
import temp.predictors.IPredictor;

public abstract class AbsPredictAgent extends AbsLab02Agent {

  protected List<ITradeable> tradeables; 
  protected IValuation valuation;
  protected IValuationDistribution vDistribution; 
  protected IMaximizer maximizer; 
  protected IPredictor predictor; 

  public AbsPredictAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }
  
  public abstract void onSimpleSealed(SealedBidChannel channel); 
  
  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {   
    if (privateInfo instanceof ValuationInformationMessage) {
      Logging.log("[-] Valuation Info Received");
      this.tradeables = ((ValuationInformationMessage) privateInfo).getTradeables();
      this.valuation = ((ValuationInformationMessage) privateInfo).getPrivateValuation();
      this.vDistribution = ((ValuationInformationMessage) privateInfo).getAllValuations();
      for (ITradeable t: this.tradeables){
        Logging.log("Agent " + this.ID + ", Good: " + t.toString() + ", Value: " +this.valuation.getValuation(t));
      }      
    } else {
      Logging.log("[x] AbsSSSPAgent: Wrong Kind of PrivateInformation Received");
    }
  }  
  
  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    Logging.log("BANKUPDATE: Agent: " + this.ID + ", " + bankUpdate.toString());
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    Logging.log("Game report received");
  }
  
  
  
  
}