package pl.kalisz.szpital.hr;

import java.util.Locale;

import org.tepi.filtertable.paged.PagedFilterTable;

import pl.kalisz.szpital.hr.data.entity.Kontraktowiec;
import pl.kalisz.szpital.hr.table.HRTable;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

/**
 * The Class HRUI.
 */
@SuppressWarnings("serial")
@Theme("valo")
@Title("WSZ HR")
public class HRUI extends UI {

	public int pageLength = 20;
	
	/** The root. */
	final CssLayout root = new CssLayout();
	PagedFilterTable<JPAContainer<Kontraktowiec>> table;
	private DBHelper dbHelper = new DBHelper();

	public DBHelper getDBHelper() {
		return dbHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 */
	@Override
	protected void init(VaadinRequest request) {
		setLocale(new Locale("pl", "PL"));
		setContent(root);
		root.setSizeFull();
		try {
			table = new HRTable(dbHelper);
		} catch (Exception e) {
			e.printStackTrace();
		}
		root.addComponent(table);
		UIComponentsFactory componentsFactory = new UIComponentsFactory(this);
		root.addComponent(componentsFactory.createExportButton(dbHelper));
		root.addComponent(componentsFactory.createNewButton());
		root.addComponent(componentsFactory.createEditButton());
		root.addComponent(componentsFactory.createUpdateButton());
		root.addComponent(componentsFactory.createClearSearchButton());
		root.addComponent(componentsFactory.createTableControls());
		componentsFactory.setStyles();
	}

}
