package brown.generatepredictions;

import java.util.HashSet;
import java.util.Set;

import brown.agent.AbsAgent;
import brown.tradeable.library.Tradeable;


public abstract class Constants {
	static final int NUM_GOODS = 8; 
	static final int NUM_PRICES = 100;
	static final int NUM_ITERATIONS=100;
	static final double MAX_VAL=1.0;
	static final double MIN_VAL=0.0;
	//given
	static final int NUM_AGENTS=8;
	//given
	static final Set<Tradeable> goodSet= new HashSet<Tradeable>();
	//given
	static final Set<AbsAgent> agentSet = new HashSet<AbsAgent>();
}
