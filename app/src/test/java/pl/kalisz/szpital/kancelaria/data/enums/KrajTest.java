package pl.kalisz.szpital.kancelaria.data.enums;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class KrajTest.
 */
public class KrajTest {

  /**
   * Load test.
   */
  @Test
  public void loadTest() {
    Kraj k = Kraj.PL;
    Assert.assertTrue(k.getNazwa().equals("Polska"));
  }


}
