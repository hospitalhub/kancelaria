package pl.kalisz.szpital.kancelaria.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.gridutil.cell.CellFilterChangedListener;
import org.vaadin.gridutil.cell.CellFilterComponent;
import org.vaadin.gridutil.cell.GridCellFilter;
import org.vaadin.gridutil.converter.SimpleStringConverter;
import org.vaadin.gridutil.renderer.EditDeleteButtonValueRenderer;
import org.vaadin.gridutil.renderer.EditDeleteButtonValueRenderer.EditDeleteButtonClickListener;
import org.vaadin.teemusa.gridextensions.contextclick.ContextClickEvent;
import org.vaadin.teemusa.gridextensions.contextclick.ContextClickExtension;
import org.vaadin.teemusa.gridextensions.contextclick.ContextClickListener;
import org.vaadin.teemusa.gridextensions.sidebarmenuextension.SidebarMenuExtension;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.data.pojo.Address;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.TypPisma;
import pl.kalisz.szpital.kancelaria.data.pojo.User;
import pl.kalisz.szpital.kancelaria.ui.editor.transaction.CancelClickListener;
import pl.kalisz.szpital.kancelaria.ui.editor.transaction.KOChangeListener;
import pl.kalisz.szpital.kancelaria.ui.editor.transaction.KONewItemHandler;
import pl.kalisz.szpital.kancelaria.ui.editor.transaction.SaveClickListener;
import pl.kalisz.szpital.kancelaria.ui.editor.transaction.TransactionWindow;
import pl.kalisz.szpital.kancelaria.utils.DateRange;
import pl.kalisz.szpital.kancelaria.utils.DateRangeEnum;
import pl.kalisz.szpital.kancelaria.utils.OrganisationEnum;
import pl.kalisz.szpital.kancelaria.utils.Strings;

@SuppressWarnings("serial")
@UIScoped
public class TransactionGrid extends Grid {

  static final String BUTTONS = "opcje";
  private static final String NEW_ITEMS = "new-item";

  private TreeSet<Object> newItems = new TreeSet<>();

  @Inject
  UserSettings userSettings;

  @Inject
  TransactionWindow window;

  boolean production = false;

  /** The visible. */
  private static final Object[] VISIBLE = new Object[] { BUTTONS, Transaction.SYGNATURA_STR,
      Transaction.TYP_WIADOMOSCI, Transaction.TYP_PISMA_STR, Transaction.ODBIORCA_STR,
      Transaction.DATA_STR, Transaction.ADDRESS, Transaction.OPIS_STR, Transaction.USER };

  // data
  JPAContainer<Transaction> container;
  GridCellFilter gridCellFilter;

  // transaction form
  @Inject
  SaveClickListener saveClickLisener;
  @Inject
  CancelClickListener cancelClickLisener;
  @Inject
  KONewItemHandler newItemHandler;
  @Inject
  KOChangeListener changeListener;

  @Inject
  ContainerFactory dbHelper;

  public void addNewItem(Object id) {
    this.newItems.add(id);
  }


  public TransactionGrid() {
    setErrorHandler(new ErrorHandler() {
      @Override
      public void error(final com.vaadin.server.ErrorEvent event) {
        Notification.show("Błąd: " + event.getThrowable().getMessage());
      }
    });
  }

  public GridCellFilter getFilter() {
    return gridCellFilter;
  }

  @PostConstruct
  public void init() {

    gridCellFilter = new GridCellFilter(this);
    setProperties();
    setContainerDataSource(prepareContainers());
    prepareFilters();

    setRenderers();
    hideColumns();
    setColumnWidths();

    setColumnOrder(VISIBLE);
    sort(Transaction.DATA_STR, SortDirection.DESCENDING);

    // setRowDetails();

    prepareExtensions();

  }

  private void hideColumns() {
    getColumn(Transaction.USER).setHidable(true);
    getColumn(Transaction.USER).setHidden(true);
  }


  private void prepareExtensions() {
    SidebarMenuExtension extension = SidebarMenuExtension.extend(this);
    extension.addCommand(new SidebarMenuExtension.Command() {

      @Override
      public void trigger() {
        Notification.show("OK!");
      }

    }, "OK?");
    ContextClickExtension contextClickExtension = ContextClickExtension.extend(this);
    contextClickExtension.addContextClickListener(new ContextClickListener() {


      @Override
      public void contextClick(ContextClickEvent event) {
        editTransaction(event.getItemId());
      }
    });
  }

  private void setProperties() {
    setSizeFull();
    setImmediate(true);
    setFooterVisible(false);
    setColumnReorderingAllowed(true);
    setSelectionMode(SelectionMode.MULTI);
  }

  // private void setRowDetails() {
  // setDetailsGenerator(tdg);

  // addItemClickListener(e -> {
  // Object itemId = e.getItemId();
  // hideDetails();
  // setDetailsVisible(itemId, !isDetailsVisible(itemId));
  // tdg.detailsOpenIndex = itemId;
  // });
  // }



  private void setColumnWidths() {
    try {
      getColumn(Transaction.DATA_STR).setExpandRatio(12); // Width(120);
      getColumn(Transaction.DATA_STR).setMaximumWidth(135);
      getColumn(Transaction.SYGNATURA_STR).setExpandRatio(8); // Width(80);
      getColumn(Transaction.SYGNATURA_STR).setMaximumWidth(100);
      getColumn(Transaction.TYP_PISMA_STR).setExpandRatio(10); // Width(100);
      getColumn(Transaction.TYP_PISMA_STR).setMaximumWidth(120);
      getColumn(Transaction.TYP_WIADOMOSCI).setExpandRatio(4); // Width(40);
      getColumn(Transaction.TYP_WIADOMOSCI).setMaximumWidth(60);
      getColumn(Transaction.ODBIORCA_STR).setExpandRatio(15);
      getColumn(Transaction.ODBIORCA_STR).setMaximumWidth(120);
      getColumn(Transaction.ADDRESS).setExpandRatio(14); // MaximumWidth(140);
      getColumn(Transaction.ADDRESS).setMaximumWidth(240);
      getColumn(Transaction.OPIS_STR).setExpandRatio(200);
    } catch (Exception e) {
      System.out.println("miising column " + e.getMessage());
    }
  }



  private void setRenderers() {
    getColumn(Transaction.TYP_WIADOMOSCI).setRenderer(new HtmlRenderer(),
        new SimpleStringConverter<TypWiadomosci>(TypWiadomosci.class) {

          @Override
          public String convertToPresentation(final TypWiadomosci value,
              final Class<? extends String> targetType, final Locale locale)
                  throws com.vaadin.data.util.converter.Converter.ConversionException {
            if (value != null && value.getHtml() != null) {
              return String.format("%s", value.getHtml());
            } else {
              return "?";
            }
          }
        });
    getColumn(Transaction.DATA_STR).setRenderer(new HtmlRenderer(),
        new SimpleStringConverter<Date>(Date.class) {
          @Override
          public String convertToPresentation(final Date value,
              final Class<? extends String> targetType, final Locale locale)
                  throws com.vaadin.data.util.converter.Converter.ConversionException {
            return String.format(Strings.DATE_FORMAT, value);
          }
        });
    getColumn(BUTTONS)
        .setRenderer(new EditDeleteButtonValueRenderer(new EditDeleteButtonClickListener() {

          @Override
          public void onEdit(RendererClickEvent event) {
            editTransaction(event.getItemId());
          }

          @Override
          public void onDelete(RendererClickEvent event) {
            new PdfHandler(new Transaction(container.getItem(event.getItemId()))).showPDF();
          }
        }));
    setRowStyleGenerator(new RowStyleGenerator() {

      @Override
      public String getStyle(RowReference rowReference) {
        Object id = rowReference.getItemId();
        if (newItems.contains(id)) {
          return NEW_ITEMS;
        } else {
          return "";
        }
      }

    });
  }

  protected void editTransaction(Object itemId) {
    newItems.remove(itemId);
    window.setTransaction(container.getItem(itemId));
    window.addCloseListener(new CloseListener() {
      
      @Override
      public void windowClose(CloseEvent e) {
      }
    });
    UI.getCurrent().addWindow(window);
  }


  private void prepareFilters() {

    gridCellFilter.setTextFilter(Transaction.SYGNATURA_STR, true, false);
    gridCellFilter.setTextFilter(Transaction.ODBIORCA_STR, true, false);
    gridCellFilter.setTextFilter(Transaction.OPIS_STR, true, false);

    // DATE FILTER
    gridCellFilter.setCustomFilter(Transaction.DATA_STR, new CellFilterComponent<MenuBar>() {

      @Override
      public MenuBar layoutComponent() {
        Command c = new MenuBar.Command() {

          @Override
          public void menuSelected(MenuItem selectedItem) {
            DateRangeEnum date = DateRangeEnum.valueOf(selectedItem.getDescription());
            Filter f = DateRange.getFilter(date);
            System.out.println(" === " + f);
            selectedItem.getParent().setIcon(date.getIcon());
            selectedItem.getParent().setStyleName(ValoTheme.LABEL_TINY);
            selectedItem.getParent().setText(selectedItem.getText());
            // gridCellFilter.replaceFilter(f, Transaction.USER);
            gridCellFilter.replaceFilter(f, Transaction.DATA_STR);
          }
        };

        MenuBar menu = new MenuBar();
        MenuItem mb = menu.addItem("", FontAwesome.CALENDAR, null);
        for (DateRangeEnum dr : DateRangeEnum.values()) {
          MenuItem item = mb.addItem(dr.getName(), c);
          item.setIcon(dr.getIcon());
          item.setDescription(dr.name());
        }
        return menu;
      }

      @Override
      public void clearFilter() {
        gridCellFilter.removeFilter(Transaction.DATA_STR);
      }
    });

    // USER FILTER
    gridCellFilter.setCustomFilter(BUTTONS, new CellFilterComponent<MenuBar>() {

      @Override
      public MenuBar layoutComponent() {
        Command c = new MenuBar.Command() {

          @Override
          public void menuSelected(MenuItem selectedItem) {
            OrganisationEnum org = OrganisationEnum.valueOf(selectedItem.getDescription());
            System.out.println("=== " + org);
            Filter f = org.getFilter(userSettings.getUser());
            System.out.println(" === " + f);
            selectedItem.getParent().setIcon(org.getIcon());
            selectedItem.getParent().setText(selectedItem.getText());
            selectedItem.getParent().setStyleName(ValoTheme.LABEL_TINY);
            // gridCellFilter.replaceFilter(f, Transaction.USER);
            gridCellFilter.replaceFilter(f, Transaction.USER);
          }
        };

        MenuBar menu = new MenuBar();
        MenuItem mb = menu.addItem("", FontAwesome.USER, null);
        for (OrganisationEnum oe : OrganisationEnum.values()) {
          MenuItem item = mb.addItem(oe.getName(), c);
          item.setIcon(oe.getIcon());
          item.setDescription(oe.name());
        }
        return menu;
      }

      @Override
      public void clearFilter() {
        gridCellFilter.removeFilter(Transaction.USER);
      }

    });
    // TransactionGridDateFilter
    // TransactionGridUserFilter

    preparetypPismaFilter();
    prepareUserFilter();
    prepareAdresFilter();
    prepareTypWiadomosciFilter();
    addFilterChangeNotification();

  }

  private void addFilterChangeNotification() {
    gridCellFilter.addCellFilterChangedListener(new CellFilterChangedListener() {

      @Override
      public void changedFilter(final GridCellFilter cellFilter) {
        Notification.show("Wyszukiwanie po: " + cellFilter.filteredColumnIds(),
            Type.TRAY_NOTIFICATION);
        for (Object id : cellFilter.filteredColumnIds()) {
          Component cmp = cellFilter.getFilterRow().getCell(id).getComponent();
          System.out.println(" <= id" + id);
          if (cmp instanceof MenuBar) {
            System.out.println("mb " + ((MenuBar) cmp).getCaption());
            System.out.println(((MenuBar) cmp).getItems().get(0).getText());
          } else if (cmp instanceof ComboBox) {
            System.out.println("cb" + ((ComboBox) cmp).getValue());
          }
        }
      }
    });
  }



  private void prepareTypWiadomosciFilter() {
    TypWiadomosciFilterCommand typWiadomosciCommand =
        new TypWiadomosciFilterCommand(gridCellFilter);
    HashMap<String, Resource> typWiadomosciItems = typWiadomosciCommand.getItems();
    gridCellFilter.setCustomFilter(Transaction.TYP_WIADOMOSCI,
        new MenuBarFilterComponent(typWiadomosciCommand, typWiadomosciItems));
  }



  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void prepareAdresFilter() {
    JPAContainer<Address> c4 = dbHelper.getContainer(ContainerFactory.ADDRESS_CONTAINER);
    c4.removeAllContainerFilters();
    ArrayList<Address> l4 = new ArrayList<>();
    Iterator i4 = c4.getItemIds().iterator();
    while (i4.hasNext()) {
      l4.add((Address) c4.getItem(i4.next()).getEntity());
    }
    ComboBox combo4 = gridCellFilter.setComboBoxFilter(Transaction.ADDRESS, l4);
    combo4.setFilteringMode(FilteringMode.CONTAINS);
  }



  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void prepareUserFilter() {
    JPAContainer<User> c1 = dbHelper.getContainer(ContainerFactory.USER_CONTAINER);
    ArrayList<User> l1 = new ArrayList<>();
    Iterator i1 = c1.getItemIds().iterator();
    while (i1.hasNext()) {
      l1.add((User) c1.getItem(i1.next()).getEntity());
    }
    ComboBox combo2 = gridCellFilter.setComboBoxFilter(Transaction.USER, l1);
    if (!userSettings.isAdmin()) {
      combo2.setEnabled(false);
    }
  }



  @SuppressWarnings("rawtypes")
  private void preparetypPismaFilter() {
    Container c = dbHelper.getContainer(ContainerFactory.TYP_PISMA_CONTAINER);
    ArrayList<TypPisma> l = new ArrayList<>();
    Iterator i = c.getItemIds().iterator();
    while (i.hasNext()) {
      TypPisma next = new TypPisma(i.next().toString());
      l.add(next);
    }
    gridCellFilter.setComboBoxFilter(Transaction.TYP_PISMA_STR, l);
  }



  @SuppressWarnings("unchecked")
  public GeneratedPropertyContainer prepareContainers() {
    container = dbHelper.getContainer(ContainerFactory.TRANSACTION_CONTAINER);
    GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(container);
    gpc.addGeneratedProperty(BUTTONS, new PropertyValueGenerator() {

      @Override
      public Object getValue(Item item, Object itemId, Object propertyId) {
        return "&nbsp;";
      }

      @Override
      public Class getType() {
        return String.class;
      }
    });

    gpc.addGeneratedProperty(Transaction.SYGNATURA_STR, new PropertyValueGenerator() {

      @Override
      public Object getValue(Item item, Object itemId, Object propertyId) {
        Object id = item.getItemProperty(Transaction.ID);
        Integer i = Integer.parseInt(id.toString());
        i = (i > 99999 ? i % 99999 : i);
        return String.format("%05d", i);
      }

      @Override
      public Class getType() {
        return String.class;
      }
    });

    List<Object> available = Arrays.asList(VISIBLE);
    for (Object id : gpc.getContainerPropertyIds()) {
      if (!available.contains(id)) {
        gpc.removeContainerProperty(id);
      }
    }
    return gpc;
  }


  /**
   * Archivise.
   * 
   * @param transaction the transaction
   */
  private void archivise(final Transaction transaction) {
    String titleString = transaction.getId().toString();
    ConfirmDialog.show(UI.getCurrent(), titleString, Strings.CZY_PRZENIESC, Strings.TAK,
        Strings.NIE, new ConfirmDialog.Listener() {

          @SuppressWarnings("unchecked")
          @Override
          public void onClose(final ConfirmDialog dialog) {
            if (dialog.isConfirmed()) {
              Object itemId = transaction.getId();
              container.getItem(itemId).getItemProperty(Transaction.ARCHIVE_STR)
                  .setValue(Boolean.TRUE);
              container.commit();
              container.refresh();
            }
          }
        });
  }



}
