package pl.kalisz.szpital.kancelaria.data.enums;

import org.junit.Assert;
import org.junit.Test;

import pl.kalisz.szpital.db.kancelaria.enums.TypWiadomosci;

/**
 * The Class TypWiadomosciTest.
 */
public class TypWiadomosciTest {

  /**
   * Gets the test.
   */
  @Test
  public void getTest() {
    TypWiadomosci tw = TypWiadomosci.WYCHODZĄCE;
    Assert.assertTrue(tw.getNazwa().contains("Wycho"));
    Assert.assertTrue(!tw.isPolecony());
    Assert.assertTrue(!tw.isWewnetrzny());
    Assert.assertTrue(!tw.isArchiwalny());
    Assert.assertTrue(!tw.isPrzychodzacy());
  }

  /**
   * By values test.
   */
  @Test
  public void byValuesTest() {
    Assert.assertEquals(TypWiadomosci.getByValues(false, true, true),
        TypWiadomosci.PRZYCHODZĄCE_WEWNĘTRZNE);
    Assert.assertEquals(TypWiadomosci.getByValues(true, false, false),
        TypWiadomosci.WYCHODZĄCE_POLECONE);
    Assert.assertEquals(TypWiadomosci.getByValues(false, false, true),
        TypWiadomosci.PRZYCHODZĄCE_ZEWNĘTRZNE);
    Assert.assertEquals(TypWiadomosci.getByValues(false, false, false), TypWiadomosci.WYCHODZĄCE);
  }


  /**
   * By title test.
   */
  @Test
  public void byTitleTest() {
    Assert.assertEquals(TypWiadomosci.getByTytul("Przychodzące zewnętrzne"),
        TypWiadomosci.PRZYCHODZĄCE_ZEWNĘTRZNE);
  }

  /**
   * Gets the filter test.
   */
  @Test
  public void getFilterTest() {
    Assert.assertTrue(TypWiadomosci.WYCHODZĄCE.getFilter() != null);
  }

}
