package pl.kalisz.szpital.db.kancelaria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

/**
 * The Class Adres.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "hospital_phone")
public class Phone extends AbstractPojo {

	public enum PhoneType {
		HOME, WORK, MOBILE, FAX
	}

	private static final String TEL_REGEX = "^$|([0-9 \\(\\)\\+\\-]{6,19}[0-9])";
	private static final String ID = "id";

	/** The fax. */
	@NotNull
	@Pattern(regexp = TEL_REGEX, message = "Nieprawidłowy format numeru")
	private String number;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ID)
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private PhoneType type = PhoneType.HOME;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADRES_ID")
	private Adres adres;

	/**
	 * Instantiates a new adres.
	 */
	public Phone() {
		//
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public PhoneType getType() {
		return type;
	}

	public void setType(PhoneType type) {
		this.type = type;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(number);
		return buffer.toString().trim();
	}

}
