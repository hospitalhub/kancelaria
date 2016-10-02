package pl.kalisz.szpital.kancelaria.ui.search;

import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tepi.filtertable.FilterGenerator;
import org.tepi.filtertable.paged.PagedFilterControlConfig;
import org.tepi.filtertable.paged.PagedFilterTable;
import org.vaadin.dialogs.ConfirmDialog;

import com.google.gwt.thirdparty.guava.common.collect.Multimap;
import com.google.gwt.thirdparty.guava.common.collect.TreeMultimap;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import pl.kalisz.szpital.kancelaria.DashboardUI;
import pl.kalisz.szpital.kancelaria.data.db.AddressMerger;
import pl.kalisz.szpital.kancelaria.data.db.DbHelper;
import pl.kalisz.szpital.kancelaria.data.pojo.Adres;
import pl.kalisz.szpital.kancelaria.data.pojo.AdresBuilder;
import pl.kalisz.szpital.kancelaria.other.IntegerComparator;
import pl.kalisz.szpital.kancelaria.ui.MyPagedFilterTable;
import pl.kalisz.szpital.kancelaria.ui.editor.AdresEditorWindow;
import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * The Class AdresSearchWindow.
 */
@SuppressWarnings("serial")
public class AdresSearchWindow extends Window {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(AdresSearchWindow.class);

  /** The contact list. */
  private MyPagedFilterTable<JPAContainer<Adres>> contactList =
      new MyPagedFilterTable<JPAContainer<Adres>>();

  /** The add new contact button. */
  private Button addNewContactButton = new Button("Nowy kontakt");

  /** The edit contact button. */
  private Button editContactButton = new Button("Edytuj kontakt");

  /** The clear button. */
  private Button clearButton = new Button("Wyczyść formularz");

  /** The merge contact button. */
  private Button mergeContactButton = new Button("Połącz identyczne");

  /** The content. */
  private VerticalLayout content = new VerticalLayout();

  /** The adres. */
  private Adres adres;

  /** The adres edited. */
  private boolean adresEdited = false;

  /**
   * Checks if is adres edited.
   * 
   * @return true, if is adres edited
   */
  public final boolean isAdresEdited() {
    return adresEdited;
  }

  /**
   * Gets the adres.
   * 
   * @return the adres
   */
  public final Adres getAdres() {
    return adres;
  }

  /**
   * Instantiates a new adres search window.
   */
  public AdresSearchWindow() {
    init();
  }

  /** The adres container. */
  private JPAContainer<Adres> adresContainer;

  /**
   * Inits the.
   */
  protected final void init() {
    initContainer();
    initLayout();
    initContactList();
    initAddRemoveButtons();
  }

  /**
   * Inits the container.
   */
  @SuppressWarnings("unchecked")
  private void initContainer() {
    if (adresContainer == null) {
      DashboardUI dashboardUI = (DashboardUI) UI.getCurrent();
      DbHelper dbHelper = dashboardUI.getDbHelper();
      adresContainer = dbHelper.getContainer(DbHelper.ADRES_CONTAINER);
      contactList.resetFilters();
      adresContainer.removeAllContainerFilters();
    }
  }

  /**
   * Gets the widoczne adresy.
   * 
   * @return the widoczne adresy
   */
  private Multimap<Adres, Integer> getWidoczneAdresy() {
    Comparator<Adres> cmp = new Adres();
    Comparator<Object> cmp2 = new IntegerComparator();
    Multimap<Adres, Integer> multimap = TreeMultimap.create(cmp, cmp2);
    for (Object id : contactList.getVisibleItemIds()) {
      Item item = contactList.getItem(id);
      if (item == null) {
        continue;
      }
      Adres adres = new Adres(item);
      multimap.put(adres, adres.getId());
    }
    return multimap;
  }

  /**
   * Polacz adresy.
   * 
   * @param multimap the multimap
   * @return the int
   */
  private int polaczAdresy(final Multimap<Adres, Integer> multimap) {
    int adresowPolaczonych = 0;
    for (Adres a : multimap.keySet()) {
      Integer[] ids = multimap.get(a).toArray(new Integer[] {});
      if (ids.length > 1) {
        adresowPolaczonych++;
        int id1 = ids[0];
        int num = 0;
        int numRemoved = 1;
        for (int id : ids) {
          if (id1 != id) {
            num += AddressMerger.mergeAdresy(id1, id);
            boolean ok = adresContainer.removeItem(id);
            if (ok) {
              numRemoved++;
            }
          }
        }
        try {
          adresContainer.commit();
        } catch (UnsupportedOperationException e) {
          e.printStackTrace();
        }
        adresContainer.refresh();
        Notification.show("Przeniesiono " + num + " dokumentów na jeden adres i połączono "
            + numRemoved + " adresow", a.toString(), Notification.Type.TRAY_NOTIFICATION);
      }
    }
    return adresowPolaczonych;
  }

  /**
   * Inits the layout.
   */
  private void initLayout() {
    setDraggable(false);
    setResizable(false);
    setModal(true);
    setSizeFull();
    setContent(content);
    VerticalLayout mainLayout = new VerticalLayout();

    PagedFilterControlConfig config = new PagedFilterControlConfig();
    config.setPageLengthsAndCaptions(Arrays.asList(new Integer[] { 30 }));
    config.setPage("");
    config.setItemsPerPage(Strings.ADRESOW_NA_STRONIE);
    config.setNext(Strings.NASTEPNA_STRONA);
    config.setFirst(Strings.PIERWSZA);
    config.setLast(Strings.OSTATNIA);
    config.setPrevious(Strings.POPRZEDNIA);
    content.addComponent(contactList.createControls(config));
    content.addComponent(mainLayout);

    HorizontalLayout topLeftLayout = new HorizontalLayout();
    HorizontalLayout topLeft2Layout = new HorizontalLayout();
    mainLayout.addComponent(topLeftLayout);
    mainLayout.addComponent(topLeft2Layout);
    mainLayout.addComponent(contactList);
    contactList.setSizeFull();

    Label fill = new Label();
    topLeftLayout.addComponent(fill);

    topLeft2Layout.addComponent(clearButton);
    topLeft2Layout.addComponent(addNewContactButton);
    topLeft2Layout.addComponent(editContactButton);
    Label fill1 = new Label();
    topLeft2Layout.addComponent(fill1);

    mainLayout.setSizeFull();

    mainLayout.setExpandRatio(contactList, 1);
    contactList.setHeight(100, Unit.PERCENTAGE);
    contactList.setSizeFull();

    topLeftLayout.setWidth(Strings.PERCENT100);
    topLeft2Layout.setWidth(Strings.PERCENT100);

    setWidth(Strings.PERCENT100);
    setHeight(Strings.PERCENT100);

    fill.setWidth(Strings.PERCENT100);
    fill.setSizeFull();
    topLeftLayout.setExpandRatio(fill, 5);
    fill1.setWidth(Strings.PERCENT100);
    fill1.setSizeFull();
    topLeft2Layout.setExpandRatio(fill1, 5);

    topLeft2Layout.addComponent(mergeContactButton);
    topLeft2Layout.setComponentAlignment(mergeContactButton, Alignment.MIDDLE_RIGHT);
  }

  /**
   * Inits the add remove buttons.
   */
  private void initAddRemoveButtons() {
    mergeContactButton.addClickListener(new ClickListener() {
      @Override
      public void buttonClick(final ClickEvent event) {
        Multimap<Adres, Integer> multimap = getWidoczneAdresy();
        System.out.println(multimap);
        int adresowPolaczonych = polaczAdresy(multimap);
        if (adresowPolaczonych == 0) {
          Notification.show(Strings.BRAK_IDENTYCZNYCH_ADRESOW, Notification.Type.HUMANIZED_MESSAGE);
        }
      }
    });
    editContactButton.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(final ClickEvent event) {
        Object contactId = contactList.getValue();
        adresEdited = true;
        if (contactId != null) {
          adresEditorWindowOpen(contactId);
        }
      }

    });

    clearButton.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(final ClickEvent event) {
        contactList.resetFilters();
        adresContainer.removeAllContainerFilters();
      }
    });
    addNewContactButton.addClickListener(new ClickListener() {
      public void buttonClick(final ClickEvent event) {

        Adres adres = new AdresBuilder().pusty().build();
        Object contactId = adresContainer.addEntity(adres);
        for (Object id : contactList.getContainerPropertyIds()) {
          if (Adres.ID.equals(id.toString())) {
            continue;
          }
        }
        /* Lets choose the newly created contact to edit it. */
        contactList.select(contactId);

        if (contactId != null) {
          adresEditorWindowOpen(contactId);
        }
      }
    });
  }

  /**
   * Adres editor window open.
   * 
   * @param contactId the contact id
   */
  protected final void adresEditorWindowOpen(final Object contactId) {
    final DashboardUI dashboardUI = (DashboardUI) UI.getCurrent();
    final AdresEditorWindow adresEditorWindow =
        new AdresEditorWindow(contactList.getItem(contactId));
    dashboardUI.addWindow(adresEditorWindow);
    adresEditorWindow.addCloseListener(new CloseListener() {

      @Override
      public void windowClose(final CloseEvent e) {
        if (adresEditorWindow.isCommit()) {
          logger.debug("commit adres");
          adresContainer.commit();
          adres = new Adres(adresEditorWindow.getAdresItem());
        } else {
          logger.debug("rollback adres");
          adresContainer.refresh();
        }
        logger.debug("close search window");
        close();
      }
    });
  }

  /**
   * Delete adres window open.
   * 
   * @param contactId the contact id
   */
  protected final void deleteAdresWindowOpen(final Object contactId) {
    Item item = contactList.getItem(contactId);
    String adres = new Adres(item).toString();
    ConfirmDialog.show(UI.getCurrent(), Strings.USUNAC_TEN_ADRES, adres, Strings.USUN,
        Strings.ANULUJ, new ConfirmDialog.Listener() {

          @Override
          public void onClose(final ConfirmDialog dialog) {
            if (dialog.isConfirmed()) {
              try {
                contactList.removeItem(contactId);
                adresContainer.commit();
                // updateFilters(false);
              } catch (Exception e) {
                Notification.show(Strings.NIE_MOZNA_USUNAC, Strings.ZAREJESTROWANO,
                    Notification.Type.ERROR_MESSAGE);
              }
            }
          }
        });
  }

  /**
   * Inits the contact list.
   */
  private void initContactList() {

    contactList.setContainerDataSource(adresContainer);
    contactList.setFilterGenerator(new FilterGenerator() {

      @Override
      public AbstractField<?> getCustomFilterComponent(final Object propertyId) {
        return null;
      }

      @Override
      public Filter generateFilter(final Object propertyId, final Field<?> originatingField) {
        return null;
      }

      @Override
      public Filter generateFilter(final Object propertyId, final Object value) {
        return null;
      }

      @Override
      public void filterRemoved(final Object propertyId) {
        //
      }

      @Override
      public Filter filterGeneratorFailed(final Exception reason, final Object propertyId,
          final Object value) {
        return null;
      }

      @Override
      public void filterAdded(final Object propertyId, final Class<? extends Filter> filterType,
          final Object value) {
        //
      }
    });

    contactList
        .addShortcutListener(new ShortcutListener(Strings.EMPTY_STRING, KeyCode.ENTER, null) {

          @Override
          public void handleAction(final Object sender, final Object target) {
            if (target instanceof PagedFilterTable && contactList.getValue() != null) {
              Integer id = (Integer) contactList.getValue();
              Item item = contactList.getItem(id);
              // newTransaction(item);
              adres = new Adres(item);
              close();
            }
          }
        });

    contactList.addActionHandler(new Handler() {

      private Action delAdres = new Action("Usuń adres (DEL)");
      private Action setAdres = new Action("Wybierz adres (ENTER)");

      @Override
      public Action[] getActions(final Object target, final Object sender) {
        return new Action[] { setAdres, delAdres };
      }

      @Override
      public void handleAction(final Action action, final Object sender, final Object target) {
        @SuppressWarnings("unchecked")
        Item item = ((PagedFilterTable<JPAContainer<Adres>>) sender).getItem(target);
        if (action == setAdres) {
          if (item != null) {
            adres = new Adres(item);
            close();
          }
        } else if (action == delAdres) {
          Object id = contactList.getValue();
          if (id != null) {
            deleteAdresWindowOpen(id);
          }
        }
      }

    });
    setColumns();
  }

  private void setColumns() {
    Object[] visible = new String[] { Adres.FIRMA, Adres.NAZWISKO, Adres.IMIE, Adres.ULICA, //
        Adres.MIASTO, Adres.KOD };
    contactList.setColumnWidth(Adres.FIRMA, 250);
    contactList.setColumnWidth(Adres.NAZWISKO, 100);
    contactList.setColumnWidth(Adres.IMIE, 50);
    contactList.setColumnWidth(Adres.ULICA, 150);
    contactList.setColumnWidth(Adres.MIASTO, 100);
    contactList.setColumnWidth(Adres.KOD, 50);
    contactList.setVisibleColumns(visible);
    contactList.setSelectable(true);
    contactList.setImmediate(true);
    contactList.setFilterBarVisible(true);
    contactList.sort(new Object[] { Adres.ILOSC_PISM }, new boolean[] { false });
  }

}
