package pl.kalisz.szpital.hr.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "hr_absence")
public class Absence {

	public static final String ID = "id";
	public final static String KONTRAKTOWIEC_ID = "kontraktowiecId";
	public final static String BEGINNING = "beginning";
	public final static String END = "end";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = KONTRAKTOWIEC_ID)
	private Long kontraktowiecId;

	@Column
	@Temporal(value = TemporalType.DATE)
	private Date beginning;

	@Column
	@Temporal(value = TemporalType.DATE)
	private Date end;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBeginning() {
		return beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public Long getKontraktowiecId() {
		return kontraktowiecId;
	}
	
	public void setKontraktowiecId(Long kontraktowiecId) {
		this.kontraktowiecId = kontraktowiecId;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getBeginning());
		sb.append(" - ");
		sb.append(getEnd());
		return sb.toString();
	}

}
