
package brown.prediction.valuation;

import java.util.Set;

import brown.prediction.good.Good;

public interface IValuation {

  double getValuation(Set<Good> goods);

}