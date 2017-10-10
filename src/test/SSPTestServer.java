package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import brown.agent.TestAgent;
import brown.exceptions.AgentCreationException;
import brown.generator.library.NormalGenerator;
import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;
import brown.valuation.library.AdditiveValuation;
import temp.MetaVal;
import temp.DistributionType;

public class SSPTestServer {
  
  private Map<Integer, TestAgent> agents; 
  private Set<Tradeable> goods;
  private MetaVal distInfo;
  
  
  public SSPTestServer(List<TestAgent> inAgents, Integer numGoods, MetaVal distInfo)
      throws AgentCreationException {
    this.distInfo = distInfo;
    agents = new HashMap<Integer, TestAgent>();
    goods = new HashSet<Tradeable>();
    for (TestAgent a : inAgents) { 
      agents.put(inAgents.indexOf(a), a);
    }
    for(int i = 0; i < numGoods; i++)
      goods.add(new Tradeable(i));
  }
   
  public void run() { 
    Map<Tradeable, Double[]> best = new HashMap<Tradeable, Double[]>();
    Integer firstPlace = 0; 
    for(Tradeable t : this.goods) {
      Double[] val = new Double[2];
      val[0] = 0.0; 
      val[1] = 0.0;
      best.put(t, val);
    }
    Map<Tradeable, Value> valuation = this.getValuation(this.goods);
    for(TestAgent a : this.agents.values()) {
      Map<Tradeable, Double> bid = a.onTestSSAdd(valuation);
      for(Tradeable t : bid.keySet()) {
        if(bid.get(t) > best.get(t)[0]) {
          Double[] val = best.get(t);
          val[0] = bid.get(t);
          best.put(t, val);
        }
        else if(bid.get(t) <= best.get(t)[0] && bid.get(t) > best.get(t)[1]) {
          Double[] val = best.get(t);
          val[1] = bid.get(t);
          best.put(t, val);
        }
      }
    }
    //now, print out the winner and his result.
    System.out.println(best);
  }
  
  private Map<Tradeable, Value> getValuation(Set<Tradeable> goods) { 
    NormalGenerator norm = new NormalGenerator(distInfo.getValFunction(), distInfo.getScale());
    AdditiveValuation valuation = new AdditiveValuation(norm, goods);
    return valuation.getValuation(goods);
  }
  
  public static void main(String[] args) throws AgentCreationException {
    final int AGENTS = 5; 
    final int GOODS = 10;
    final MetaVal meta = new MetaVal(DistributionType.Normal, 3.0, 0.2,
        x -> (double) x, true, 1.0);
    List<TestAgent> agents = new ArrayList<TestAgent>();
    for(int i = 0; i < AGENTS; i++) {
      agents.add(new DummyTestAgent());
    }
    for(int i = 0; i < 10; i++) {
      
    }
    SSPTestServer s = new SSPTestServer(agents, GOODS, meta);
    s.run();
  }
}