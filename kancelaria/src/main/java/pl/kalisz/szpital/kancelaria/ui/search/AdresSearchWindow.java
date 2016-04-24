package pl.kalisz.szpital.kancelaria.ui.search;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.xpath.operations.Equals;
import org.vaadin.gridutil.cell.GridCellFilter;
import org.vaadin.teemusa.gridextensions.contextclick.ContextClickEvent;
import org.vaadin.teemusa.gridextensions.contextclick.ContextClickExtension;
import org.vaadin.teemusa.gridextensions.contextclick.ContextClickListener;

import com.google.gwt.thirdparty.guava.common.collect.Multimap;
import com.google.gwt.thirdparty.guava.common.collect.TreeMultimap;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;

import pl.kalisz.szpital.kancelaria.DashboardUI;
import pl.kalisz.szpital.kancelaria.data.db.AddressMerger;
import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.pojo.Address;
import pl.kalisz.szpital.kancelaria.data.pojo.AdresBuilder;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.other.IntegerComparator;
import pl.kalisz.szpital.kancelaria.ui.TransactionGrid;
import pl.kalisz.szpital.kancelaria.ui.editor.AdresEditorWindow;
import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * The Class AdresSearchWindow.
 */
@SuppressWarnings("serial")
@UIScoped
public class AdresSearchWindow extends Window {

  private static final String[] VISIBLE =
      { Address.COMPANY, Address.LASTNAME, Address.NAME, Address.CITY, Address.POSTCODE };

  @Inject
  AddressMerger merger;

  @Inject
  ContainerFactory containerFactory;

  /** The contact list. */
  private Grid addressGrid = new Grid();

  /** The add new contact button. */
  private Button addNewContactButton = new Button("Nowy kontakt");

  /** The edit contact button. */
  private Button editContactButton = new Button("Edytuj kontakt");

  /** The clear button. */
  private Button clearButton = new Button("Wyczyść formularz");

  /** The merge contact button. */
  private Button mergeContactButton = new Button("Połącz identyczne");

  /** The content. */
  // private VerticalLayout content = new VerticalLayout();

  /** The adres. */
  private Address adres;

  /** The adres edited. */
  private boolean adresEdited = false;

  GridCellFilter gridCellFilter = new GridCellFilter(addressGrid);

  VerticalLayout content = new VerticalLayout();

  @Inject
  public TransactionGrid transactionGrid;

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
  public final Address getAdres() {
    return adres;
  }


  /** The adres container. */
  private JPAContainer<Address> adresContainer;

  /**
   * Inits the.
   */
  @PostConstruct
  protected final void init() {
    initContainer();
    initLayout();
    // initContactList();
    initAddRemoveButtons();
  }

  /**
   * Inits the container.
   */
  @SuppressWarnings("unchecked")
  private void initContainer() {
    if (adresContainer == null) {
      adresContainer = containerFactory.getContainer(ContainerFactory.ADDRESS_CONTAINER);
      // contactList.resetFilters();
      adresContainer.removeAllContainerFilters();
      GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(adresContainer);
      List<String> visible = Arrays.asList(VISIBLE);
      for (Object s : gpc.getContainerPropertyIds()) {
        if (!visible.contains(s)) {
          gpc.removeContainerProperty(s);
        }
      }
      gpc.addGeneratedProperty("Wybierz", new PropertyValueGenerator<String>() {
        @Override
        public Class<String> getType() {
          return String.class;
        }

        @Override
        public String getValue(Item item, Object itemId, Object propertyId) {
          return item.getItemProperty(Address.ID).toString();
        }
      });
      addressGrid.setContainerDataSource(gpc);
      gridCellFilter.setTextFilter(Address.COMPANY, true, false);
      gridCellFilter.setTextFilter(Address.LASTNAME, true, false);
      gridCellFilter.setTextFilter(Address.NAME, true, false);
      gridCellFilter.setTextFilter(Address.CITY, true, false);
      gridCellFilter.setTextFilter(Address.POSTCODE, true, false);

      ContextClickExtension clickExtension = ContextClickExtension.extend(addressGrid);
      clickExtension.addContextClickListener(new ContextClickListener() {

        @Override
        public void contextClick(ContextClickEvent event) {
          adres = new Address(event.getItem());
          close();
        }
      });
      addressGrid.setSizeFull();
      RendererClickListener listener = new RendererClickListener() {

        @Override
        public void click(RendererClickEvent event) {
          adres = adresContainer.getItem(event.getItemId()).getEntity();
          closeWindow();
        }
      };
      addressGrid.getColumn("Wybierz").setRenderer(new ButtonRenderer(listener));
      addressGrid.setColumnOrder(VISIBLE);
      addressGrid.setEditorSaveCaption("Zapisz");
      addressGrid.setEditorCancelCaption("Anuluj");
      addressGrid.setEditorEnabled(true);
      addressGrid.getEditorFieldGroup().addCommitHandler(new CommitHandler() {

        @Override
        public void preCommit(CommitEvent commitEvent) throws CommitException {}

        @Override
        public void postCommit(CommitEvent commitEvent) throws CommitException {
          adresEdited = true;
          /*Item i = commitEvent.getFieldBinder().getItemDataSource();
          Address original = new Address(i);
          Address a = new Address(i);
          a.setId(null);
          Object id = adresContainer.addEntity(a);
          System.out.println("NEW ADRES " + id);
          Item added = adresContainer.getItem(id);
          JPAContainer<Transaction> transactionContainer =
              containerFactory.getContainer(containerFactory.TRANSACTION_CONTAINER);
          Collection<Filter> fs = transactionContainer.getContainerFilters();
          transactionContainer.removeAllContainerFilters();
          transactionContainer.addContainerFilter(new Compare.Equal(Transaction.ADDRESS, original));
          for(Object tId : transactionContainer.getItemIds()) {
            System.out.println("UPDATE" + tId);
            transactionContainer.getItem(tId).getEntity().setAdres(new Address(added));
            transactionContainer.getItem(tId).commit();
          }
          transactionContainer.commit();
          transactionContainer.removeAllContainerFilters();
          for (Filter f : fs) {
            transactionContainer.addContainerFilter(f);
          }
          transactionContainer.refresh();
          adres = new Address(added);
          closeWindow();*/
        }
      });
    }
  }


  /**
   * Gets the widoczne adresy.
   * 
   * @return the widoczne adresy
   */
  private Multimap<Address, Integer> getWidoczneAdresy() {
    Comparator<Address> cmp = new Address();
    Comparator<Object> cmp2 = new IntegerComparator();
    Multimap<Address, Integer> multimap = TreeMultimap.create(cmp, cmp2);
    for (Object id : addressGrid.getContainerDataSource().getItemIds()) { // get
                                                                          // getVisibleItemIds()) {
      // Item item = contactList.getItem(id);
      Address adres = adresContainer.getItem(id).getEntity();
      // if (item == null) {
      // continue;
      // }
      // Adres adres = new Adres(item);
      multimap.put(adres, adres.getId());
    }
    return multimap;
  }

  public void closeWindow() {
    this.close();
  }


  /**
   * Inits the layout.
   */
  private void initLayout() {
    // setDraggable(false);
    // setResizable(false);
    // setModal(true);
    // setSizeFull();
    // setContent(content);
    VerticalLayout mainLayout = new VerticalLayout();

    /*
     * PagedFilterControlConfig config = new PagedFilterControlConfig();
     * config.setPageLengthsAndCaptions(Arrays.asList(new Integer[] { 30 })); config.setPage("");
     * config.setItemsPerPage(Strings.ADRESOW_NA_STRONIE); config.setNext(Strings.NASTEPNA_STRONA);
     * config.setFirst(Strings.PIERWSZA); config.setLast(Strings.OSTATNIA);
     * config.setPrevious(Strings.POPRZEDNIA);
     */
    // content.addComponent(contactList.createControls(config));
    setContent(content);
    content.addComponent(mainLayout);

    HorizontalLayout topLeftLayout = new HorizontalLayout();
    HorizontalLayout topLeft2Layout = new HorizontalLayout();
    mainLayout.addComponent(topLeftLayout);
    mainLayout.addComponent(topLeft2Layout);
    mainLayout.addComponent(addressGrid);
    addressGrid.setSizeFull();

    Label fill = new Label();
    topLeftLayout.addComponent(fill);

    topLeft2Layout.addComponent(clearButton);
    topLeft2Layout.addComponent(addNewContactButton);
    topLeft2Layout.addComponent(editContactButton);
    Label fill1 = new Label();
    topLeft2Layout.addComponent(fill1);

    mainLayout.setSizeFull();

    mainLayout.setExpandRatio(addressGrid, 1);
    // contactList.setHeight(100, Unit.PERCENTAGE);
    addressGrid.setSizeFull();

    topLeftLayout.setWidth(Strings.PERCENT100);
    topLeft2Layout.setWidth(Strings.PERCENT100);

    // setWidth(Strings.PERCENT100);
    // setHeight(Strings.PERCENT100);

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
        Multimap<Address, Integer> multimap = getWidoczneAdresy();
        int adresowPolaczonych = merger.polaczAdresy(multimap, adresContainer);
        if (adresowPolaczonych == 0) {
          Notification.show(Strings.BRAK_IDENTYCZNYCH_ADRESOW, Notification.Type.HUMANIZED_MESSAGE);
        }
      }
    });
    editContactButton.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(final ClickEvent event) {
        // Object contactId = contactList.getValue();
        adresEdited = true;
        // if (contactId != null) {
        // adresEditorWindowOpen(contactId);
      }
      // }

    });

    clearButton.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(final ClickEvent event) {
        // contactList.resetFilters();
        gridCellFilter.clearAllFilters();
      }
    });
    addNewContactButton.addClickListener(new ClickListener() {
      public void buttonClick(final ClickEvent event) {

        Address adres = new AdresBuilder().pusty().build();
        Object contactId = adresContainer.addEntity(adres);
        // for (Object id : contactList.getContainerPropertyIds()) {
        // if (Adres.ID.equals(id.toString())) {
        // continue;
        // }
        // }
        /* Lets choose the newly created contact to edit it. */
        addressGrid.select(contactId);

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
        new AdresEditorWindow(adresContainer.getItem(contactId));
    dashboardUI.addWindow(adresEditorWindow);
    /*
     * adresEditorWindow.addCloseListener(new CloseListener() {
     * 
     * @Override public void windowClose(final CloseEvent e) { if (adresEditorWindow.isCommit()) {
     * logger.debug("commit adres"); adresContainer.commit(); adres = new
     * Adres(adresEditorWindow.getAdresItem()); } else { logger.debug("rollback adres");
     * adresContainer.refresh(); } logger.debug("close search window"); close(); } });
     */
  }

  /**
   * Delete adres window open.
   * 
   * @param contactId the contact id
   */
  protected final void deleteAdresWindowOpen(final Object contactId) {
    // Item item = contactList.getItem(contactId);
    // String adres = new Adres(item).toString();
    /*
     * ConfirmDialog.show(UI.getCurrent(), Strings.USUNAC_TEN_ADRES, adres, Strings.USUN,
     * Strings.ANULUJ, new ConfirmDialog.Listener() {
     * 
     * @Override public void onClose(final ConfirmDialog dialog) { if (dialog.isConfirmed()) { try {
     * // contactList.removeItem(contactId); adresContainer.commit(); // updateFilters(false); }
     * catch (Exception e) { Notification.show(Strings.NIE_MOZNA_USUNAC, Strings.ZAREJESTROWANO,
     * Notification.Type.ERROR_MESSAGE); } } } });
     */
  }

  /**
   * Inits the contact list.
   */
  private void initContactList() {

    addressGrid.setContainerDataSource(adresContainer);
    // .addShortcutListener(new ShortcutListener(Strings.EMPTY_STRING, KeyCode.ENTER, null) {
    //
    // @Override
    // public void handleAction(final Object sender, final Object target) {
    // if (target instanceof PagedFilterTable && contactList.getValue() != null) {
    // Integer id = (Integer) contactList.getValue();
    // Item item = contactList.getItem(id);
    // // newTransaction(item);
    // adres = new Adres(item);
    // close();
    // }
    // }
    // });

    // contactList.addActionHandler(new Handler() {
    //
    // private Action delAdres = new Action("Usuń adres (DEL)");
    // private Action setAdres = new Action("Wybierz adres (ENTER)");
    //
    // @Override
    // public Action[] getActions(final Object target, final Object sender) {
    // return new Action[] { setAdres, delAdres };
    // }
    //
    // @Override
    // public void handleAction(final Action action, final Object sender, final Object target) {
    // @SuppressWarnings("unchecked")
    // Item item = ((PagedFilterTable<JPAContainer<Adres>>) sender).getItem(target);
    // if (action == setAdres) {
    // if (item != null) {
    // adres = new Adres(item);
    // close();
    // }
    // } else if (action == delAdres) {
    //// Object id = contactList.getValue();
    //// if (id != null) {
    //// deleteAdresWindowOpen(id);
    //// }
    // }
    // }
    //
    // });
    //// setColumns();
    // }


  }

}
