package pl.kalisz.szpital.kancelaria.data.pojo;

import pl.kalisz.szpital.kancelaria.data.filerepo.FilepathLocationStrategy;
import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * A factory for creating Transaction objects.
 */
public final class TransactionFactory {

  /** The last transaction. */
  private Transaction lastTransaction;

  /** The default transaction. */
  private Transaction defaultTransaction;

  /** The builder. */
  private TransactionBuilder builder = new TransactionBuilder();

  /**
   * Sets the last transaction.
   * 
   * @param lastTransaction the new last transaction
   */
  private void setLastTransaction(Transaction lastTransaction) {
    if (lastTransaction == null) {
      this.lastTransaction = defaultTransaction;
    } else {
      this.lastTransaction = lastTransaction;
    }
  }

  private void setDefaultTransaction(User u) {
    defaultTransaction = builder.defaultTransaction(u).build();
  }

  /**
   * Instantiates a new transaction factory.
   */
  public TransactionFactory(User u, Transaction lastTransaction) {
    setDefaultTransaction(u);
    setLastTransaction(lastTransaction);
  }

  /**
   * Gets the filepath.
   * 
   * @return the filepath
   */
  private String getFilepath() {
    String plikSciezka = null;
    try {
      plikSciezka = new FilepathLocationStrategy().getFile(lastTransaction).getAbsolutePath();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    return plikSciezka;
  }

  /**
   * Gets the transaction.
   * 
   * @param type the type
   * @return the transaction
   * @throws Exception the exception
   */
  public Transaction getTransaction(final String type) throws Exception {
    if (Strings.NOWE_ZDARZENIE.equals(type)) {
      return defaultTransaction;
    } else if (Strings.SKOPIUJ_POPRZEDNIE_ZDARZENIE.equals(type)) {
      return new TransactionBuilder(lastTransaction).id(null).plikSciezka(null).build();
    } else if (Strings.SKOPIUJ_POPRZEDNIE_ZDARZENIE_I_SKAN.equals(type)) {
      return new TransactionBuilder(lastTransaction).id(null).plikSciezka(getFilepath()).build();
    } else if (Strings.SKOPIUJ_TYLKO_ADRES.equals(type)) {
      return new TransactionBuilder(defaultTransaction).adres(lastTransaction.getAdres()).build();
    } else if (Strings.SKOPIUJ_TYLKO_TYP_PISMA.equals(type)) {
      return new TransactionBuilder(defaultTransaction).typPisma(lastTransaction.getTypPisma())
          .build();
    } else {
      throw new Exception("Missing Transaction Type in Transaction Factory: " + type);
    }
  }
}
