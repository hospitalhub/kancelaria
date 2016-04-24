package pl.kalisz.szpital.hr.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Index;

@SuppressWarnings("serial")
@Entity
@Table(name = "hr_kontraktowiec")
public class Kontraktowiec implements Serializable {

	public final static String ARCHIWALNY = "archiwalny";
	public static String[] captions = { "Nazwisko", "Imię", "NIP", "REGON",
			"PESEL", "Nr PWZ", "Specjalizacje", "Nr tel.", "Data początku um.",
			"Data końca um.", "Data ważn. zaśw. lek.", "Data ważn. OC", "Płeć",
			"Miejsce Pracy", "Archiwalny", "Adres 1", "Adres 2", "Adres 3" };

	public static Integer[][] columnSets = { { 0, 1, 2, 3, 4, 5 },
			{ 0, 1, 8, 9, 10, 11 } };
	public final static String DATA_KONCA_UMOWY = "dataKoncaUmowy";
	public final static String DATA_POCZATKU_UMOWY = "dataPoczatkuUmowy";
	public final static String DATA_WAZNOSCI_POLISY_OC = "dataWaznosciPolisyOC";
	public final static String DATA_WAZNOSCI_ZASWIADCZENIA_LEKARSKIEGO = "dataWaznosciZaswiadczeniaLekarskiego";
	public final static String ID = "id";
	public final static String IMIE = "imie";
	public final static String MIEJSCEPRACY = "miejscePracy";
	public final static String NAZWISKO = "nazwisko";
	public final static String NIP = "nip";
	public final static String NUMER_PRAWA_WYKONYWANIA_ZAWODU = "numerPrawaWykonywaniaZawodu";
	public final static String NUMER_TELEFONU = "numerTelefonu";
	public final static String PESEL = "pesel";
	public final static String PLEC = "plec";
	public final static String REGON = "regon";
	public final static String SPECJALIZACJE = "specjalizacje";
	public final static String ADRES1 = "adres1";
	public final static String ADRES2 = "adres2";
	public final static String ADRES3 = "adres3";

	public static String[] Columns = { NAZWISKO, IMIE, NIP, REGON, PESEL,
			NUMER_PRAWA_WYKONYWANIA_ZAWODU, SPECJALIZACJE, NUMER_TELEFONU,
			DATA_POCZATKU_UMOWY, DATA_KONCA_UMOWY,
			DATA_WAZNOSCI_ZASWIADCZENIA_LEKARSKIEGO, DATA_WAZNOSCI_POLISY_OC,
			PLEC, MIEJSCEPRACY, ARCHIWALNY, ADRES1, ADRES2, ADRES3 };

	@Index
	@Column(name = ARCHIWALNY, nullable = false)
	private Boolean archiwalny = false;

	@Index
	@Column(name = DATA_KONCA_UMOWY, nullable = true)
	@Temporal(value = TemporalType.DATE)
	private Date dataKoncaUmowy;

	@Index
	@Column(name = DATA_POCZATKU_UMOWY, nullable = true)
	@Temporal(value = TemporalType.DATE)
	private Date dataPoczatkuUmowy;

	@Index
	@Column(name = DATA_WAZNOSCI_POLISY_OC, nullable = true)
	@Temporal(value = TemporalType.DATE)
	private Date dataWaznosciPolisyOC;

	@Index
	@Column(name = DATA_WAZNOSCI_ZASWIADCZENIA_LEKARSKIEGO, nullable = true)
	@Temporal(value = TemporalType.DATE)
	private Date dataWaznosciZaswiadczeniaLekarskiego;

	/** The id. */
	@Id
	@GeneratedValue(generator = "KontraktowiecSeq")
	@TableGenerator(name = "KontraktowiecSeq", pkColumnValue = "KONTRAKTOWIEC_SEQ", initialValue = 0, allocationSize = 1)
	@Column(name = ID)
	private Integer id;

	@Index
	@Column(name = IMIE, nullable = false)
	private String imie = "";

	@Index
	@Column(name = MIEJSCEPRACY, nullable = false)
	private String miejscePracy = "";

	@Index
	@Column(name = NAZWISKO, nullable = false)
	private String nazwisko = "";

	@Index
	@Column(name = NIP, nullable = false)
	private String nip = "";

	@Index
	@Column(name = NUMER_PRAWA_WYKONYWANIA_ZAWODU, nullable = false)
	private String numerPrawaWykonywaniaZawodu = "";

	@Column(name = NUMER_TELEFONU, nullable = false)
	private String numerTelefonu = "";

	@Index
	@Column(name = PESEL, nullable = false)
	private String pesel = "";

	@Column(name = PLEC, nullable = true, columnDefinition = "TINYINT(1)")
	private Boolean plec = null;

	@Index
	@Column(name = REGON, nullable = false)
	private String regon = "";

	@Column(name = ADRES1, nullable = false)
	private String adres1 = "";

	@Column(name = ADRES2, nullable = false)
	private String adres2 = "";

	@Column(name = ADRES3, nullable = false)
	private String adres3 = "";

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Specjalizacja> specjalizacje = new HashSet<Specjalizacja>();

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name=Absence.KONTRAKTOWIEC_ID)
	private Set<Absence> nieobecnosci = new HashSet<Absence>();

	public Boolean getArchiwalny() {
		return archiwalny;
	}

	public Set<Absence> getNieobecnosci() {
		return nieobecnosci;
	}

	public Date getDataWaznosciPolisyOC() {
		return dataWaznosciPolisyOC;
	}

	public Date getDataWaznosciZaswiadczeniaLekarskiego() {
		return dataWaznosciZaswiadczeniaLekarskiego;
	}

	public String getImie() {
		return imie;
	}

	public String getMiejscePracy() {
		return miejscePracy;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public String getNip() {
		return nip;
	}

	public String getNumerPrawaWykonywaniaZawodu() {
		return numerPrawaWykonywaniaZawodu;
	}

	public String getNumerTelefonu() {
		return numerTelefonu;
	}

	public String getPesel() {
		return pesel;
	}

	public Boolean getPlec() {
		return plec;
	}

	public String getRegon() {
		return regon;
	}

	public Set<Specjalizacja> getSpecjalizacje() {
		return specjalizacje;
	}

	public void setArchiwalny(Boolean archiwalny) {
		this.archiwalny = archiwalny;
	}

	public void setDataWaznosciPolisyOC(Date dataWaznosciPolisyOC) {
		this.dataWaznosciPolisyOC = dataWaznosciPolisyOC;
	}

	public void setDataWaznosciZaswiadczeniaLekarskiego(
			Date dataWaznosciZaswiadczeniaLekarskiego) {
		this.dataWaznosciZaswiadczeniaLekarskiego = dataWaznosciZaswiadczeniaLekarskiego;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public void setMiejscePracy(String miejscePracy) {
		this.miejscePracy = miejscePracy;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public void setNumerPrawaWykonywaniaZawodu(
			String numerPrawaWykonywaniaZawodu) {
		this.numerPrawaWykonywaniaZawodu = numerPrawaWykonywaniaZawodu;
	}

	public void setNumerTelefonu(String numerTelefonu) {
		this.numerTelefonu = numerTelefonu;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public void setPlec(Boolean plec) {
		this.plec = plec;
	}

	public void setRegon(String regon) {
		this.regon = regon;
	}

	public void setSpecjalizacje(Set<Specjalizacja> specjalizacje) {
		this.specjalizacje = specjalizacje;
	}

	public Integer getId() {
		return id;
	}

	public String getNazwiskoImie() {
		StringBuffer sb = new StringBuffer();
		sb.append(nazwisko);
		sb.append(" ");
		sb.append(imie);
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(nazwisko);
		sb.append(imie);
		sb.append(pesel);
		sb.append(nip);
		sb.append(regon);
		sb.append(numerPrawaWykonywaniaZawodu);
		sb.append(specjalizacje);
		sb.append(numerTelefonu);
		sb.append(dataWaznosciZaswiadczeniaLekarskiego);
		sb.append(dataWaznosciPolisyOC);
		sb.append(plec);
		sb.append(miejscePracy);
		return sb.toString();
	}
}
