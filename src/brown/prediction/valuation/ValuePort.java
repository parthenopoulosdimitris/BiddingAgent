package brown.prediction.valuation; 

import java.util.HashSet;
import java.util.Set;

import brown.assets.value.FullType;
import brown.assets.value.TradeableType;
import brown.prediction.good.GoodPrice;
import brown.prediction.good.GoodPriceVector;
import brown.valuation.NormalValuation;
import brown.valuation.SizeDependentValuation;
import brown.valuation.UniformValuation;

/**
 * class that extrapolates data from the metavaluation to a value generator
 * of agnostic source. 
 * In this case, that source is the trading platform but this method can be 
 * edited to change that.
 * @author acoggins
 *
 */
public class ValuePort {
  
  private NormalValuation normalVal;
  private SizeDependentValuation sizeVal; 
  private UniformValuation uniVal;

  /**
   * constructor for a value port.
   * @param metaVal
   */
  public ValuePort(MetaVal metaVal, GoodPriceVector allGoods) {
    //create FullType 
    Set<FullType> allGoodsTP = new HashSet<FullType>();
    
    for(GoodPrice g : allGoods) {
      allGoodsTP.add(new FullType(TradeableType.Good, 0));
    }
    
    if(metaVal.getType() == DistributionType.Normal) {
        this.normalVal = new NormalValuation(allGoodsTP,
            metaVal.getValFunction(), metaVal.getVariance(),
            0.0, metaVal.getMonotonic(), metaVal.getScale());
        }
    
    else if (metaVal.getType() == DistributionType.Uniform) {
      
    }
    
    else {
      this.sizeVal = new SizeDependentValuation(allGoodsTP,  
          metaVal.getValFunction(), metaVal.getScale());
    }
    
  }
  
}