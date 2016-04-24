package pl.kalisz.szpital.hr.table;

import pl.kalisz.szpital.hr.DBHelper;
import pl.kalisz.szpital.hr.HRUI;
import pl.kalisz.szpital.hr.data.entity.Absence;
import pl.kalisz.szpital.hr.data.entity.Kontraktowiec;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class AbsenceWindow extends Window {
	VerticalLayout vl = new VerticalLayout();
	Object target;

	public AbsenceWindow(Object target) {
		this.target = target;
	}

	@SuppressWarnings("unchecked")
	private void init() {
		setContent(vl);
		JPAContainer<Kontraktowiec> kontraktowiecContainer;
		try {
			kontraktowiecContainer = ((HRUI) UI.getCurrent()).getDBHelper()
					.getContainer(Kontraktowiec.class);
			EntityItem<Kontraktowiec> kontraktowiecItem = (EntityItem<Kontraktowiec>) kontraktowiecContainer
					.getItem(target);
			Kontraktowiec kontraktowiec = kontraktowiecItem.getEntity();
			vl.addComponent(new Label("Nieobecności: "
					+ kontraktowiec.getNazwiskoImie()));
			JPAContainer<Absence> container = JPAContainerFactory.make(
					Absence.class, DBHelper.JPA);
			container.addContainerFilter(new Equal(
					Absence.KONTRAKTOWIEC_ID, kontraktowiec.getId()));
			Grid grid = new Grid();
			grid.setContainerDataSource(container);
			grid.removeColumn(Absence.ID);
			grid.removeColumn(Absence.KONTRAKTOWIEC_ID);
			grid.setEditorEnabled(true);
			grid.setHeaderVisible(true);
			grid.setEditorCancelCaption("Anuluj");
			grid.setEditorSaveCaption("Zapisz");
			grid.getColumn(Absence.BEGINNING).setHeaderCaption("Początek");
			grid.getColumn(Absence.END).setHeaderCaption("Koniec");
			Button b = new Button("Dodaj");
			Integer id = (Integer) kontraktowiecItem.getItemId();
			b.addClickListener(event -> {
				Absence a = new Absence();
				a.setKontraktowiecId(id.longValue());
				Object itemId = container.addEntity(a);
				System.out.println("add" + itemId);
			});
			vl.addComponent(b);
			vl.addComponent(grid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void attach() {
		init();
		super.attach();
	}
}