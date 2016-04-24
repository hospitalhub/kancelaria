package pl.kalisz.szpital.db.kancelaria;

import java.util.Collection;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.annotations.Index;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

import com.vaadin.data.Item;

import pl.kalisz.szpital.db.HospitalEntity;
import pl.kalisz.szpital.db.kancelaria.enums.Kraj;

/**
 * The Class Adres.
 */
@SuppressWarnings("serial")
@Entity
public class Adres extends HospitalEntity implements Comparable<Adres>, Comparator<Adres> {
	private static final String CODE_REGEX = "^$|([0-9]{2}-[0-9]{3})";

	private static final String MAIL_REGEX = "^$|(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)";

	/** The Constant FIRMA. */
	public static final String COMPANY = "firma";

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

	/** The Constant ULICA. */
	public static final String ULICA = "ulica";

	/** The Constant ILOSC_PISM. */
	public static final String ILOSC_PISM = "iloscPism";

	public static final String KRAJ = "kraj";

	public static final String PHONES = "phones";

	/** The Constant COLUMN_NAMES. */
	public static final String[] COLUMN_NAMES = new String[] { COMPANY, NAZWISKO, IMIE, ULICA, KOD, MIASTO, PHONES,
			MAIL, KRAJ, NOTATKI };

	/** The firma. */
	@Index
	@Size(min = 0, max = 80, message = "Maksymalna liczba znaków to 80")
	private String firma;

	/** The id. */
	@Id
	@GeneratedValue(generator = "AdrSeq")
	@TableGenerator(name = "AdrSeq", pkColumnValue = "ADRES_SEQ", initialValue = 0, allocationSize = 1)
	@Column(name = HospitalEntity.ID)
	private Long id;

	/** The imie. */
	@Index
	@Size(min = 0, max = 30, message = "Popraw imię, maks. liczba znaków to 30")
	private String imie;

	/** The kod. */
	@Index
	@Pattern(regexp = CODE_REGEX, message = "Kod pocztowy w postaci: 00-000")
	private String kod;

	/** The mail. */
	@Pattern(regexp = MAIL_REGEX, message = "Nieprawidłowy format maila")
	private String mail;

	/** The miasto. */
	@Index
	@Size(min = 0, max = 50, message = "Maksymalna liczba znaków to 50")
	private String miasto;

	/** The nazwisko. */
	@Index
	@Size(min = 0, max = 80, message = "Maksymalna liczba znaków to 80")
	private String nazwisko;

	/** The notatki. */
	@Size(min = 0, max = 250, message = "Maksymalna liczba znaków to 250")
	private String notatki;

	/** The ulica. */
	@Index
	@Size(min = 0, max = 80, message = "Maksymalna liczba znaków to 80")
	private String ulica;

	@OneToMany(orphanRemoval = true, mappedBy = "adres")
	private Collection<Phone> phones;

	/** The count. */
	@Index
	private Integer iloscPism;

	@Index
	@Enumerated(EnumType.STRING)
	private Kraj kraj = Kraj.PL;

	/**
	 * Instantiates a new adres.
	 */
	public Adres() {
		//
	}

	/**
	 * Instantiates a new adres.
	 *
	 * @param adresBuilder
	 *            the adres builder
	 */
	public Adres(final AdresBuilder adresBuilder) {
		this.id = adresBuilder.getIdNew();
		this.imie = adresBuilder.getImie();
		this.nazwisko = adresBuilder.getNazwisko();
		this.firma = adresBuilder.getFirma();
		this.ulica = adresBuilder.getUlica();
		this.miasto = adresBuilder.getMiasto();
		this.kod = adresBuilder.getKod();
		this.mail = adresBuilder.getMail();
		this.notatki = adresBuilder.getNotatki();
	}

	/**
	 * Instantiates a new adres.
	 *
	 * @param item
	 *            the item
	 */
	public Adres(final Item item) {
		super();
		this.id = (Long) getValue(item, HospitalEntity.ID);
		this.imie = (String) getValue(item, IMIE);
		this.nazwisko = (String) getValue(item, NAZWISKO);
		this.firma = (String) getValue(item, COMPANY);
		this.ulica = (String) getValue(item, ULICA);
		this.miasto = (String) getValue(item, MIASTO);
		this.kod = (String) getValue(item, KOD);
		this.mail = (String) getValue(item, MAIL);
		this.notatki = (String) getValue(item, NOTATKI);
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
	 * @param item
	 *            the item
	 * @param name
	 *            the name
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
	 * Sets the firma.
	 *
	 * @param firma
	 *            the new firma
	 */
	public final void setFirma(final String firma) {
		this.firma = firma;
	}

	/**
	 * Sets the imie.
	 *
	 * @param imie
	 *            the new imie
	 */
	public final void setImie(final String imie) {
		this.imie = imie;
	}

	/**
	 * Sets the kod.
	 *
	 * @param kod
	 *            the new kod
	 */
	public final void setKod(final String kod) {
		this.kod = kod;
	}

	/**
	 * Sets the mail.
	 *
	 * @param mail
	 *            the new mail
	 */
	public final void setMail(final String mail) {
		this.mail = mail;
	}

	/**
	 * Sets the miasto.
	 *
	 * @param miasto
	 *            the new miasto
	 */
	public final void setMiasto(final String miasto) {
		this.miasto = miasto;
	}

	/**
	 * Sets the nazwisko.
	 *
	 * @param nazwisko
	 *            the new nazwisko
	 */
	public final void setNazwisko(final String nazwisko) {
		this.nazwisko = nazwisko;
	}

	/**
	 * Sets the notatki.
	 *
	 * @param notatki
	 *            the new notatki
	 */
	public final void setNotatki(final String notatki) {
		this.notatki = notatki;
	}

	/**
	 * Sets the ulica.
	 *
	 * @param ulica
	 *            the new ulica
	 */
	public final void setUlica(final String ulica) {
		this.ulica = ulica;
	}

	/**
	 * Sets the ilosc pism.
	 *
	 * @param iloscPism
	 *            the new ilosc pism
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

	public Kraj getKraj() {
		return kraj;
	}

	public void setKraj(Kraj kraj) {
		this.kraj = kraj;
	}

	public void addPhone(Phone phone) {
		phones.add(phone);
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
		return buffer.toString().trim();
	}

	public final String toLocation() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(ulica.replaceAll("[^\\p{L}0-9]+", " ").replaceAll("  ", " ").replaceAll(" ", "+"));
		buffer.append(",");
		buffer.append(miasto.replaceAll("[^\\p{L}]+", ""));
		return buffer.toString().trim();
	}

	public int compare(Adres o1, Adres o2) {
		return o1.compareTo(o2);
	}

	public int compareTo(Adres o) {
		return tolowerNoWhitespace().compareTo(o.tolowerNoWhitespace());
	}
}
