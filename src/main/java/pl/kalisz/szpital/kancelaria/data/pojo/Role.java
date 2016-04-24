package pl.kalisz.szpital.kancelaria.data.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class Role.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ROLES")
public class Role implements Serializable {

  /** The id. */
  @Id
  @GeneratedValue
  private Integer id;

  /** The role. */
  private String role;

  /** The user list. */
  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "USER_ROLES", joinColumns = {@JoinColumn(name = "role_id",
      referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "user_id",
      referencedColumnName = "id")})
  private List<User> userList;

  /** The Constant ADMIN. */
  public static final String ADMIN = "admin";

  /** The Constant POWER_USER. */
  public static final String POWER_USER = "power user";

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
   * Gets the role.
   * 
   * @return the role
   */
  public final String getRole() {
    return role;
  }

  /**
   * Sets the role.
   * 
   * @param role the new role
   */
  public final void setRole(final String role) {
    this.role = role;
  }

  /**
   * Gets the user list.
   * 
   * @return the user list
   */
  public final List<User> getUserList() {
    return userList;
  }

  /**
   * Sets the user list.
   * 
   * @param userList the new user list
   */
  public final void setUserList(final List<User> userList) {
    this.userList = userList;
  }

}
