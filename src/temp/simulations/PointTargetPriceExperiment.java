package temp.simulations;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import brown.user.server.SSSPServerBundleValuations;

/**
 * Run an auction with an agent that does SCPP Point. 
 * Compare this to 
 * 1. a randomly bidding agent
 * 2. an agent that solves the solution analytically. 
 * @author andrew
 *
 */

//thoughts: 
//more goods? 
//maybe this would be a good place to implement a cognitive hierarchy
//
public class PointTargetPriceExperiment {
  private static int numSims;

  
  private static String[] botClasses = new String[] {
      "temp.agent.RandomAgent",
      "temp.agent.SCPPPointV2Agent",
  };
  
  private static int numBots = 5;
  private static String host = "localhost";
  private static int port = 2121;
  private String outFile; 
  
  public PointTargetPriceExperiment(String outFile, int nSims) {
    this.outFile = outFile;
    numSims = nSims;
  }
  
  public void run() throws InterruptedException {
      ServerRunnable sr = new ServerRunnable();
      AgentRunnable ar = new AgentRunnable("SCPP Agent");
      BotsRunnable br = new BotsRunnable("Random Agent");
      
      ar.setTier(1);
      br.setTier(0);
      
      Thread st = new Thread(sr);
      Thread bt = new Thread(br);
      Thread at = new Thread(ar); 
      
      st.start();
      bt.start();
      at.start();
      
      TimeUnit.SECONDS.sleep(numSims * 5);
      st.interrupt();
      bt.interrupt();
      at.interrupt();
  }
  
  public class ServerRunnable implements Runnable {
    
    
    @Override
    public void run() {
      DateFormat df = new SimpleDateFormat("MM.dd.yyyy '-' HH:mm:ss");
      String outfile = outFile +"-" + df.format(new Date());
      
      SSSPServerBundleValuations server = 
          new SSSPServerBundleValuations();
      try {
        server.runAll(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  public static class BotsRunnable implements Runnable {
    private int tier;
    private String botName;
    
    public BotsRunnable(String name) { 
      this.botName = name;
    }
    
    @Override
    public void run() { 
      try {
        String botClass = botClasses[tier];
        Class<?> cl = Class.forName(botClass);
        Constructor<?> cons = cl.getConstructor(String.class, Integer.TYPE, String.class);
        
        for (int i = 0; i < numBots; i++) {
          cons.newInstance(host, port + tier, this.botName);
        }
        
        while (true) {}
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    public void setTier(int tier) {
      this.tier = tier;
    }
  }
  
  public static class AgentRunnable implements Runnable {
    private int tier;
    private String agentName; 
    
    public AgentRunnable(String name) { 
      this.agentName = name;
    }
    
    @Override
    public void run() { 
      try {
        String botClass = botClasses[tier];
        Class<?> cl = Class.forName(botClass);
        Constructor<?> cons = cl.getConstructor(String.class, Integer.TYPE, String.class);
        cons.newInstance(host, port, this.agentName);
        while (true) {}
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    public void setTier(int tier) {
      this.tier = tier;
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    PointTargetPriceExperiment p = new PointTargetPriceExperiment("experiment_results.txt", 1);
    p.run();
  }
}