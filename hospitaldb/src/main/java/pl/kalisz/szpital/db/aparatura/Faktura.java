package pl.kalisz.szpital.db.aparatura;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.kalisz.szpital.db.HospitalEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "apmed_invoice")
public class Faktura extends HospitalEntity implements Serializable {

	public final static String DATAFAKTURY = "dataFaktury";
	public final static String NRFAKTURY = "nrFaktury";
	public final static String WARTOSCNAFAKTURZE = "wartośćNaFakturze";
	public final static String UWAGI = "uwagi";

	public final static Object[] VISIBLE = { DATAFAKTURY, NRFAKTURY, WARTOSCNAFAKTURZE, UWAGI, };

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date dataFaktury;
	private String nrFaktury;
	private String wartośćNaFakturze;
	private String uwagi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDataFaktury() {
		return dataFaktury;
	}

	public void setDataFaktury(Date dataFaktury) {
		this.dataFaktury = dataFaktury;
	}

	public String getNrFaktury() {
		return nrFaktury;
	}

	public void setNrFaktury(String nrFaktury) {
		this.nrFaktury = nrFaktury;
	}

	public void setWartośćNaFakturze(String wartośćNaFakturze) {
		this.wartośćNaFakturze = wartośćNaFakturze;
	}
	
	public String getWartośćNaFakturze() {
		return wartośćNaFakturze;
	}

	public String getUwagi() {
		return uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

}
