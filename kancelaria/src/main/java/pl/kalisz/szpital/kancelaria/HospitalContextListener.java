package pl.kalisz.szpital.kancelaria;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.authentication.util.PasswordUtil;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.kancelaria.utils.File2StringUtil;

@WebListener
public class HospitalContextListener implements ServletContextListener {

	private Vector<User> users = new Vector<User>();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		loadCSVUsers();
		setProps();
		registerFacade();
		fillUsers();
		updateTransactionUserIds();
		updateTables();
	}

	private void updateTables() {
		// TODO
		// ADRES alter table drop column tel1, tel2, fax
	}

	private void updateTransactionUserIds() {
		/*
		for (User u : users) {
			String query = "select t FROM Transaction t WHERE t.user = :userid";
			Long newId = u.getId();
			Long oldId = u.getId() - 1000 + 10;
			Map<String, Object> parameters = new HashMap<String, Object>();
			// u.setId(oldId);
			parameters.put("userid", u);
			List<Transaction> ts = FacadeFactory.getFacade().list(query,
					parameters);
			User u1 = new User();
			u1.setId(newId);
			u1.setUsername(u.getUsername());
			for (Transaction t : ts) {
				// t.setUser(u1);
			}
			try {
				System.out.println(u.getId() + " : " + ts.size());
				FacadeFactory.getFacade().storeAll(ts);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int updatedRows = ts.size();
			System.out.println("UPDATE " + updatedRows + " FOR "
					+ u.getUsername());
		}
		*/
	}

	private void loadCSVUsers() {
		try {
			String string = File2StringUtil.readFromResourceFile("users.csv");
			for (String s : string.split("\n")) {
				User u = new User();
				String record[] = s.split(",");
				u.setId(Long.parseLong(record[0]));
				u.setUsername(record[1]);
				u.setPassword(PasswordUtil.generateHashedPassword(record[2]));
				users.add(u);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setProps() {
		System.setProperty("authentication.password.validation.length", "1");
		System.setProperty("authentication.username.validation.length", "2");
	}

	private void registerFacade() {
		try {
			if (FacadeFactory.getFacade() == null) {
				FacadeFactory.registerFacade(DbHelper.JPA, true);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void fillUsers() {
		for (User u : users) {
			try {
				FacadeFactory.getFacade().store(u);
			} catch (Exception e) {
				System.err.println("exists or other ex." + u.getUsername());
			}
		}
	}

}
