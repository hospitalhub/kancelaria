package pl.kalisz.szpital.db.aparatura;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import pl.kalisz.szpital.db.HospitalEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "apmed_equipment")
public class Urzadzenie extends HospitalEntity implements Serializable, Comparable<Urzadzenie> {

	private final static String NAZWAURZĄDZENIA = "nazwaUrządzenia";
	private final static String TYP = "typ";
	private final static String NUMERFABRYCZNY = "numerFabryczny";
	private final static String NRINWENTARZOWY = "nrInwentarzowy";
	private final static String KATEGORIA = "kategoria";
	private final static String ODDZIAŁ = "oddział";
	private final static String UWAGI = "uwagi";

	public static final Object[] VISIBLE = { NAZWAURZĄDZENIA, TYP, NUMERFABRYCZNY, NRINWENTARZOWY, KATEGORIA, ODDZIAŁ,
			UWAGI, };

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nazwaUrządzenia;
	private String typ;
	private String numerFabryczny;
	private String nrInwentarzowy;
	private String kategoria;
	private String oddział;
	private String uwagi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKategoria(String kategoria) {
		this.kategoria = kategoria;
	}

	public String getKategoria() {
		return kategoria;
	}

	public String getNazwaUrządzenia() {
		return nazwaUrządzenia;
	}

	public void setNazwaUrządzenia(String nazwaUrządzenia) {
		this.nazwaUrządzenia = nazwaUrządzenia;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getNumerFabryczny() {
		return numerFabryczny;
	}

	public void setNumerFabryczny(String numerFabryczny) {
		this.numerFabryczny = numerFabryczny;
	}

	public String getNrInwentarzowy() {
		return nrInwentarzowy;
	}

	public void setNrInwentarzowy(String nrInwentarzowy) {
		this.nrInwentarzowy = nrInwentarzowy;
	}

	public String getOddział() {
		return oddział;
	}

	public void setOddział(String oddział) {
		this.oddział = oddział;
	}

	public String getUwagi() {
		return uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(nazwaUrządzenia);
		builder.append(" [");
		builder.append(numerFabryczny);
		builder.append(" | ");
		builder.append(nrInwentarzowy);
		builder.append("]");
		return super.toString();
	}

	@Override
	public int compareTo(Urzadzenie o) {
		return this.toString().compareTo(o.toString());
	}

}
