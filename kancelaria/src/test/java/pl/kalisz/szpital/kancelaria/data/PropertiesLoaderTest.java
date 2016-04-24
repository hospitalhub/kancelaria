package pl.kalisz.szpital.kancelaria.data;

import org.junit.Assert;
import org.junit.Test;

import pl.kalisz.szpital.kancelaria.utils.PropertiesLoader;

/**
 * The Class PropertiesLoaderTest.
 */
public class PropertiesLoaderTest {

  /**
   * Load test.
   */
  @Test
  public void loadTest() {
    Assert.assertTrue(PropertiesLoader.loadProperty("plik.adresow").endsWith("csv"));
  }
}
