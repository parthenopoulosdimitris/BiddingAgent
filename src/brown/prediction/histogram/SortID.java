package brown.prediction.histogram;

import java.util.Comparator;

/**
 * sorts bins by their id tags.
 * @author acoggins
 *
 */
public class SortID implements Comparator<Bin> {

  @Override
  public int compare(Bin b1, Bin b2) {
    // TODO Auto-generated method stub
    return Integer.compare(b1.getID(), b2.getID());
  }
  
}
