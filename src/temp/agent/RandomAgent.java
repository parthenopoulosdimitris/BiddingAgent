package temp.agent;

import java.util.HashMap;
import java.util.Map;

import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.channel.library.SealedBidChannel;
import brown.mechanism.tradeable.ITradeable;
import brown.system.setup.library.SSSPSetup;
import brown.user.agent.library.AbsLab02Agent;

public class RandomAgent extends AbsLab02Agent {

  public RandomAgent(String host, int port) {
    super(host, port, new SSSPSetup());
  }
  
  public RandomAgent(String host, int port, String name) {
    super(host, port, new SSSPSetup(), name);
  }

  @Override
  public void onSimpleSealed(SealedBidChannel channel) {
    Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>(); 
    for (ITradeable t : this.tradeables) {
      bidMap.put(t, new BidType(Math.random(), 1)); 
    }
    channel.bid(this, new AuctionBidBundle(bidMap)); 
  }
  
  public static void main(String[] args) {
    // predictor and maximizer rely on private information.
    new RandomAgent("localhost", 2121); 
    while(true){}
}

  
}