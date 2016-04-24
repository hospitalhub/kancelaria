package pl.kalisz.szpital.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@SuppressWarnings("serial")
@MappedSuperclass
abstract public class HospitalEntity implements Serializable {

	/** The Constant ID. */
	public static final String ID = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Column(nullable = false)
	@Version
	protected Long consistencyVersion;

	public HospitalEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConsistencyVersion() {
		return consistencyVersion;
	}

	public void setConsistencyVersion(Long consistencyVersion) {
		this.consistencyVersion = consistencyVersion;
	}

}
