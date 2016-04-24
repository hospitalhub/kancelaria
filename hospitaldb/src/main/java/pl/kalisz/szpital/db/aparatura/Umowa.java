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
@Table(name = "apmed_agreement")
public class Umowa extends HospitalEntity implements Serializable {

	public final static String DATAUMOWY = "dataUmowy";
	public final static String DATAKONCAUMOWY = "dataKoncaUmowy";
	public final static String UWAGI = "uwagi";

	public final static Object[] VISIBLE = { DATAUMOWY, DATAKONCAUMOWY, UWAGI, };

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date dataUmowy;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date dataKoncaUmowy;
	private String uwagi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataOferty() {
		return dataUmowy;
	}

	public void setDataOferty(Date dataOferty) {
		this.dataUmowy = dataOferty;
	}

	public Date getDataUmowy() {
		return dataUmowy;
	}

	public void setDataUmowy(Date dataUmowy) {
		this.dataUmowy = dataUmowy;
	}

	public Date getDataKoncaUmowy() {
		return dataKoncaUmowy;
	}

	public void setDataKoncaUmowy(Date dataKoncaUmowy) {
		this.dataKoncaUmowy = dataKoncaUmowy;
	}

	public String getUwagi() {
		return uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

}
