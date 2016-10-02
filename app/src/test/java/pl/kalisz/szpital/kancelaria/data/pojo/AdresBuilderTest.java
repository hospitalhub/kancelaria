package pl.kalisz.szpital.kancelaria.data.pojo;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class AdresBuilderTest.
 */
public class AdresBuilderTest {


  /** The Constant FIRMA. */
  private static final String FIRMA = "Siatex Import-Export";
  
  /** The Constant UL. */
  private static final String UL = "ul. Janka Bonka";
  
  /** The Constant NAZWISKO. */
  private static final String NAZWISKO = "Pompa";
  
  /** The Constant IMIE. */
  private static final String IMIE = "Jan";
  
  /** The Constant MIASTO. */
  private static final String MIASTO = "Bzdziny Dolne";
  
  /** The Constant KOD. */
  private static final String KOD = "01-666";

  /**
   * Builds the test.
   */
  @Test
  public void buildTest() {
    AdresBuilder adresBuilder = new AdresBuilder();
    Adres a = adresBuilder.adres(UL, KOD, MIASTO).osoba(IMIE, NAZWISKO).build();
    Assert.assertEquals(a.getNazwisko(), NAZWISKO);
    Assert.assertEquals(a.getImie(), IMIE);
    Assert.assertEquals(a.getMiasto(), MIASTO);
    Assert.assertEquals(a.getKod(), KOD);
    Assert.assertEquals(a.getUlica(), UL);
  }

  /**
   * Build2 test.
   */
  @Test
  public void build2Test() {
    AdresBuilder adresBuilder = new AdresBuilder();
    Adres a = adresBuilder.firma(FIRMA).kontaktowe("1", "2", "3", "mail").build();
    Assert.assertEquals(a.getFirma(), FIRMA);
    Assert.assertEquals(a.getFax(), "3");
    Assert.assertEquals(a.getMail(), "mail");
  }


}
