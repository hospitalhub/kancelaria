package pl.kalisz.szpital.aparatura.grid;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.HospitalEntity;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@UIScoped
public abstract class GridLayout<T extends HospitalEntity> extends
		VerticalLayout {

	@Inject
	DbHelper dbHelper;

	private Class containerClass;

	protected GeneratedPropertyContainer container;
	protected JPAContainer<T> jpaContainer;

	@PostConstruct
	public void pc() {
		System.out.println("GL PC");
		System.out.println("GL PC" + this.containerClass);
		this.jpaContainer = (JPAContainer) dbHelper.getContainer(containerClass);
		this.container = new GeneratedPropertyContainer(jpaContainer);
		init();
	}

	public GridLayout(Class containerClass) {
		System.out.println("GL C");
		System.out.println(containerClass);
		this.containerClass = containerClass;
	}

	private void addDelButton() {
		container.addGeneratedProperty("Usuń",
				new PropertyValueGenerator<String>() {

					@Override
					public String getValue(Item item, Object itemId,
							Object propertyId) {
						return item.toString();
					}

					@Override
					public Class<String> getType() {
						return String.class;
					}

				});
	}

	private void init() {
		Grid grid = HospitalGridFactory.getGrid(container,
				jpaContainer.getEntityClass());
		createHeaderRow(grid);
		Button addButton = new Button("Dodaj");
		addButton.addClickListener(event -> {
			// XXX
				Object id = jpaContainer.addItem();
				EntityItem<T> z = jpaContainer.getItem(id);
				updateItem(z, id);
			});
		addComponent(grid);
		setExpandRatio(grid, 20);
		addComponent(addButton);
		setSizeFull();
	}

	private void createHeaderRow(Grid grid) {
		HeaderRow filterRow = grid.appendHeaderRow();
		for (Object pid : grid.getContainerDataSource()
				.getContainerPropertyIds()) {
			HeaderCell cell = filterRow.getCell(pid);

			TextField filterField = new TextField();
			filterField.setColumns(8);
			filterField.addTextChangeListener(change -> {
				jpaContainer.removeContainerFilters(pid);

				if (!change.getText().isEmpty())
					jpaContainer.addContainerFilter(new SimpleStringFilter(pid,
							change.getText(), true, false));
			});
			cell.setComponent(filterField);
		}
	}

	protected abstract void updateItem(EntityItem<T> z, Object id);

}