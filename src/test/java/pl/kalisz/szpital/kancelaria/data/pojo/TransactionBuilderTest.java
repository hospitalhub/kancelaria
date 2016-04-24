package pl.kalisz.szpital.kancelaria.data.pojo;

import org.junit.Assert;
import org.junit.Test;

import pl.kalisz.szpital.kancelaria.data.enums.TypWiadomosci;

/**
 * The Class TransactionBuilderTest.
 */
public class TransactionBuilderTest {


  /** The Constant UL. */
  private static final String UL = "ul. Janka Bonka";
  
  /** The Constant MIASTO. */
  private static final String MIASTO = "Bzdziny Dolne";
  
  /** The Constant KOD. */
  private static final String KOD = "01-666";

  /**
   * Builds the test.
   */
  @Test
  public void buildTest() {
    TransactionBuilder tBuilder = new TransactionBuilder();
    Adres a = new AdresBuilder().adres(UL, KOD, MIASTO).build();
    Transaction t = tBuilder.adres(a).id(1) //
        .typ(TypWiadomosci.WYCHODZĄCE) //
        .typPisma(new TypPisma("Pismo")).build();
    Assert.assertEquals(t.getAdres().getUlica(), UL);
  }


}
