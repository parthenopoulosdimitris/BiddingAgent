package temp.finalproject;

import brown.user.server.CombAuctionServer;

public class CombAuctionServerTemplate {
  private static int initDelay = 5;  // time to wait before beginning the simulation
  private static int initLag = 5000;
  private static int lag = 100; // time between intervals in which bots can trade, should probably leave this high to be safe
  private static int nSims = 1;  // number of simulations
  private static double increment = 20.; // how much prices increment between rounds
  private static String file = null; //if you want to write results to somewhere

  public static void main(String[] args) throws InterruptedException {
    CombAuctionServer server = new CombAuctionServer(initDelay, initLag, lag, 2121, nSims, increment, file);
    server.runAll();
  }
}