package pl.kalisz.szpital.db.kancelaria;

/**
 * The Class AdresBuilder.
 */
public class AdresBuilder {

	/** The id new. */
	private Long idNew;

	/** The imie. */
	private String imie;

	/** The nazwisko. */
	private String nazwisko;

	/** The firma. */
	private String firma;

	/** The ulica. */
	private String ulica;

	/** The miasto. */
	private String miasto;

	/** The kod. */
	private String kod;

	/** The tel1. */
	private String tel1;

	/** The tel2. */
	private String tel2;

	/** The fax. */
	private String fax;

	/**
	 * Gets the id new.
	 * 
	 * @return the id new
	 */
	public final Long getIdNew() {
		return idNew;
	}

	/**
	 * Sets the id new.
	 * 
	 * @param idNew
	 *            the new id new
	 */
	public final void setIdNew(final Long idNew) {
		this.idNew = idNew;
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
	 * Sets the imie.
	 * 
	 * @param imie
	 *            the new imie
	 */
	public final void setImie(final String imie) {
		this.imie = imie;
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
	 * Sets the nazwisko.
	 * 
	 * @param nazwisko
	 *            the new nazwisko
	 */
	public final void setNazwisko(final String nazwisko) {
		this.nazwisko = nazwisko;
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
	 * Sets the firma.
	 * 
	 * @param firma
	 *            the new firma
	 */
	public final void setFirma(final String firma) {
		this.firma = firma;
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
	 * Sets the ulica.
	 * 
	 * @param ulica
	 *            the new ulica
	 */
	public final void setUlica(final String ulica) {
		this.ulica = ulica;
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
	 * Sets the miasto.
	 * 
	 * @param miasto
	 *            the new miasto
	 */
	public final void setMiasto(final String miasto) {
		this.miasto = miasto;
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
	 * Sets the kod.
	 * 
	 * @param kod
	 *            the new kod
	 */
	public final void setKod(final String kod) {
		this.kod = kod;
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
	 * Sets the tel1.
	 * 
	 * @param tel1
	 *            the new tel1
	 */
	public final void setTel1(final String tel1) {
		this.tel1 = tel1;
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
	 * Sets the tel2.
	 * 
	 * @param tel2
	 *            the new tel2
	 */
	public final void setTel2(final String tel2) {
		this.tel2 = tel2;
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
	 * Sets the fax.
	 * 
	 * @param fax
	 *            the new fax
	 */
	public final void setFax(final String fax) {
		this.fax = fax;
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
	 * Sets the mail.
	 * 
	 * @param mail
	 *            the new mail
	 */
	public final void setMail(final String mail) {
		this.mail = mail;
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
	 * Sets the notatki.
	 * 
	 * @param notatki
	 *            the new notatki
	 */
	public final void setNotatki(final String notatki) {
		this.notatki = notatki;
	}

	/** The mail. */
	private String mail;

	/** The notatki. */
	private String notatki;

	/**
	 * Instantiates a new adres builder.
	 * 
	 * @param idNew
	 *            the id new
	 */
	public AdresBuilder(final Long idNew) {
		this.idNew = idNew;
	}

	/**
	 * Instantiates a new adres builder.
	 */
	public AdresBuilder() {
		//
	}

	/**
	 * Pusty.
	 * 
	 * @return the adres builder
	 */
	public final AdresBuilder pusty() {
		this.imie = "";
		this.nazwisko = "";
		this.firma = "";
		this.ulica = "";
		this.miasto = "";
		this.kod = "";
		this.tel1 = "";
		this.tel2 = "";
		this.fax = "";
		this.mail = "";
		this.notatki = "";
		return this;
	}

	/**
	 * Osoba.
	 * 
	 * @param imie
	 *            the imie
	 * @param nazwisko
	 *            the nazwisko
	 * @return the adres builder
	 */
	public final AdresBuilder osoba(final String imie, final String nazwisko) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		return this;
	}

	/**
	 * Firma.
	 * 
	 * @param nazwa
	 *            the nazwa
	 * @return the adres builder
	 */
	public final AdresBuilder firma(final String nazwa) {
		this.firma = nazwa;
		return this;
	}

	/**
	 * Adres.
	 * 
	 * @param ulica
	 *            the ulica
	 * @param kod
	 *            the kod
	 * @param miasto
	 *            the miasto
	 * @return the adres builder
	 */
	public final AdresBuilder adres(final String ulica, final String kod,
			final String miasto) {
		this.ulica = ulica;
		this.kod = kod;
		this.miasto = miasto;
		return this;
	}

	/**
	 * Kontaktowe.
	 * 
	 * @param tel1
	 *            the tel1
	 * @param tel2
	 *            the tel2
	 * @param fax
	 *            the fax
	 * @param mail
	 *            the mail
	 * @return the adres builder
	 */
	public final AdresBuilder kontaktowe(final String tel1, final String tel2,
			final String fax, final String mail) {
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.fax = fax;
		this.mail = mail;
		return this;
	}

	/**
	 * Notatki.
	 * 
	 * @param notatki
	 *            the notatki
	 * @return the adres builder
	 */
	public final AdresBuilder notatki(final String notatki) {
		this.notatki = notatki;
		return this;
	}

	/**
	 * Builds the.
	 * 
	 * @return the adres
	 */
	public final Adres build() {
		return new Adres(this);
	}

}
