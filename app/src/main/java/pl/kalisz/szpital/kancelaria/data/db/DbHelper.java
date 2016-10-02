package pl.kalisz.szpital.kancelaria.data.db;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import pl.kalisz.szpital.kancelaria.data.pojo.Adres;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.TypPisma;
import pl.kalisz.szpital.kancelaria.data.pojo.User;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

/**
 * The Class DbHelper.
 */
@SuppressWarnings("serial")
public class DbHelper implements Serializable {

	/** The Constant ADRES_CONTAINER. */
	public static final int ADRES_CONTAINER = 1;

	/** The Constant JPA. */
	private static final String JPA = "jpa";

	/** The Constant TRANSACTION_CONTAINER. */
	public static final int TRANSACTION_CONTAINER = 0;

	/** The Constant TYP_PISMA_CONTAINER. */
	public static final int TYP_PISMA_CONTAINER = 3;

	/** The Constant USER_CONTAINER. */
	public static final int USER_CONTAINER = 2;

	/** The Constant ADDRESS_BOUND_TRANSACTION_CONTAINER. */
	public static final int ADDRESS_BOUND_TRANSACTION_CONTAINER = 4;

	/** The adres container. */
	private JPAContainer<Adres> adresContainer;

	/** The em. */
	@PersistenceUnit
	protected EntityManager em;

	/** The transaction container. */
	private JPAContainer<Transaction> transactionContainer;

	/** The address bound transaction container. */
	private JPAContainer<Transaction> addressBoundTransactionContainer;

	/** The typpisma container. */
	private JPAContainer<TypPisma> typpismaContainer;

	/** The user container. */
	private JPAContainer<User> userContainer;

	/**
	 * Instantiates a new db helper.
	 */
	public DbHelper() {
		createContainers();
		checkNumOfUsers();
	}

	private void checkNumOfUsers() {
		System.out.println("USER SIZE " + userContainer.size());
	}

	private void createContainers() {
		adresContainer = JPAContainerFactory.make(Adres.class, JPA);
		transactionContainer = JPAContainerFactory.make(Transaction.class, JPA);
		transactionContainer.setApplyFiltersImmediately(true);
		addressBoundTransactionContainer = JPAContainerFactory.make(Transaction.class, JPA);
		userContainer = JPAContainerFactory.make(User.class, JPA);
		typpismaContainer = JPAContainerFactory.make(TypPisma.class, JPA);

	}

	/**
	 * Gets the container.
	 * 
	 * @param containerType
	 *            the container type
	 * @return the container
	 */
	@SuppressWarnings("rawtypes")
	public final JPAContainer getContainer(final int containerType) {
		switch (containerType) {
		case TRANSACTION_CONTAINER:
			return transactionContainer;
		case ADDRESS_BOUND_TRANSACTION_CONTAINER:
			return addressBoundTransactionContainer;
		case ADRES_CONTAINER:
			return adresContainer;
		case USER_CONTAINER:
			return userContainer;
		case TYP_PISMA_CONTAINER:
			return typpismaContainer;
		default:
			throw new IllegalArgumentException();
		}
	}

}
