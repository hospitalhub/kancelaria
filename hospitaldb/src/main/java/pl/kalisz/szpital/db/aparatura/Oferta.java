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
@Table(name = "apmed_offer")
public class Oferta extends HospitalEntity implements Serializable {

	public final static String DATAOFERTY = "dataOferty";
	public final static String DATAAKCEPTACJI = "dataAkceptacji";
	public final static String WARTOSCOFERTY = "wartośćOferty";
	public final static String UWAGI = "uwagi";

	public final static Object[] VISIBLE = { DATAOFERTY, DATAAKCEPTACJI, WARTOSCOFERTY, UWAGI, };

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date dataOferty;
	private String wartośćOferty;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date dataAkceptacji;
	private String uwagi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataOferty() {
		return dataOferty;
	}

	public void setDataOferty(Date dataOferty) {
		this.dataOferty = dataOferty;
	}

	public String getWartośćOferty() {
		return wartośćOferty;
	}

	public void setWartośćOferty(String wartośćOferty) {
		this.wartośćOferty = wartośćOferty;
	}

	public Date getDataAkceptacji() {
		return dataAkceptacji;
	}

	public void setDataAkceptacji(Date dataAkceptacji) {
		this.dataAkceptacji = dataAkceptacji;
	}

	public String getUwagi() {
		return uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

}
