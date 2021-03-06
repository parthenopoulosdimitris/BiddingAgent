package temp.finalproject.beta; 

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.spectrumauctions.sats.core.model.mrvm.MRVMGenericDefinition;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLicense;
import org.spectrumauctions.sats.opt.model.mrvm.demandquery.MRVMDemandQueryMipResult;
import org.spectrumauctions.sats.opt.model.mrvm.demandquery.MRVM_DemandQueryMIP;

import brown.user.agent.library.AbsCombinatorialProjectAgentV2;
import brown.user.agent.library.T1CombAgent;

import java.util.Set;


public class FinalProjectBetaAgent extends AbsCombinatorialProjectAgentV2 {
  
  
  private Set<Integer> bundle = new HashSet<Integer>();
  private double bundleValue;

  public FinalProjectBetaAgent(String host, int port, String name) {
    super(host, port, name);
  }

  @Override
  public Set<Integer> onBidRound() {  
    // bid for our bundle, if it's price isn't too high
    if (getBundlePrice(bundle) < bundleValue) {
      return bundle;
    } else {
      return new HashSet<Integer>();
    }
  }

  @Override
  public void onBidResults(double[] allocations) {
    // do nothing
  }

  @Override
  public void onAuctionStart() {    
    Map<Set<Integer>, Double> favorite = getFavoriteBundle(); 
    for (Set<Integer> bund : favorite.keySet()) {
      bundle = bund; 
      bundleValue = favorite.get(bund);
    }
  }

  @Override
  public void onAuctionEnd(Set<Integer> finalBundle) {

    bundle.clear();
    bundleValue = 0;
  }
  
  /**
   * gets favorite bundle at current prices. 
   * @return
   * the favorite bundle from an agent's valuation at the current prices. 
   */
  public Map<Set<Integer>, Double> getFavoriteBundle() {
    Map<Set<Integer>, Double> returnMap = new HashMap<Set<Integer>, Double>(); 
    Set<Integer> returnSet = new HashSet<Integer>(); 
    // create the map. 
    Map<MRVMGenericDefinition, BigDecimal> em = new HashMap<MRVMGenericDefinition, BigDecimal>();
    double[] p = this.prices; 
    // from our prices, create a set of MRMGenericDefinition associated with the prices. 
    // map of liecenses to IDs. Assuming an injective mapping.
    Map<MRVMLicense, Integer> licenseToId = new HashMap<MRVMLicense, Integer>(); 
    for (int i= 0; i < p.length; i++) { 
      licenseToId.put(this.idToLicense.get(i), i);
      MRVMGenericDefinition genericDef = new MRVMGenericDefinition(this.idToLicense.get(i).getBand(), this.idToLicense.get(i).getRegion()); 
      em.put(genericDef, new BigDecimal(p[i]));
    }
    // create and solve. 
    MRVM_DemandQueryMIP mip = new MRVM_DemandQueryMIP(this.valuation, em); 
    MRVMDemandQueryMipResult result = mip.getResult();
    Map<MRVMGenericDefinition, Integer> quant = result.getResultingBundle().getQuantities(); 
    BigDecimal price = result.getResultingBundle().getValue(); 
    //convert back into usable form.
    for (MRVMGenericDefinition def : quant.keySet()) {
       for (MRVMLicense l: this.idToLicense.values()) {
         if (l.getBand().equals(def.getBand()) && l.getRegion().equals(def.getRegion())) {
           returnSet.add(licenseToId.get(l)); 
         }
       }
    }
    returnMap.put(returnSet, price.doubleValue());
    return returnMap; 
  }
  
  public static void main(String[] args) {
    new FinalProjectBetaAgent("localhost", 2121, "agent1");
    new T1CombAgent("localhost", 2121, "agent2"); 
    new T1CombAgent("localhost", 2121, "agent3"); 
    new T1CombAgent("localhost", 2121, "agent4"); 
    new T1CombAgent("localhost", 2121, "agent5"); 
    new T1CombAgent("localhost", 2121, "agent6"); 
    new T1CombAgent("localhost", 2121, "agent7"); 
    while (true) {}
  }
}