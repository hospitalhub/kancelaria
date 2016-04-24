package pl.kalisz.szpital.kancelaria.data.pojo;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Index;

import pl.kalisz.szpital.kancelaria.data.enums.KomorkaOrganizacyjna;


/**
 * The Class Transaction.
 */
@SuppressWarnings("serial")
@Entity
public class Dekretacja implements Serializable {

  /** The Constant DATA_STR. */
  public static final String DATA_STR = "data";

  /** The Constant ID. */
  public static final String ID = "id";

  public static final String USERID = "userid";


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Temporal(value = TemporalType.TIMESTAMP)
  private Date data;

  KomorkaOrganizacyjna komorkaOrganizacyjna;

  /** The user. */
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = USERID, referencedColumnName = User.ID,
      foreignKey = @ForeignKey(name = User.ID) )
  @Index
  private User user;

  /**
   * Instantiates a new transaction.
   */
  public Dekretacja() {
    //
  }



  /**
   * Gets the data.
   * 
   * @return the data
   */
  public final Date getData() {
    return data;
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
   * Sets the data.
   * 
   * @param data the new data
   */
  public final void setData(final Date data) {
    this.data = data;
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
   * Gets the user.
   * 
   * @return the user
   */
  public final User getUser() {
    return user;
  }

  /**
   * Sets the user.
   * 
   * @param user the new user
   */
  public final void setUser(final User user) {
    this.user = user;
  }

  public KomorkaOrganizacyjna getKomorkaOrganizacyjna() {
    return komorkaOrganizacyjna;
  }

  public void setKomorkaOrganizacyjna(KomorkaOrganizacyjna komorkaOrganizacyjna) {
    this.komorkaOrganizacyjna = komorkaOrganizacyjna;
  }

}
