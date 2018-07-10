package temp.agent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.user.agent.library.AbsCombinatorialProjectAgentV2;
import temp.maximizers.library.LocalBidIndDist;
import temp.maximizers.library.TargetPriceDist;
import temp.predictors.library.SCPPIndDist;

public class SpectrumIndDistAgent extends AbsCombinatorialProjectAgentV2 {

  private Map<ITradeable, Double> trimmedValuation; 
  private Map<ITradeable, Double> bid; 
  
  public SpectrumIndDistAgent(String host, int port, String name) {
    super(host, port, name);
  }

  @Override
  public void onAuctionEnd(Set<Integer> arg0) {
    // TODO Auto-generated method stub
    Map<ITradeable, Double> trimmedValuationMap = new HashMap<ITradeable, Double>(); 
    for (int i = 1; i <= 98; i++) {
      Set<Integer> individualGood = new HashSet<Integer>(); 
      individualGood.add(i);
      trimmedValuationMap.put(new SimpleTradeable(i), this.queryValue(individualGood));
    }
    this.trimmedValuation = trimmedValuationMap;
  }

  @Override
  public void onAuctionStart() {
    Map<ITradeable, Double> trimmedValuationMap = new HashMap<ITradeable, Double>(); 
    Set<Integer> individualGood = new HashSet<Integer>(); 
    for (int i = 1; i <= 98; i++) {
      individualGood.add(i);
      trimmedValuationMap.put(new SimpleTradeable(i), this.queryValue(individualGood));
      individualGood.clear();
    }
    this.trimmedValuation = trimmedValuationMap;
    // 1. SCPP
    SCPPIndDist predictor = new SCPPIndDist(new LocalBidIndDist(new TargetPriceDist()), 98, this); 
    LocalBidIndDist maximizer = new LocalBidIndDist(new TargetPriceDist());
    Map<ITradeable, Double> bid = maximizer.getBids(this.trimmedValuation, predictor.getPrediction());
    this.bid = bid; 
  }

  @Override
  public void onBidResults(double[] arg0) {
    
  }

  @Override
  public Set<Integer> onBidRound() {
    Set<Integer> toBid = new HashSet<Integer>(); 
    Set<Integer> indSet = new HashSet<Integer>(); 
    for(ITradeable t : this.bid.keySet()) {
      indSet.add(t.getID());
      if (this.getBundlePrice(indSet) 
          <= this.bid.get(t)) {
        toBid.add(t.getID());
      }
      indSet.clear(); 
    }
    return toBid;
  }
  
  public static void main(String[] args) {
    new SpectrumIndDistAgent("localhost", 2121, "agent1");
//    new T1CombAgent("localhost", 2121, "agent2"); 
//    new T1CombAgent("localhost", 2121, "agent3"); 
//    new T1CombAgent("localhost", 2121, "agent4"); 
//    new T1CombAgent("localhost", 2121, "agent5"); 
//    new T1CombAgent("localhost", 2121, "agent6"); 
//    new T1CombAgent("localhost", 2121, "agent7"); 
    while (true) {}
  }
}