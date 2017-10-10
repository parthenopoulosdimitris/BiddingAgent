package temp.agent;

import brown.agent.Agent;
import brown.exceptions.AgentCreationException;
import brown.setup.Setup;

/**
 * an agent which uses a bid strategy and a prediction strategy, 
 * but not predictors or maximizers.
 * @author andrew
 *
 */
public abstract class PredStratAgent extends Agent {

  public PredStratAgent(String host, int port, Setup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }
  
}