package pl.kalisz.szpital.kancelaria.data.db;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.cdi.UIScoped;

import pl.kalisz.szpital.kancelaria.data.pojo.Address;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.TypPisma;
import pl.kalisz.szpital.kancelaria.data.pojo.User;

@SuppressWarnings("serial")
@UIScoped
public class ContainerFactory implements Serializable {
  
/* context.xml :
  <Resource name="jpa" auth="Container" type="javax.sql.DataSource"
               maxActive="100" maxIdle="30" maxWait="10000"
               username="user" password="pass" driverClassName="com.mysql.jdbc.Driver"
               url="jdbc:mysql://localhost:3306/wordpress?useUnicode=yes&amp;characterEncoding=UTF-8" 
               removeAbandoned="true" removeAbandonedTimeout="20" logAbandoned="true"
               factory="org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory"
               validationQuery="select 1" minEvictableIdleTimeMillis="3600000" timeBetweenEvictionRunsMillis="1800000"
               numTestsPerEvictionRun="10" testWhileIdle="true" testOnBorrow="true" testOnReturn="false"
               />
               <!-- ?useUnicode=yes&amp;characterEncoding=UTF-8 -->
*/
  /** The Constant ADRES_CONTAINER. */
  public static final int ADDRESS_CONTAINER = 1;

  /** The Constant JPA. */
  private static final String JPA = "jpa";

  /** The Constant TRANSACTION_CONTAINER. */
  public static final int TRANSACTION_CONTAINER = 0;

  /** The Constant TYP_PISMA_CONTAINER. */
  public static final int TYP_PISMA_CONTAINER = 3;

  /** The Constant USER_CONTAINER. */
  public static final int USER_CONTAINER = 2;

  public static final String PERSISTENCE_UNIT = "jpa";

  /** The adres container. */
  private JPAContainer<Address> addressContainer;

  /** The em. */
  @PersistenceContext(unitName = PERSISTENCE_UNIT, name = PERSISTENCE_UNIT)
  protected EntityManager em;

  /** The transaction container. */
  private JPAContainer<Transaction> transactionContainer;

  /** The typpisma container. */
  private JPAContainer<TypPisma> typpismaContainer;

  /** The user container. */
  private JPAContainer<User> userContainer;

  /**
   * Gets the container.
   * 
   * @param containerType the container type
   * @return the container
   */
  @SuppressWarnings("rawtypes")
  public final JPAContainer getContainer(final int containerType) {
    switch (containerType) {
      case TRANSACTION_CONTAINER:
        if (transactionContainer == null) {
          transactionContainer = JPAContainerFactory.make(Transaction.class, JPA);
          transactionContainer.setApplyFiltersImmediately(true);
        }
        return transactionContainer;
      case ADDRESS_CONTAINER:
        if (addressContainer == null) {
          addressContainer = JPAContainerFactory.make(Address.class, JPA);
        }
        return addressContainer;
      case USER_CONTAINER:
        if (userContainer == null) {
          userContainer = JPAContainerFactory.make(User.class, JPA);
          userContainer.setAutoCommit(true);
        }
        return userContainer;
      case TYP_PISMA_CONTAINER:
        if (typpismaContainer == null) {
          typpismaContainer = JPAContainerFactory.make(TypPisma.class, JPA);
        }
        return typpismaContainer;
      default:
        throw new IllegalArgumentException();
    }
  }


}
