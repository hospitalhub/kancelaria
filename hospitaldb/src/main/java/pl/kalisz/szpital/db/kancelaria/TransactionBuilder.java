package pl.kalisz.szpital.db.kancelaria;

import java.util.Date;

import pl.kalisz.szpital.db.kancelaria.enums.TypWiadomosci;
import pl.kalisz.szpital.utils.Strings;

/**
 * The Class TransactionBuilder.
 */
public class TransactionBuilder {

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public final Long getId() {
    return id;
  }

  /**
   * Sets the id.
   * 
   * @param id the new id
   */
  public final void setId(final Long id) {
    this.id = id;
  }

  /**
   * Gets the data.
   * 
   * @return the data
   */
  public final Date getData() {
    return data;
  }

  /**
   * Sets the data.
   * 
   * @param data the new data
   */
  public final void setData(final Date data) {
    this.data = data;
  }

  /**
   * Gets the sygnatura.
   * 
   * @return the sygnatura
   */
  public final String getSygnatura() {
    return sygnatura;
  }

  /**
   * Sets the sygnatura.
   * 
   * @param sygnatura the new sygnatura
   */
  public final void setSygnatura(final String sygnatura) {
    this.sygnatura = sygnatura;
  }

  /**
   * Gets the typ pisma.
   * 
   * @return the typ pisma
   */
  public final TypPisma getTypPisma() {
    return typPisma;
  }

  /**
   * Sets the typ pisma.
   * 
   * @param typPisma the new typ pisma
   */
  public final void setTypPisma(final TypPisma typPisma) {
    this.typPisma = typPisma;
  }

  /**
   * Gets the odbiorca.
   * 
   * @return the odbiorca
   */
  public final String getOdbiorca() {
    return odbiorca;
  }

  /**
   * Sets the odbiorca.
   * 
   * @param odbiorca the new odbiorca
   */
  public final void setOdbiorca(final String odbiorca) {
    this.odbiorca = odbiorca;
  }

  /**
   * Gets the opis.
   * 
   * @return the opis
   */
  public final String getOpis() {
    return opis;
  }

  /**
   * Sets the opis.
   * 
   * @param opis the new opis
   */
  public final void setOpis(final String opis) {
    this.opis = opis;
  }

  /**
   * Gets the przychodzacy.
   * 
   * @return the przychodzacy
   */
  public final Boolean getPrzychodzacy() {
    return przychodzacy;
  }

  /**
   * Sets the przychodzacy.
   * 
   * @param przychodzacy the new przychodzacy
   */
  public final void setPrzychodzacy(final Boolean przychodzacy) {
    this.przychodzacy = przychodzacy;
  }

  /**
   * Gets the polecony.
   * 
   * @return the polecony
   */
  public final Boolean getPolecony() {
    return polecony;
  }

  /**
   * Sets the polecony.
   * 
   * @param polecony the new polecony
   */
  public final void setPolecony(final Boolean polecony) {
    this.polecony = polecony;
  }

  /**
   * Gets the wewn.
   * 
   * @return the wewn
   */
  public final Boolean getWewn() {
    return wewn;
  }

  /**
   * Sets the wewn.
   * 
   * @param wewn the new wewn
   */
  public final void setWewn(final Boolean wewn) {
    this.wewn = wewn;
  }

  /**
   * Gets the archive.
   * 
   * @return the archive
   */
  public final Boolean getArchive() {
    return archive;
  }

  /**
   * Sets the archive.
   * 
   * @param archive the new archive
   */
  public final void setArchive(final Boolean archive) {
    this.archive = archive;
  }

  /**
   * Gets the adres.
   * 
   * @return the adres
   */
  public final Adres getAdres() {
    return adres;
  }

  /**
   * Sets the adres.
   * 
   * @param adres the new adres
   */
  public final void setAdres(final Adres adres) {
    this.adres = adres;
  }

  /**
   * Gets the plik sciezka.
   * 
   * @return the plik sciezka
   */
  public final String getPlikSciezka() {
    return plikSciezka;
  }

  /**
   * Sets the plik sciezka.
   * 
   * @param plikSciezka the new plik sciezka
   */
  public final void setPlikSciezka(final String plikSciezka) {
    this.plikSciezka = plikSciezka;
  }

  /**
   * Gets the user.
   * 
   * @return the user
   */
  public final User getUser() {
    return user;
  }

  /**
   * Sets the user.
   * 
   * @param user the new user
   */
  public final void setUser(final User user) {
    this.user = user;
  }

  /** The id. */
  private Long id;

  /** The data. */
  private Date data;

  /** The sygnatura. */
  private String sygnatura;

  /** The typ pisma. */
  private TypPisma typPisma;

  /** The odbiorca. */
  private String odbiorca;

  /** The opis. */
  private String opis;

  /** The przychodzacy. */
  private Boolean przychodzacy;

  /** The polecony. */
  private Boolean polecony;

  /** The wewn. */
  private Boolean wewn;

  /** The archive. */
  private Boolean archive;

  /** The adres. */
  private Adres adres;

  /** The plik sciezka. */
  private String plikSciezka;

  /** The user. */
  private User user;

  /**
   * Instantiates a new transaction builder.
   * 
   * @param id the id
   */
  public TransactionBuilder(final Long id) {
    this.id = id;
  }

  /**
   * Instantiates a new transaction builder.
   */
  public TransactionBuilder() {
    //
  }

  /**
   * Instantiates a new transaction builder.
   * 
   * @param transaction the transaction
   */
  public TransactionBuilder(final Transaction transaction) {
    this.id = transaction.getId();
    this.sygnatura = transaction.getSygnatura();
    this.typPisma = transaction.getTypPisma();
    this.odbiorca = transaction.getOdbiorca();
    this.data = transaction.getData();
    this.opis = transaction.getOpis();
    this.przychodzacy = transaction.getPrzychodzacy();
    this.polecony = transaction.getPolecony();
    this.wewn = transaction.getWewnetrzne();
    this.archive = transaction.getArchive();
    this.adres = transaction.getAdres();
    this.plikSciezka = transaction.getSciezkaDoPliku();
    this.user = transaction.getUser();
  }

  /**
   * Data.
   * 
   * @param data the data
   * @return the transaction builder
   */
  public final TransactionBuilder data(final Date data) {
    this.data = data;
    return this;
  }

  /**
   * Typ.
   * 
   * @param typWiadomosci the typ wiadomosci
   * @return the transaction builder
   */
  public final TransactionBuilder typ(final TypWiadomosci typWiadomosci) {
    if (typWiadomosci != null) {
      this.przychodzacy = typWiadomosci.isPrzychodzacy();
      this.polecony = typWiadomosci.isPolecony();
      this.wewn = typWiadomosci.isWewnetrzny();
      this.archive = typWiadomosci.isArchiwalny();
    }
    return this;
  }

  /**
   * Boole.
   * 
   * @param przychodzacy the przychodzacy
   * @param polecony the polecony
   * @param wewn the wewn
   * @param archive the archive
   * @return the transaction builder
   */
  public final TransactionBuilder boole(final Boolean przychodzacy, final Boolean polecony,
      final Boolean wewn, final Boolean archive) {
    this.przychodzacy = przychodzacy;
    this.polecony = polecony;
    this.wewn = wewn;
    this.archive = archive;
    return this;
  }

  /**
   * Plik sciezka.
   * 
   * @param sciezka the sciezka
   * @return the transaction builder
   */
  public final TransactionBuilder plikSciezka(final String sciezka) {
    this.plikSciezka = sciezka;
    return this;
  }

  /**
   * Stringi.
   * 
   * @param sygnatura the sygnatura
   * @param typPisma the typ pisma
   * @param odbiorca the odbiorca
   * @param opis the opis
   * @return the transaction builder
   */
  public final TransactionBuilder stringi(final String sygnatura, final TypPisma typPisma,
      final String odbiorca, final String opis) {
    this.sygnatura = sygnatura;
    this.typPisma = typPisma;
    this.odbiorca = odbiorca;
    this.opis = opis;
    return this;
  }

  /**
   * Default transaction.
   * 
   * @param u the u
   * @return the transaction builder
   */
  public final TransactionBuilder defaultTransaction(User u) {
    return this.user(u).stringi(null, new TypPisma(Strings.DEFAULT_TYPPISMA), Strings.EMPTY_STRING,
        Strings.EMPTY_STRING);
  }

  /**
   * Typ pisma.
   * 
   * @param typPisma the typ pisma
   * @return the transaction builder
   */
  public final TransactionBuilder typPisma(final TypPisma typPisma) {
    this.typPisma = typPisma;
    return this;
  }

  /**
   * Id.
   * 
   * @param id the id
   * @return the transaction builder
   */
  public final TransactionBuilder id(final Long id) {
    this.id = id;
    return this;
  }

  /**
   * Adres.
   * 
   * @param adres the adres
   * @return the transaction builder
   */
  public final TransactionBuilder adres(final Adres adres) {
    this.adres = adres;
    return this;
  }

  /**
   * User.
   * 
   * @param user the user
   * @return the transaction builder
   */
  public final TransactionBuilder user(final User user) {
    this.user = user;
    return this;
  }

  /**
   * Builds the.
   * 
   * @return the transaction
   */
  public final Transaction build() {
    return new Transaction(this);
  }

}
