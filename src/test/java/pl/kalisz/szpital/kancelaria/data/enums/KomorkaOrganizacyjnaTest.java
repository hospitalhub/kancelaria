package pl.kalisz.szpital.kancelaria.data.enums;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class KomorkaOrganizacyjnaTest.
 */
public class KomorkaOrganizacyjnaTest {

  /**
   * Load test.
   */
  @Test
  public void loadTest() {
    KomorkaOrganizacyjna ko = KomorkaOrganizacyjna.getByKod("L4");
    Assert.assertEquals(ko.getKod(), "L4");
    Assert.assertTrue(ko.getNazwa().contains("ZUS"));
    Assert.assertTrue(KomorkaOrganizacyjna.contains("DN"));
  }


}
