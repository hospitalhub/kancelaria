package pl.kalisz.szpital.kancelaria.data.pojo;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.annotations.Index;

import com.vaadin.data.Item;


/**
 * The Class Adres.
 */
@SuppressWarnings("serial")
@Entity
public class Adres implements Comparable<Adres>, Comparator<Adres>, Serializable {

  /** The Constant FAX. */
  public static final String FAX = "fax";

  /** The Constant FIRMA. */
  public static final String FIRMA = "firma";

  /** The Constant ID. */
  public static final String ID = "id";

  /** The Constant IMIE. */
  public static final String IMIE = "imie";

  /** The Constant KOD. */
  public static final String KOD = "kod";

  /** The Constant MAIL. */
  public static final String MAIL = "mail";

  /** The Constant MIASTO. */
  public static final String MIASTO = "miasto";

  /** The Constant NAZWISKO. */
  public static final String NAZWISKO = "nazwisko";

  /** The Constant NOTATKI. */
  public static final String NOTATKI = "notatki";

  /** The Constant TEL1. */
  public static final String TEL1 = "tel1";

  /** The Constant TEL2. */
  public static final String TEL2 = "tel2";

  /** The Constant ULICA. */
  public static final String ULICA = "ulica";
  
  /** The Constant ILOSC_PISM. */
  public static final String ILOSC_PISM = "iloscPism";

  /** The Constant COLUMN_NAMES. */
  public static final String[] COLUMN_NAMES = new String[] {FIRMA, NAZWISKO, IMIE, ULICA, KOD,
      MIASTO, TEL1, TEL2, FAX, MAIL, NOTATKI};

  /** The fax. */
  private String fax;

  /** The firma. */
  @Index
  private String firma;

  /** The id. */
  @Id
  @GeneratedValue(generator = "AdrSeq")
  @TableGenerator(name = "AdrSeq", pkColumnValue = "ADRES_SEQ", initialValue = 0,
      allocationSize = 1)
  @Column(name = ID)
  private Integer id;

  /** The imie. */
  @Index
  private String imie;

  /** The kod. */
  @Index
  private String kod;

  /** The mail. */
  private String mail;

  /** The miasto. */
  @Index
  private String miasto;

  /** The nazwisko. */
  @Index
  private String nazwisko;

  /** The notatki. */
  private String notatki;

  /** The tel1. */
  private String tel1;

  /** The tel2. */
  private String tel2;

  /** The ulica. */
  @Index
  private String ulica;
  
  /** The count. */
  @Index
  private Integer iloscPism;

  @Index
  private String kraj;

  /**
   * Instantiates a new adres.
   */
  public Adres() {
    //
  }

  /**
   * Instantiates a new adres.
   *
   * @param adresBuilder the adres builder
   */
  public Adres(final AdresBuilder adresBuilder) {
    this.id = adresBuilder.getIdNew();
    this.imie = adresBuilder.getImie();
    this.nazwisko = adresBuilder.getNazwisko();
    this.firma = adresBuilder.getFirma();
    this.ulica = adresBuilder.getUlica();
    this.miasto = adresBuilder.getMiasto();
    this.kod = adresBuilder.getKod();
    this.tel1 = adresBuilder.getTel1();
    this.tel2 = adresBuilder.getTel2();
    this.fax = adresBuilder.getFax();
    this.mail = adresBuilder.getMail();
    this.notatki = adresBuilder.getNotatki();
  }

  /**
   * Instantiates a new adres.
   *
   * @param item the item
   */
  public Adres(final Item item) {
    super();
    this.id = (Integer) getValue(item, ID);
    this.imie = (String) getValue(item, IMIE);
    this.nazwisko = (String) getValue(item, NAZWISKO);
    this.firma = (String) getValue(item, FIRMA);
    this.ulica = (String) getValue(item, ULICA);
    this.miasto = (String) getValue(item, MIASTO);
    this.kod = (String) getValue(item, KOD);
    this.tel1 = (String) getValue(item, TEL1);
    this.tel2 = (String) getValue(item, TEL2);
    this.fax = (String) getValue(item, FAX);
    this.mail = (String) getValue(item, MAIL);
    this.notatki = (String) getValue(item, NOTATKI);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public final int compare(final Adres arg0, final Adres arg1) {
    return arg0.compareTo(arg1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public final int compareTo(final Adres arg0) {
    return tolowerNoWhitespace().compareTo(arg0.tolowerNoWhitespace());
  }

  /**
   * Gets the fax.
   *
   * @return the fax
   */
  public final String getFax() {
    return fax;
  }

  /**
   * Gets the firma.
   *
   * @return the firma
   */
  public final String getFirma() {
    return firma;
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
   * Gets the imie.
   *
   * @return the imie
   */
  public final String getImie() {
    return imie;
  }

  /**
   * Gets the kod.
   *
   * @return the kod
   */
  public final String getKod() {
    return kod;
  }

  /**
   * Gets the mail.
   *
   * @return the mail
   */
  public final String getMail() {
    return mail;
  }

  /**
   * Gets the miasto.
   *
   * @return the miasto
   */
  public final String getMiasto() {
    return miasto;
  }

  /**
   * Gets the nazwa.
   *
   * @return the nazwa
   */
  public final String getNazwa() {
    return StringUtils.trim(firma + " " + imie + " " + nazwisko);
  }

  /**
   * Gets the nazwisko.
   *
   * @return the nazwisko
   */
  public final String getNazwisko() {
    return nazwisko;
  }

  /**
   * Gets the notatki.
   *
   * @return the notatki
   */
  public final String getNotatki() {
    return notatki;
  }

  /**
   * Gets the tel1.
   *
   * @return the tel1
   */
  public final String getTel1() {
    return tel1;
  }

  /**
   * Gets the tel2.
   *
   * @return the tel2
   */
  public final String getTel2() {
    return tel2;
  }

  /**
   * Gets the ulica.
   *
   * @return the ulica
   */
  public final String getUlica() {
    return ulica;
  }

  /**
   * Gets the value.
   *
   * @param item the item
   * @param name the name
   * @return the value
   */
  private Object getValue(final Item item, final String name) {
    if (item != null && item.getItemProperty(name) != null) {
      return item.getItemProperty(name).getValue();
    } else {
      return null;
    }
  }

  /**
   * Sets the fax.
   *
   * @param fax the new fax
   */
  public final void setFax(final String fax) {
    this.fax = fax;
  }

  /**
   * Sets the firma.
   *
   * @param firma the new firma
   */
  public final void setFirma(final String firma) {
    this.firma = firma;
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
   * Sets the imie.
   *
   * @param imie the new imie
   */
  public final void setImie(final String imie) {
    this.imie = imie;
  }

  /**
   * Sets the kod.
   *
   * @param kod the new kod
   */
  public final void setKod(final String kod) {
    this.kod = kod;
  }

  /**
   * Sets the mail.
   *
   * @param mail the new mail
   */
  public final void setMail(final String mail) {
    this.mail = mail;
  }

  /**
   * Sets the miasto.
   *
   * @param miasto the new miasto
   */
  public final void setMiasto(final String miasto) {
    this.miasto = miasto;
  }

  /**
   * Sets the nazwisko.
   *
   * @param nazwisko the new nazwisko
   */
  public final void setNazwisko(final String nazwisko) {
    this.nazwisko = nazwisko;
  }

  /**
   * Sets the notatki.
   *
   * @param notatki the new notatki
   */
  public final void setNotatki(final String notatki) {
    this.notatki = notatki;
  }

  /**
   * Sets the tel1.
   *
   * @param tel1 the new tel1
   */
  public final void setTel1(final String tel1) {
    this.tel1 = tel1;
  }

  /**
   * Sets the tel2.
   *
   * @param tel2 the new tel2
   */
  public final void setTel2(final String tel2) {
    this.tel2 = tel2;
  }

  /**
   * Sets the ulica.
   *
   * @param ulica the new ulica
   */
  public final void setUlica(final String ulica) {
    this.ulica = ulica;
  }
  
  /**
   * Sets the ilosc pism.
   *
   * @param iloscPism the new ilosc pism
   */
  public void setIloscPism(Integer iloscPism) {
    this.iloscPism = iloscPism;
  }
  
  /**
   * Gets the ilosc pism.
   *
   * @return the ilosc pism
   */
  public Integer getIloscPism() {
    return iloscPism;
  }

  /**
   * Tolower no whitespace.
   *
   * @return the string
   */
  private String tolowerNoWhitespace() {
    return toString().toLowerCase().replaceAll(" ", "");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public final String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(imie);
    buffer.append(" ");
    buffer.append(nazwisko);
    buffer.append(" ");
    buffer.append(firma);
    buffer.append("\n");
    buffer.append(ulica);
    buffer.append("\n");
    buffer.append(kod);
    buffer.append(" ");
    buffer.append(miasto);
    return buffer.toString();
  }
}
