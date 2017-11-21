package temp.agent;

import brown.agent.AbsAgent;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

/**
 * an agent which uses a bid strategy and a prediction strategy, 
 * but not predictors or maximizers.
 * @author andrew
 *
 */
public abstract class PredStratAgent extends AbsAgent {

  public PredStratAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }
  
}