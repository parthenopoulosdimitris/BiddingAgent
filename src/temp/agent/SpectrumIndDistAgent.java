package temp.agent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.exceptions.AgentCreationException;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.valuation.IValuation;
import brown.value.valuation.library.AdditiveValuation;
import temp.maximizers.IMaxDist;
import temp.predictors.library.SCPPIndDist;

public class SpectrumIndDistAgent extends AbsCombinatorialProjectAgentV2 {

  private IValuation trimmedValuation; 
  
  public SpectrumIndDistAgent(String host, int port) throws AgentCreationException {
    super(host, port);
    Map<SimpleTradeable, Double> trimmedValuationMap = new HashMap<SimpleTradeable, Double>(); 
    for (int i = 1; i <= 98; i++) {
      Set<Integer> individualGood = new HashSet<Integer>(); 
      individualGood.add(i);
      trimmedValuationMap.put(new SimpleTradeable(i), this.queryValue(individualGood));
    }
    this.trimmedValuation = new AdditiveValuation(trimmedValuationMap);
  }

  @Override
  public void onAuctionEnd(Set<Integer> arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onAuctionStart() {
    // 1. SCPP
    SCPPIndDist predictor = new SCPPIndDist(null, 98, this); 
  }

  @Override
  public void onBidResults(double[] arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Set<Integer> onBidRound() {
    // TODO Auto-generated method stub
    return null;
  }
  
}