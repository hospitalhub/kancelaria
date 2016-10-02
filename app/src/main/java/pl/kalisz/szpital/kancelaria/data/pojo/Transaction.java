package pl.kalisz.szpital.kancelaria.data.pojo;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import com.vaadin.data.Item;

import pl.kalisz.szpital.kancelaria.data.enums.TypWiadomosci;


/**
 * The Class Transaction.
 */
@SuppressWarnings("serial")
@Entity
public class Transaction implements Serializable {

  /** The Constant ADRES. */
  public static final String ADRES = "adres";

  /** The Constant ADRESID. */
  public static final String ADRESID = "adresid";

  /** The Constant ARCHIVE_STR. */
  public static final String ARCHIVE_STR = "archive";

  /** The Constant DATA_STR. */
  public static final String DATA_STR = "data";

  /** The Constant ID. */
  public static final String ID = "id";

  /** The Constant ODBIORCA_STR. */
  public static final String ODBIORCA_STR = "odbiorca";

  /** The Constant OPIS_STR. */
  public static final String OPIS_STR = "opis";

  /** The Constant PLIK_SCIEZKA_STR. */
  public static final String PLIK_SCIEZKA_STR = "sciezkaDoPliku";

  /** The Constant POLECONY_STR. */
  public static final String POLECONY_STR = "polecony";

  /** The Constant PRZYCHODZACY_STR. */
  public static final String PRZYCHODZACY_STR = "przychodzacy";

  /** The Constant SYGNATURA_STR. */
  public static final String SYGNATURA_STR = "sygnatura";

  /** The Constant TYP_PISMA_STR. */
  public static final String TYP_PISMA_STR = "typPisma";

  /** The Constant TYP_WIADOMOSCI. */
  public static final String TYP_WIADOMOSCI = "typWiadomosci";

  /** The Constant USER. */
  public static final String USER = "user";

  /** The Constant USERID. */
  public static final String USERID = "userid";

  /** The Constant WEWN_STR. */
  public static final String WEWN_STR = "wewnetrzne";

  /** The adres. */
  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull
  @JoinColumn(name = ADRESID, referencedColumnName = Adres.ID, foreignKey = @ForeignKey(
      name = Adres.ID))
  @JoinFetch(JoinFetchType.INNER)
  @Index
  private Adres adres;

  /** The archive. */
  @Index
  private Boolean archive;

  /** The data. */
  @Index
  @Temporal(TemporalType.TIMESTAMP)
  private Date data;

  /** The id. */
  @Id
  @GeneratedValue(generator = "InvTab")
  @TableGenerator(name = "InvTab", pkColumnValue = "TRANSACTION_SEQ", initialValue = 34953,
      allocationSize = 1)
  private Integer id;

  /** The odbiorca. */
  private String odbiorca;

  /** The opis. */
  private String opis;

  /** The polecony. */
  @Index
  private Boolean polecony;

  /** The przychodzacy. */
  @Index
  private Boolean przychodzacy;

  /** The sciezka do pliku. */
  @Column(name = "plik_sciezka")
  private String sciezkaDoPliku;

  /** The sygnatura. */
  private String sygnatura;

  /** The typ pisma. */
  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull
  @JoinColumn(name = "typ_pisma", referencedColumnName = TypPisma.NAZWA, foreignKey = @ForeignKey(
      name = TypPisma.NAZWA))
  @JoinFetch(JoinFetchType.INNER)
  @Index
  private TypPisma typPisma;

  /** The user. */
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = USERID, referencedColumnName = User.ID, foreignKey = @ForeignKey(
      name = User.ID))
  @Index
  private User user;

  /** The wewnetrzne. */
  @Column(name = "wewn")
  @Index
  private Boolean wewnetrzne;

  /**
   * Instantiates a new transaction.
   */
  public Transaction() {
    //
  }

  /**
   * Instantiates a new transaction.
   * 
   * @param item the item
   */
  public Transaction(final Item item) {
    super();
    this.id = (Integer) getValue(item, ID);
    this.sygnatura = (String) getValue(item, SYGNATURA_STR);
    this.typPisma = (TypPisma) getValue(item, TYP_PISMA_STR);
    this.odbiorca = (String) getValue(item, ODBIORCA_STR);
    this.data = (Date) getValue(item, DATA_STR);
    this.opis = (String) getValue(item, OPIS_STR);
    this.przychodzacy = (Boolean) getValue(item, PRZYCHODZACY_STR);
    this.polecony = (Boolean) getValue(item, POLECONY_STR);
    this.wewnetrzne = (Boolean) getValue(item, WEWN_STR);
    this.archive = (Boolean) getValue(item, ARCHIVE_STR);
    this.sciezkaDoPliku = (String) getValue(item, PLIK_SCIEZKA_STR);
    this.user = (User) getValue(item, USER);
  }

  /**
   * Instantiates a new transaction.
   * 
   * @param builder the builder
   */
  public Transaction(final TransactionBuilder builder) {
    this.id = builder.getId();
    this.sygnatura = builder.getSygnatura();
    this.typPisma = builder.getTypPisma();
    this.odbiorca = builder.getOdbiorca();
    this.data = builder.getData();
    this.opis = builder.getOpis();
    this.przychodzacy = builder.getPrzychodzacy();
    this.polecony = builder.getPolecony();
    this.wewnetrzne = builder.getWewn();
    this.archive = builder.getArchive();
    this.adres = builder.getAdres();
    this.sciezkaDoPliku = builder.getPlikSciezka();
    this.user = builder.getUser();
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
   * Gets the data.
   * 
   * @return the data
   */
  public final Date getData() {
    return data;
  }

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public final Integer getId() {
    return id;
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
   * Gets the opis.
   * 
   * @return the opis
   */
  public final String getOpis() {
    return opis;
  }

  /**
   * Gets the plik sciezka.
   * 
   * @return the plik sciezka
   */
  public final String getPlikSciezka() {
    return sciezkaDoPliku;
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
   * Gets the przychodzacy.
   * 
   * @return the przychodzacy
   */
  public final Boolean getPrzychodzacy() {
    return przychodzacy;
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
   * Gets the typ pisma.
   * 
   * @return the typ pisma
   */
  public final TypPisma getTypPisma() {
    return typPisma;
  }

  /**
   * Gets the typ wiadomosci.
   * 
   * @return the typ wiadomosci
   */
  public final TypWiadomosci getTypWiadomosci() {
    return TypWiadomosci.getByValues(polecony, wewnetrzne, przychodzacy);
  }

  /**
   * Gets the value.
   * 
   * @param item the item
   * @param name the name
   * @return the value
   */
  private Object getValue(final Item item, final String name) {
    return item.getItemProperty(name).getValue();
  }

  /**
   * Gets the wewn.
   * 
   * @return the wewn
   */
  public final Boolean getWewn() {
    return wewnetrzne;
  }

  /**
   * Checks if is imported.
   * 
   * @return the boolean
   */
  public final Boolean isImported() {
    if (getSygnatura() == null) {
      return false;
    }
    return getSygnatura().startsWith("W/") || getSygnatura().startsWith("P/");
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
   * Sets the data.
   * 
   * @param data the new data
   */
  public final void setData(final Date data) {
    this.data = data;
  }

  /**
   * Sets the id.
   * 
   * @param id the new id
   */
  public final void setId(final Integer id) {
    this.id = id;
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
   * Sets the opis.
   * 
   * @param opis the new opis
   */
  public final void setOpis(final String opis) {
    this.opis = opis;
  }

  /**
   * Sets the plik sciezka.
   * 
   * @param plikSciezka the new plik sciezka
   */
  public final void setPlikSciezka(final String plikSciezka) {
    this.sciezkaDoPliku = plikSciezka;
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
   * Sets the typ pisma.
   * 
   * @param typPisma the new typ pisma
   */
  public final void setTypPisma(final TypPisma typPisma) {
    this.typPisma = typPisma;
  }

  /**
   * Sets the typ wiadomosci.
   * 
   * @param typWiadomosci the new typ wiadomosci
   */
  public final void setTypWiadomosci(final TypWiadomosci typWiadomosci) {
    this.polecony = typWiadomosci.isPolecony();
    this.przychodzacy = typWiadomosci.isPrzychodzacy();
    this.wewnetrzne = typWiadomosci.isWewnetrzny();
    this.archive = typWiadomosci.isArchiwalny();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public final String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(sygnatura);
    buffer.append(archive ? "\nArchiwalny" : "");
    buffer.append(polecony ? "\nPolecony" : "");
    buffer.append(wewnetrzne ? "\nWewnętrzny" : "\nZewnętrzny");
    buffer.append(przychodzacy ? "\nPrzychodzący" : "\nWychodzący");
    buffer.append("\nData:");
    buffer.append(data);
    buffer.append("\nAdres:");
    buffer.append(getAdres());
    return buffer.toString();
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
   * Gets the sciezka do pliku.
   * 
   * @return the sciezka do pliku
   */
  public final String getSciezkaDoPliku() {
    return sciezkaDoPliku;
  }

  /**
   * Sets the sciezka do pliku.
   * 
   * @param sciezkaDoPliku the new sciezka do pliku
   */
  public final void setSciezkaDoPliku(final String sciezkaDoPliku) {
    this.sciezkaDoPliku = sciezkaDoPliku;
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

  /**
   * Gets the wewnetrzne.
   * 
   * @return the wewnetrzne
   */
  public final Boolean getWewnetrzne() {
    return wewnetrzne;
  }

  /**
   * Sets the wewnetrzne.
   * 
   * @param wewnetrzne the new wewnetrzne
   */
  public final void setWewnetrzne(final Boolean wewnetrzne) {
    this.wewnetrzne = wewnetrzne;
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
   * Sets the przychodzacy.
   * 
   * @param przychodzacy the new przychodzacy
   */
  public final void setPrzychodzacy(final Boolean przychodzacy) {
    this.przychodzacy = przychodzacy;
  }

}
