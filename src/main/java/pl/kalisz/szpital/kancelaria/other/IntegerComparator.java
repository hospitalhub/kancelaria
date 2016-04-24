package pl.kalisz.szpital.kancelaria.other;

import java.util.Comparator;

/**
 * The Class IntegerComparator.
 */
public class IntegerComparator implements Comparator<Object> {

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public int compare(Object arg0, Object arg1) {
    Integer i0 = (Integer) arg0;
    Integer i1 = (Integer) arg1;
    return i0.compareTo(i1);
  }

}
