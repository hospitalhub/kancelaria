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
@Table(name = "apmed_errand")
public class ZlecenieDetails extends HospitalEntity implements Serializable {

	public final static String URZADZENIE = "urządzenie";
	public final static String OPIS_AWARII = "opisAwarii";
	public final static String SPOSÓBREALIZACJI = "sposóbRealizacji";
	public final static String DATAWYSŁANIA = "dataWysłania";
	public final static String DATAREALIZACJI = "dataRealizacji";
	public final static String STATUS = "status";

	public final static Object[] VISIBLE = { URZADZENIE, OPIS_AWARII,
			SPOSÓBREALIZACJI, DATAWYSŁANIA, DATAREALIZACJI, STATUS };

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long nrZlecenia;
	private Urzadzenie urządzenie;
	private String opisAwarii;
	private String sposóbRealizacji;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date dataWysłania;
	@Column
	@Temporal(value = TemporalType.DATE)
	private Date dataRealizacji;
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNrZlecenia(Long nrZlecenia) {
		this.nrZlecenia = nrZlecenia;
	}

	public Long getNrZlecenia() {
		return nrZlecenia;
	}

	public Urzadzenie getUrządzenie() {
		return urządzenie;
	}

	public void setUrządzenie(Urzadzenie urządzenie) {
		this.urządzenie = urządzenie;
	}

	public String getOpisAwarii() {
		return opisAwarii;
	}

	public void setOpisAwarii(String opisAwarii) {
		this.opisAwarii = opisAwarii;
	}

	public String getSposóbRealizacji() {
		return sposóbRealizacji;
	}

	public void setSposóbRealizacji(String sposóbRealizacji) {
		this.sposóbRealizacji = sposóbRealizacji;
	}

	public Date getDataWysłania() {
		return dataWysłania;
	}

	public void setDataWysłania(Date dataWysłania) {
		this.dataWysłania = dataWysłania;
	}

	public Date getDataRealizacji() {
		return dataRealizacji;
	}

	public void setDataRealizacji(Date dataRealizacji) {
		this.dataRealizacji = dataRealizacji;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return nrZlecenia.toString();
	}

}
