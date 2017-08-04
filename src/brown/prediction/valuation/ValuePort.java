package brown.prediction.valuation; 

import java.util.HashSet;
import java.util.Set;

import brown.assets.value.FullType;
import brown.assets.value.TradeableType;
import brown.prediction.good.Good;
import brown.prediction.good.GoodDist;
import brown.prediction.good.GoodDistVector;
import brown.prediction.good.GoodPrice;
import brown.prediction.good.GoodPriceVector;
import brown.valuation.NormalValuation;
import brown.valuation.SizeDependentValuation;
import brown.valuation.UniformValuation;
import brown.valuation.Valuation;
import brown.valuation.ValuationBundle;

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
  private MetaVal metaVal;

  
  /**
   * constructor for a value port. currently using multiple objects
   * for the different distributions but this shouldn't really be 
   * necessary.
   * TODO: update jar and change from FullType to BasicType
   * @param metaVal
   * a meta-valuation that holds all the info of the underlying distribution
   * @param allGoods
   * all of the goods to be valued, input as a GoodPriceVector
   */
  public ValuePort(MetaVal metaVal, Set<Good> allGoods) {
    //create FullType 
    Set<FullType> allGoodsTP = new HashSet<FullType>();
    
    for(Good g : allGoods) {
      allGoodsTP.add(new FullType(TradeableType.Good, g.ID));
    }
    if(metaVal.getType() == DistributionType.Normal) {
      this.normalVal = new NormalValuation(allGoodsTP,
          metaVal.getValFunction(), metaVal.getVariance(),
          0.0, metaVal.getMonotonic(), metaVal.getScale());
      }
    else if (metaVal.getType() == DistributionType.Uniform) {
      //fill this in eventually when get uniform valuation.
    }
    else {
      this.sizeVal = new SizeDependentValuation(allGoodsTP,  
        metaVal.getValFunction(), metaVal.getScale());
    } 
  }
  
  
  /**
   * get all for normal distribution
   * @return
   */
  public SimpleValuationBundle getAllNormal() {
    ValuationBundle temp =  normalVal.getAllValuations();
    return convertToSimple(temp); 
  }
  
  /**
   * get one good from a normal distribution.
   * @param aGood
   * @return
   */
  public SimpleValuation getIndependentNormal(Good aGood) {
    Set<FullType> mockSet = new HashSet<FullType>(); 
    mockSet.add(new FullType(TradeableType.Good, aGood.ID));
    NormalValuation singleVal = new NormalValuation(mockSet, metaVal.getValFunction(),
        metaVal.getMonotonic(), metaVal.getScale());
    ValuationBundle intermediate = singleVal.getAllValuations();
    Set<Good> returnSet = new HashSet<Good>();
    returnSet.add(aGood);
    return new SimpleValuation(returnSet, intermediate.getOrDefault(mockSet, 0.0));
  }
  
  /**
   * get some for a normal distribution
   * @param numValuations
   * number of valuations 
   * @param valSizeMean
   * mean bundle size
   * @param valSizeStdDev
   * standard deviation of the valuation size.
   * @return
   */
  public SimpleValuationBundle getSomeNormal(Integer numValuations, 
      Integer valSizeMean, Double valSizeStdDev) {
    ValuationBundle temp = normalVal.getSomeValuations(numValuations, valSizeMean, 
        valSizeStdDev);
    return convertToSimple(temp);
  }
  
  /**
   * gets all valuations for a size dependent i.e. no variance
   * valuation. 
   * @return
   */
  public SimpleValuationBundle getAllSdp() {
    ValuationBundle temp = sizeVal.getAllValuations();
    return convertToSimple(temp);
  }
  
  /**
   * gets some valuations for a size dependent i.e. no variance
   * valuation
   * @param numValuations
   * number of valuations. 
   * @param valSizeMean
   * mean valuation size.
   * @param valSizeStdDev
   * standard deviation of the valuation size
   * @return
   */
  public SimpleValuationBundle getSomeSdp(Integer numValuations, Integer valSizeMean,
      Double valSizeStdDev) {
    ValuationBundle temp = sizeVal.getSomeValuations(numValuations, valSizeMean, 
        valSizeStdDev);
    return convertToSimple(temp);
  }
  
  /**
   * converts a valuation bundle to a simple valuation bundle. As in, 
   * from the trading platform datatype to the bidding agent datatype.
   * @param someBundle
   * @return
   */
  private SimpleValuationBundle convertToSimple(ValuationBundle someBundle) {
    SimpleValuationBundle converted = new SimpleValuationBundle(); 
    for(Valuation v : someBundle) {
      Set<Good> goodSet = new HashSet<Good>();
      for(FullType f : v.getGoods()) {
        goodSet.add(new Good(f.ID));
      }
      SimpleValuation s = new SimpleValuation(goodSet, v.getPrice());
      converted.add(s);
    }
    return converted; 
  }
  
  
}


