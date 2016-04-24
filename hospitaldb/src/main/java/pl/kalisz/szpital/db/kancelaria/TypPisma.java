package pl.kalisz.szpital.db.kancelaria;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import pl.kalisz.szpital.db.HospitalEntity;

/**
 * The Class TypPisma.
 */
@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = TypPisma.NAZWA) )
public class TypPisma implements Serializable, Comparable<TypPisma> {

	/** The Constant NAZWA. */
	public static final String NAZWA = "nazwa";

	/** The nazwa. */
	@Id
	private String nazwa;

	/**
	 * Instantiates a new typ pisma.
	 */
	public TypPisma() {
		//
	}

	/**
	 * Instantiates a new typ pisma.
	 * 
	 * @param nazwa
	 *            the nazwa
	 */
	public TypPisma(String nazwa) {
		this.nazwa = nazwa;
	}

	/**
	 * Sets the nazwa.
	 * 
	 * @param nazwa
	 *            the new nazwa
	 */
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	/**
	 * Gets the nazwa.
	 * 
	 * @return the nazwa
	 */
	public String getNazwa() {
		return nazwa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nazwa;
	}

	/**
	 * TypPisma.
	 */
	@Override
	public int compareTo(TypPisma o) {
		return this.nazwa.compareTo(o.getNazwa());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TypPisma) {
			return this.getNazwa().equals(((TypPisma) obj).getNazwa());
		} else {
			return false;
		}
	}

}
