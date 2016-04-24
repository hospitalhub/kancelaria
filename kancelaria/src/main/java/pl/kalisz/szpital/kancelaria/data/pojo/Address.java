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
import com.vaadin.server.FontAwesome;


/**
 * The Class Adres.
 */
@SuppressWarnings("serial")
@Entity(name=Transaction.ADDRESS)
public class Address implements Comparable<Address>, Comparator<Address>, Serializable {

  /** The Constant FAX. */
  public static final String FAX = "fax";

  /** The Constant FIRMA. */
  public static final String COMPANY = "firma";

  /** The Constant ID. */
  public static final String ID = "id";

  /** The Constant IMIE. */
  public static final String NAME = "imie";

  /** The Constant KOD. */
  public static final String POSTCODE = "kod";

  /** The Constant MAIL. */
  public static final String MAIL = "mail";

  /** The Constant MIASTO. */
  public static final String CITY = "miasto";

  /** The Constant NAZWISKO. */
  public static final String LASTNAME = "nazwisko";

  /** The Constant NOTATKI. */
  public static final String NOTES = "notatki";

  /** The Constant TEL1. */
  public static final String TEL1 = "tel1";

  /** The Constant TEL2. */
  public static final String TEL2 = "tel2";

  /** The Constant ULICA. */
  public static final String STREET = "ulica";

  /** The Constant ILOSC_PISM. */
  public static final String TRANSACTION_COUNT = "iloscPism";

  public static final String COUNTRY = "kraj";

  /** The Constant COLUMN_NAMES. */
  public static final String[] COLUMN_NAMES =
      new String[] { COMPANY, LASTNAME, NAME, STREET, POSTCODE, CITY, TEL1, TEL2, FAX, MAIL, NOTES };

  /** The fax. */
  private String fax;

  /** The firma. */
  @Index
  @Column(name=COMPANY)
  private String company;

  /** The id. */
  @Id
  @GeneratedValue(generator = "AdrSeq")
  @TableGenerator(name = "AdrSeq", pkColumnValue = "ADRES_SEQ", initialValue = 0,
      allocationSize = 1)
  @Column(name = ID)
  private Integer id;

  /** The imie. */
  @Index
  @Column(name=NAME)
  private String name;

  /** The kod. */
  @Index
  @Column(name=POSTCODE)
  private String postCode;

  /** The mail. */
  @Column(name=MAIL)
  private String mail;

  /** The miasto. */
  @Index
  @Column(name=CITY)
  private String city;

  /** The nazwisko. */
  @Index
  @Column(name=LASTNAME)
  private String lastName;

  /** The notatki. */
  @Column(name=NOTES)
  private String notes;

  /** The tel1. */
  private String tel1;

  /** The tel2. */
  private String tel2;

  /** The ulica. */
  @Index
  @Column(name=STREET)
  private String street;

  /** The count. */
  @Index
  @Column(name=TRANSACTION_COUNT)
  private Integer transactionCount;

  @Index
  @Column(name=COUNTRY)
  private String country;

  /**
   * Instantiates a new adres.
   */
  public Address() {
    //
  }

  /**
   * Instantiates a new adres.
   *
   * @param adresBuilder the adres builder
   */
  public Address(final AdresBuilder adresBuilder) {
    this.id = adresBuilder.getIdNew();
    this.name = adresBuilder.getImie();
    this.lastName = adresBuilder.getNazwisko();
    this.company = adresBuilder.getFirma();
    this.street = adresBuilder.getUlica();
    this.city = adresBuilder.getMiasto();
    this.postCode = adresBuilder.getKod();
    this.tel1 = adresBuilder.getTel1();
    this.tel2 = adresBuilder.getTel2();
    this.fax = adresBuilder.getFax();
    this.mail = adresBuilder.getMail();
    this.notes = adresBuilder.getNotatki();
  }

  /**
   * Instantiates a new adres.
   *
   * @param item the item
   */
  public Address(final Item item) {
    super();
    this.id = (Integer) getValue(item, ID);
    this.name = (String) getValue(item, NAME);
    this.lastName = (String) getValue(item, LASTNAME);
    this.company = (String) getValue(item, COMPANY);
    this.street = (String) getValue(item, STREET);
    this.city = (String) getValue(item, CITY);
    this.postCode = (String) getValue(item, POSTCODE);
    this.tel1 = (String) getValue(item, TEL1);
    this.tel2 = (String) getValue(item, TEL2);
    this.fax = (String) getValue(item, FAX);
    this.mail = (String) getValue(item, MAIL);
    this.notes = (String) getValue(item, NOTES);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public final int compare(final Address arg0, final Address arg1) {
    return arg0.compareTo(arg1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public final int compareTo(final Address arg0) {
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
    return company;
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
    return name;
  }

  /**
   * Gets the kod.
   *
   * @return the kod
   */
  public final String getKod() {
    return postCode;
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
    return city;
  }

  /**
   * Gets the nazwa.
   *
   * @return the nazwa
   */
  public final String getNazwa() {
    return StringUtils.trim(company + " " + name + " " + lastName);
  }

  /**
   * Gets the nazwisko.
   *
   * @return the nazwisko
   */
  public final String getNazwisko() {
    return lastName;
  }

  /**
   * Gets the notatki.
   *
   * @return the notatki
   */
  public final String getNotatki() {
    return notes;
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
    return street;
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
    this.company = firma;
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
    this.name = imie;
  }

  /**
   * Sets the kod.
   *
   * @param kod the new kod
   */
  public final void setKod(final String kod) {
    this.postCode = kod;
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
    this.city = miasto;
  }

  /**
   * Sets the nazwisko.
   *
   * @param nazwisko the new nazwisko
   */
  public final void setNazwisko(final String nazwisko) {
    this.lastName = nazwisko;
  }

  /**
   * Sets the notatki.
   *
   * @param notatki the new notatki
   */
  public final void setNotatki(final String notatki) {
    this.notes = notatki;
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
    this.street = ulica;
  }

  /**
   * Sets the ilosc pism.
   *
   * @param iloscPism the new ilosc pism
   */
  public void setIloscPism(Integer iloscPism) {
    this.transactionCount = iloscPism;
  }

  /**
   * Gets the ilosc pism.
   *
   * @return the ilosc pism
   */
  public Integer getIloscPism() {
    return transactionCount;
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
    buffer.append(name);
    buffer.append(" ");
    buffer.append(lastName);
    buffer.append(" ");
    buffer.append(company);
    buffer.append("\n");
    buffer.append(street);
    buffer.append("\n");
    buffer.append(postCode);
    buffer.append(" ");
    buffer.append(city);
    return buffer.toString().trim().replaceAll("null", "?");
  }

  public final String toHTML() {
    return String.format(
        String.format("%s %s", FontAwesome.HOME.getHtml(), toString().replace("\n", "<BR/>")));
  }
}
