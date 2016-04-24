package pl.kalisz.szpital.db.kancelaria;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.StringUtils;

import pl.kalisz.szpital.db.HospitalEntity;

/**
 * The Class User.
 */
@SuppressWarnings("serial")
@Entity(name="hospital_user")
@Table(name = "hospital_user", uniqueConstraints = @UniqueConstraint(columnNames = User.USERNAME))
public class User extends HospitalEntity {


	/** The Constant LOGIN. */
	public static final String USERNAME = "name";

	/** The login. */
	private String username;

	/** The password. */
	private String password;

	/** The roles. */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	private List<Role> roles;

	/**
	 * Instantiates a new user.
	 */
	public User() {
		//
	}

	/**
	 * Instantiates a new user.
	 * 
	 * @param username
	 *            the login
	 * @param password
	 *            the password
	 */
	public User(final String username, final String password) {
		this.username = username;
		this.password = password;
	}


	/**
	 * Gets the login.
	 * 
	 * @return the login
	 */
	public final String getLogin() {
		return username;
	}

	/**
	 * Sets the login.
	 * 
	 * @param login
	 *            the new login
	 */
	public final void setUsername(final String login) {
		this.username = login;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public final void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Gets the role.
	 * 
	 * @return the role
	 */
	public final List<Role> getRole() {
		return roles;
	}

	/**
	 * Adds the role.
	 * 
	 * @param role
	 *            the role
	 */
	public final void addRole(final Role role) {
		this.roles.add(role);
	}

	public final boolean hasRole(final String roleName) {
		if (roles != null && roles.size() > 0) {
			for (Role r : roles) {
				if (r.getRole().equals(roleName)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return StringUtils.capitalize(username);
	}

}
