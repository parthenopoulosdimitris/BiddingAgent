package temp.predictors.library; 

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.generator.library.UniformValGenerator;
import temp.maximizers.library.TargetPrice;

public class SCPPPointTest {
  
  @Test
  public void testSCPPPoint() {
    // tradeables
    Set<ITradeable> t = new HashSet<ITradeable>();
    for (int i = 0; i < 10; i++) {
      t.add(new SimpleTradeable(i));
    }
    SCPPPoint scp = new SCPPPoint(new TargetPrice(), 10, 10, 10, 0.1, new AdditiveValuationDistribution(new UniformValGenerator(), t));
  }
}