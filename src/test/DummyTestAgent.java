package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.agent.TestAgent;
import brown.exceptions.AgentCreationException;
import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

public class DummyTestAgent extends TestAgent {

  public DummyTestAgent() throws AgentCreationException {
    // TODO Auto-generated constructor stub
  }

  @Override
  public Map<Tradeable, Double> onTestSSAdd(Map<Tradeable, Value> arg0) {
    Map<Tradeable, Double> ret = new HashMap<Tradeable, Double>();
    
    for(Tradeable t : arg0.keySet()) {
      ret.put(t, arg0.get(t).value);
    }
    return ret;
  }

  public Map<Tradeable, Double>
      onTestSSBundle(Map<Set<Tradeable>, Value> arg0) {
    // TODO Auto-generated method stub
    return null;
  }
  
}