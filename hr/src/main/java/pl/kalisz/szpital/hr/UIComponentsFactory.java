package pl.kalisz.szpital.hr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.tepi.filtertable.paged.PagedFilterControlConfig;
import org.vaadin.haijian.ExcelExporter;

import pl.kalisz.szpital.hr.data.entity.Kontraktowiec;
import pl.kalisz.szpital.hr.table.generator.GeneratedColumnFactory;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class UIComponentsFactory {
	/**
	 * 
	 */
	private final HRUI hrui;

	Logger logger = Logger.getLogger(UIComponentsFactory.class);

	public UIComponentsFactory(HRUI hrui) {
		this.hrui = hrui;
	}

	public Component createTableControls() {
		PagedFilterControlConfig config = new PagedFilterControlConfig();
		config.setPageLengthsAndCaptions(Arrays
				.asList(new Integer[] { ((HRUI) UI.getCurrent()).pageLength }));
		config.setNext("NASTĘPNA STRONA");
		config.setLast("OSTATNIA");
		config.setFirst("PIERWSZA");
		config.setPrevious("POPRZEDNIA");
		config.setPage("");
		config.setItemsPerPage("OSÓB NA STRONĘ");
		HorizontalLayout controls = this.hrui.table.createControls(config);
		return controls;
	}

	public Component createUpdateButton() {
		Button button = new Button("Odśwież");
		button.addClickListener(e -> {
			try {
				hrui.getDBHelper().getContainer(Kontraktowiec.class).refresh();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		return button;
	}

	public Component createEditButton() {
		Button button = new Button("Edycja");
		button.addClickListener(e -> {
			if (this.hrui.table.isEditable()) {
				this.hrui.table.setEditable(false);
				this.hrui.table.commit();
				button.setCaption("Edycja");
			} else {
				this.hrui.table.setEditable(true);
				button.setCaption("Zapisz");
			}
		});
		return button;
	}

	public Component createClearSearchButton() {
		Button button = new Button("Wyczyść wyszukiwanie");
		button.addClickListener(e -> {
			this.hrui.table.resetFilters();
			try {
				this.hrui.getDBHelper().getContainer(Kontraktowiec.class)
						.removeAllContainerFilters();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		return button;
	}

	Component createNewButton() {
		Button newButton = new Button("Nowy");
		newButton
				.addClickListener(e -> {
					// add
					JPAContainer<Kontraktowiec> c;
					try {
						c = this.hrui.getDBHelper().getContainer(
								Kontraktowiec.class);
						Object id = c.addItem();
						// set visible
						c.removeAllContainerFilters();
						c.addContainerFilter(new Compare.Equal(
								Kontraktowiec.ID, id));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
		return newButton;
	}

	Component createExportButton(DBHelper db) {
		ExcelExporter excelExport;

		Table t = new Table("Tabela", this.hrui.table.getContainerDataSource());
		t.setVisible(false);
		this.hrui.root.addComponent(t);
		try {
			Object[] visible = this.hrui.table.getVisibleColumns();
			ArrayList<Object> visibleList = (ArrayList<Object>) ListUtils
					.subtract(Arrays.asList(visible), Arrays
							.asList(GeneratedColumnFactory.GENERATED_COLUMNS));
			JPAContainer<Kontraktowiec> c = db
					.getContainer(Kontraktowiec.class);
			excelExport = new ExcelExporter(
					this.hrui.table.getContainerDataSource()
					// t
					, visibleList.toArray()) {
				@Override
				public void attach() {
					super.attach();
				}

				public void clickListener() {
					addClickListener(e -> {
						setContainerToBeExported(t.getContainerDataSource());
						setVisibleColumns(getVisibleColumns().toArray());
					});

				}

				@Override
				public void setVisibleColumns(Object[] visibleColumns) {
					logger.debug("setting visible");
					super.setVisibleColumns(visibleColumns);
				}
			};
			return excelExport;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected ArrayList<Object> getVisibleColumns() {
		// hide generated
		ArrayList<Object> visibleColumns = (ArrayList<Object>) ListUtils
				.subtract(Arrays.asList(this.hrui.table.getVisibleColumns()),
						Arrays.asList(GeneratedColumnFactory.GENERATED_COLUMNS));
		// hide collapsed
		for (Object o : this.hrui.table.getVisibleColumns()) {
			if (this.hrui.table.isColumnCollapsed(o)) {
				visibleColumns.remove(o);
			}
		}
		logger.info("Columns: " + visibleColumns);
		return visibleColumns;
	}

	public void setStyles() {
		Styles styles = Page.getCurrent().getStyles();
		styles.add(".v-app .v-table-cell-content-red { background-color:red !important; }");
		styles.add(".v-app .v-table-cell-content-orange { background-color:orange !important; }");
		styles.add(".v-app .v-table-cell-content-yellow { background-color:yellow !important; }");
	}

	public static AbstractField<?> twoOptionsCombo(String option1,
			String option2) {
		ComboBox box = new ComboBox();
		Boolean tak = new Boolean(true);
		Boolean nie = new Boolean(false);
		box.addItem(tak);
		box.setItemCaption(tak, option1);
		box.addItem(nie);
		box.setItemCaption(nie, option2);
		return box;
	}
}