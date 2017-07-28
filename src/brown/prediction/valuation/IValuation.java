
package brown.prediction.valuation;

import java.util.Set;

import brown.prediction.good.Good;

public interface IValuation {

  public SimpleValuation getValuation(Set<Good> goods);

}