
package brown.interfaces;

import java.util.Set;

import brown.prediction.Good;

public interface IValuation {

  double getValuation(Good g);
  double getValuation(Set<Good> goods);

}