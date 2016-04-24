package pl.kalisz.szpital.db;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;

/**
 * The Class DbHelper.
 */
@SuppressWarnings("serial")
@UIScoped
public class DbHelper implements Serializable {

	private final static Logger logger = Logger.getLogger(DbHelper.class);

	/** The Constant JPA. */
	public static final String JPA = "jpa";

	/** The em. */
	@PersistenceContext(unitName = JPA)
	protected EntityManager em;

	private ConcurrentHashMap<Class, JPAContainer<?>> map = new ConcurrentHashMap<Class, JPAContainer<?>>();
	private ConcurrentHashMap<Class, BeanItemContainer<?>> mapEnum = new ConcurrentHashMap<Class, BeanItemContainer<?>>();

	private JPAContainer get(Class className) {
		if (!map.contains(className)) {
			logger.info("No jpa container => generating");
			JPAContainer container = JPAContainerFactory.make(className, JPA);
			map.put(className, container);
		}
		return map.get(className);
	}

	private BeanItemContainer getEnum(Class className) {
		if (!mapEnum.contains(className)) {
			logger.info("No beanitem (enum) container => generating");
			BeanItemContainer container = new BeanItemContainer(className);
			container.addAll(Arrays.asList(className.getEnumConstants()));
			mapEnum.put(className, container);
		}
		return mapEnum.get(className);
	}

	@Override
	public final String toString() {
		return "D>B> HE: LP er";
	}

	@PostConstruct
	public final void init() {
		System.out.println("POST CONSTRUCT DB HELPER");
	}

	/**
	 * Gets the container.
	 * 
	 * @param containerType
	 *            the container type
	 * @return the container
	 */
	public final Container getContainer(Class<?> containerClass) {
		System.out.println(containerClass);
		System.out.println(containerClass.getSimpleName());
		logger.info("Load container: " + containerClass.getSimpleName());
		switch (containerClass.getSimpleName()) {
		case "Transaction":
		case "Adres":
		case "TypPisma":
		case "Zlecenie":
		case "Urzadzenie":
		case "User":
		case "Faktura":
		case "Oferta":
		case "Umowa":
		case "ZlecenieDetails":
			return get(containerClass);
		case "TypWiadomosci":
		case "KomorkaOrganizacyjna":
			return getEnum(containerClass);
		default:
			logger.fatal("DbHelper failed:" + containerClass.toString());
			throw new IllegalArgumentException("DbHelper"
					+ containerClass.toString());
		}
	}

}
