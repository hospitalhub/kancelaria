package pl.kalisz.szpital.kancelaria.data.pojo;

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

import pl.kalisz.szpital.kancelaria.data.enums.Theme;

/**
 * The Class User.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = User.LOGIN) )
public class User implements Serializable {

  /** The Constant ID. */
  public static final String ID = "id";

  /** The Constant LOGIN. */
  public static final String LOGIN = "login";

  public static final String THEME = "theme";

  /** The id. */
  @Id
  @GeneratedValue
  private Integer id;

  /** The login. */
  private String login;

  /** The password. */
  private String password;

  /** The roles. */
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "USER_ROLES",
      joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
      inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
  private List<Role> roles;

  private Theme theme;
  
  /**
   * Instantiates a new user.
   */
  public User() {
    //
  }

  /**
   * Instantiates a new user.
   * 
   * @param login the login
   * @param password the password
   */
  public User(final String login, final String password) {
    this.login = login;
    this.password = password;
  }

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public final Integer getId() {
    return id;
  }

  /**
   * Sets the id.
   * 
   * @param id the new id
   */
  public final void setId(final Integer id) {
    this.id = id;
  }

  /**
   * Gets the login.
   * 
   * @return the login
   */
  public final String getLogin() {
    return login;
  }

  /**
   * Sets the login.
   * 
   * @param login the new login
   */
  public final void setLogin(final String login) {
    this.login = login;
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
   * @param password the new password
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
   * @param role the role
   */
  public final void addRole(final Role role) {
    this.roles.add(role);
  }

  /**
   * Checks for role.
   * 
   * @param roleName the role name
   * @return true, if successful
   */
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
    return StringUtils.capitalize(login);
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  public Theme getTheme() {
    return theme;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof User) {
      return getLogin().equals(((User) obj).getLogin());
    } else {
      return false;
    }
  }
  
}
