package pl.kalisz.szpital.hr;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

import pl.kalisz.szpital.hr.data.entity.Kontraktowiec;
import pl.kalisz.szpital.hr.data.entity.Specjalizacja;
import pl.kalisz.szpital.hr.helper.File2StringUtil;

public class DBHelper {

	private JPAContainer<Kontraktowiec> kontraktowiecContainer;
	private JPAContainer<Specjalizacja> specjalizacjaContainer;
	public final static String JPA = "kadry";

	public DBHelper() {
		// Map props = new HashMap();
		// props.put(PersistenceUnitProperties.JDBC_URL,
		// "jdbc:mysql://localhost:3306/hospital?useUnicode=yes&amp;characterEncoding=UTF-8");
		// props.put(PersistenceUnitProperties.JDBC_USER, "root");
		// props.put(PersistenceUnitProperties.JDBC_PASSWORD, "nA8Wedeg");
		// EntityManagerFactory emf =
		// Persistence.createEntityManagerFactory("kadry", props);
		createAndFillSpecjalizacjeContainer();
		kontraktowiecContainer = JPAContainerFactory.make(Kontraktowiec.class,
				JPA);
		specjalizacjaContainer = JPAContainerFactory.makeReadOnly(
				Specjalizacja.class, JPA);
		specjalizacjaContainer.sort(new Object[] { Specjalizacja.NAZWA },
				new boolean[] { true });
		fillKontraktowiecContainer();
	}

	private Specjalizacja getByNazwa(String nazwa) {
		EntityManager em = specjalizacjaContainer.getEntityProvider()
				.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Specjalizacja> c = cb.createQuery(Specjalizacja.class);
		Root<Specjalizacja> kontraktowiec = c.from(Specjalizacja.class);
		c.where(cb.equal(kontraktowiec.get(Specjalizacja.NAZWA), nazwa));
		TypedQuery tq = em.createQuery(c);
		List<Specjalizacja> list = tq.getResultList();
		return list.get(0);
	}

	private void fillKontraktowiecContainer() {
		if (kontraktowiecContainer.size() == 0) {
			try {
				String s = File2StringUtil
						.readFromResourceFile("kontraktowcy.csv");
				for (String line : s.split("\n")) {
					String[] kontraktowiecArray = line.split("\t");
					Kontraktowiec kontraktowiec = new Kontraktowiec();
					kontraktowiec.setNazwisko(kontraktowiecArray[0]);
					kontraktowiec.setImie(kontraktowiecArray[1]);
					Set<Specjalizacja> specjalizacje = new HashSet<Specjalizacja>();
					for (String spec : kontraktowiecArray[2].split(",")) {
						specjalizacje.add(getByNazwa(spec));
					}
					kontraktowiec.setSpecjalizacje(specjalizacje);
					kontraktowiecContainer.addEntity(kontraktowiec);
				}
				kontraktowiecContainer.commit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static JPAContainer<Specjalizacja> createAndFillSpecjalizacjeContainer() {
		JPAContainer<Specjalizacja> container = JPAContainerFactory.make(
				Specjalizacja.class, JPA);
		HashSet<Specjalizacja> specjalizacje = Specjalizacja.generate();
		if (container.size() == 0) {
			for (Specjalizacja s : specjalizacje) {
				container.addEntity(s);
			}
			container.commit();
		}
		container.sort(new Object[] { Specjalizacja.NAZWA },
				new boolean[] { true });
		return container;
	}

	public JPAContainer getContainer(Class c) throws Exception {
		switch (c.getSimpleName()) {
		case "Kontraktowiec":
			return kontraktowiecContainer;
		case "Specjalizacja":
			return specjalizacjaContainer;
		default:
			throw new Exception("No container " + c.getName());
		}
	}

}