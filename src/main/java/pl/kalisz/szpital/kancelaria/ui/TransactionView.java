package pl.kalisz.szpital.kancelaria.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.tepi.filtertable.datefilter.DateInterval;
import org.tepi.filtertable.paged.PagedFilterControlConfig;
import org.vaadin.dialogs.ConfirmDialog;

import pl.kalisz.szpital.kancelaria.DashboardUI;
import pl.kalisz.szpital.kancelaria.data.db.DbHelper;
import pl.kalisz.szpital.kancelaria.data.enums.Theme;
import pl.kalisz.szpital.kancelaria.data.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.data.filerepo.FilepathLocationStrategy;
import pl.kalisz.szpital.kancelaria.data.filerepo.SygnaturaFileLocation;
import pl.kalisz.szpital.kancelaria.data.filerepo.TempFileLocationStrategy;
import pl.kalisz.szpital.kancelaria.data.pojo.Role;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.User;
import pl.kalisz.szpital.kancelaria.raport.RaportEtykietyPdf;
import pl.kalisz.szpital.kancelaria.raport.RaportKorespondencjaPdf;
import pl.kalisz.szpital.kancelaria.raport.RaportPocztaPdf;
import pl.kalisz.szpital.kancelaria.ui.editor.TransactionEditorWindow;
import pl.kalisz.szpital.kancelaria.utils.DateUtils;
import pl.kalisz.szpital.kancelaria.utils.Strings;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

/**
 * The Class TransactionView.
 */
@SuppressWarnings("serial")
public class TransactionView extends VerticalLayout implements View {

  /** The Constant DAY_IN_MILLIS. */
  private static final int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

  /** The Constant PAGE_LENGTH. */
  private static final int PAGE_LENGTH = 30;

  /** The title. */
  private Label title = new Label();

  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger(TransactionView.class);

  /** The toolbar. */
  private final HorizontalLayout toolbar = new HorizontalLayout();

  /** The container. */
  private JPAContainer<Transaction> container;

  /** The table. */
  private MyPagedFilterTable<JPAContainer<Transaction>> table;

  /** The menu bar. */
  private final MenuBar menuBar = new MenuBar();

  /** The Constant _100. */
  private static final int _100 = 100;

  private static final String[] HEADERS = new String[] { Strings.MINIATURA, Strings.TYP_WIADOMOSCI,
      Strings.SYGNATURA, Strings.TYP_PISMA, Strings.KOMORKA_ORG, Strings.DATA,
      Strings.DANE_ADRESOWE, Strings.UZYTKOWNIK, Strings.NOTATKI };

  /** The file ctx. */
  private final FileLocationContext fileCtx = new FileLocationContext();

  /** The filter generator. */
  private final TransactionFilterGenerator filterGenerator = new TransactionFilterGenerator();

  /** The visible. */
  private static final Object[] VISIBLE = new Object[] { Transaction.PLIK_SCIEZKA_STR,
      Transaction.TYP_WIADOMOSCI, Transaction.ID, Transaction.TYP_PISMA_STR,
      Transaction.ODBIORCA_STR, Transaction.DATA_STR, Transaction.ADRES, Transaction.USER,
      Transaction.OPIS_STR };

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

  /**
   * Edits the transaction action.
   * 
   * @param transaction the transaction
   */
  private void editTransactionAction(final Transaction transaction) {
    // stare zdarzenia
    logger.debug("transaction editor");
    if (transaction.isImported()) {
      Notification.show(Strings.ZDARZENIE_PRZENIESIONE_Z_POPRZEDNIEGO_PROGRAMU,
          Strings.BRAK_MOZLIWOSCI_EDYCJI, Notification.Type.ERROR_MESSAGE);
    } else {
      final TransactionEditorWindow window = new TransactionEditorWindow(transaction, false);
      window.addCloseListener(new CloseListener() {

        @Override
        public void windowClose(final CloseEvent e) {
          if (window.isAdressesEdited()) {
            try {
              table.addItem();
            } catch (Exception e2) {
              logger.debug(Strings.DODAWANIE_FAKE);
            }
          }
        }
      });
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.navigator.View#enter
   */
  @SuppressWarnings("unchecked")
  @Override
  public final void enter(final ViewChangeEvent event) {
    if (container == null) {
      DashboardUI dashboardUI = (DashboardUI) getUI();
      DbHelper dbHelper = dashboardUI.getDbHelper();
      container = dbHelper.getContainer(DbHelper.TRANSACTION_CONTAINER);
    }
    setPreStyle();
    setSizeFull();
    createToolbar();
    createTitle();
    createNoweZdarzenieMenu();
    // TODO FIXME create theme menu
//    createThemeMenu();
    createClearMenu();
    createRaportyMenu();
    createUserMenu();
    createTransactionTable();
  }

  private void setPreStyle() {
    // break line stylees (adres \n)
    Styles styles = Page.getCurrent().getStyles();
    styles.add(".pre { white-space: pre !important; }");
  }

  /**
   * Gets the thumb.
   * 
   * @param source the source
   * @param itemId the item id
   * @return the thumb
   */
  private Component getThumb(final CustomTable source, final Object itemId) {
    Item i = source.getItem(itemId);
    Transaction t = new Transaction(i);
    fileCtx.setFileLocationStrategy(new FilepathLocationStrategy());
    File f = fileCtx.getFile(t);
    File thum = new File(f.getAbsolutePath() + ".png");
    if (f.exists() && !thum.exists()) {
      System.out.println("thumbnail (should be) generated here");
    }
    if (thum == null || !thum.exists()) {
      return new Label("-");
    }

    Image e = new Image(thum.getName(), new FileResource(thum));
    e.setWidth("64px");
    e.setStyleName("hover_resize");
    return e;
  }

  /**
   * Creates the transaction table.
   */
  private void createTransactionTable() {
    table = new MyPagedFilterTable<JPAContainer<Transaction>>() {
      @Override
      protected String formatPropertyValue(final Object rowId, final Object colId,
          final Property<?> property) {

        if (colId.equals(Transaction.DATA_STR)) {
          synchronized (this) {
            return DateUtils.DMY.format(property.getValue());
          }
        }
        return super.formatPropertyValue(rowId, colId, property);
      }

    };
    table.setErrorHandler(new ErrorHandler() {
      @Override
      public void error(final com.vaadin.server.ErrorEvent event) {
        Notification.show("Błąd: " + event.getThrowable().getMessage());
      }
    });

    table.setSizeFull();
    table.addStyleName(Strings.BORDERLESS);
    table.setSelectable(true);
    table.setMultiSelect(false);
    table.setColumnCollapsingAllowed(true);
    table.setColumnReorderingAllowed(true);
    try {
      table.setContainerDataSource(container);
    } catch (Exception e) {
      e.printStackTrace();
    }

    PagedFilterControlConfig config = new PagedFilterControlConfig();
    config.setPageLengthsAndCaptions(Arrays.asList(new Integer[] { PAGE_LENGTH }));
    config.setNext(Strings.NASTEPNA_STRONA);
    config.setLast(Strings.OSTATNIA);
    config.setFirst(Strings.PIERWSZA);
    config.setPrevious(Strings.POPRZEDNIA);
    config.setPage("");
    config.setItemsPerPage(Strings.TRANSAKCJI_NA_STRONE);
    HorizontalLayout controls = table.createControls(config);
    addComponent(controls);

    // sortTable(false);
    table.setFooterVisible(false);
    addComponent(table);
    setExpandRatio(table, 1);

    table.addActionHandler(createHandler());
    table.setImmediate(true);

    table.addGeneratedColumn(Transaction.PLIK_SCIEZKA_STR, new CustomTable.ColumnGenerator() {

      @Override
      public Object generateCell(final CustomTable source, final Object itemId,
          final Object columnId) {
        return ""; // getThumb(source, itemId);
      }
    });

    // ADRES: format
    table.addGeneratedColumn(Transaction.ADRES, new CustomTable.ColumnGenerator() {

      @Override
      public Object generateCell(final CustomTable source, final Object itemId,
          final Object columnId) {
        final Item item = source.getItem(itemId);
        final Property prop = item.getItemProperty(columnId);
        final String text = prop.getValue().toString();
        final Label label = new Label(text);
        label.addStyleName("pre");
        label.setDescription(text);
        return label;
      }
    });

    table.addGeneratedColumn(Transaction.ID, new CustomTable.ColumnGenerator() {

      @Override
      public Object generateCell(final CustomTable source, final Object itemId,
          final Object columnId) {
        final CheckBox box = new CheckBox();
        box.setValue(filterGenerator.isChecked((Integer) itemId));
        box.setCaption(itemId.toString());
        box.addValueChangeListener(new Property.ValueChangeListener() {
          @Override
          public void valueChange(final ValueChangeEvent event) {
            if (box.getValue().booleanValue()) {
              filterGenerator.addItemId(Integer.parseInt(box.getCaption()));
            } else {
              filterGenerator.removeItemId(Integer.parseInt(box.getCaption()));
            }
          }
        });
        return box;
      }

    });

    table.setFilterBarVisible(true);
    table.setFilterGenerator(filterGenerator);
    table.setFilterDecorator(new TransactionFilterDecorator());
    table.setVisibleColumns(VISIBLE);
    table.setColumnHeaders(HEADERS);
    table.setColumnCollapsed(Transaction.PLIK_SCIEZKA_STR, true);
    setToDefaultFilters();
    for (Object column : table.getVisibleColumns()) {
      table.setColumnExpandRatio(column, _100);
    }
    table.setColumnExpandRatio(Transaction.ADRES, _100 * 2);
  }

  /**
   * Creates the handler.
   * 
   * @return the handler
   */
  private Handler createHandler() {
    return new Handler() {

      private Action archive = new Action(Strings.PRZENIES_DO_ARCHIWUM);
      private Action showPdf = new Action(Strings.DOKUMENT);
      private Action edit = new Action(Strings.EDYCJA);

      @Override
      public Action[] getActions(final Object target, final Object sender) {
        return new Action[] { showPdf, edit, archive };
      }

      @Override
      public void handleAction(final Action action, final Object sender, final Object target) {
        if (target == null || container.getItem(target) == null) {
          return;
        }
        Transaction transaction = container.getItem(target).getEntity();
        if (action == edit) {
          editTransactionAction(transaction);
        } else if (action == showPdf) {
          fileCtx.setFileLocationStrategy(new FilepathLocationStrategy());
          if (!fileCtx.exists(transaction)) {
            Notification.show(Strings.BRAK_PLIKU, transaction.getPlikSciezka(),
                Type.WARNING_MESSAGE);
          } else {
            PdfDocumentWindow window =
                new PdfTransactionDocumentWindow(new FileResource(fileCtx.getFile(transaction)));
            UI.getCurrent().addWindow(window);
          }
        } else if (action == archive) {
          archivise(transaction);
        }
      }

    };
  }

  /**
   * Creates the title.
   */
  private void createTitle() {
    title.setSizeFull();
    title.setWidth(Strings.PERCENT100);
    toolbar.addComponent(title);
    toolbar.setExpandRatio(title, 6);
  }

  /**
   * Creates the nowe zdarzenie menu.
   */
  private void createNoweZdarzenieMenu() {
    // nowe zdarzenie
    toolbar.addComponent(new HorizontalLayout() {
      {
        setSizeUndefined();
        addStyleName(Strings.USER);

        Command cmd = new NoweZdarzenieCommand();
        MenuItem settingsMenu = menuBar.addItem(Strings.NOWE_ZDARZENIE, null);
        settingsMenu.setStyleName(Strings.ICON_SEARCH_1);
        settingsMenu.addItem(Strings.NOWE_ZDARZENIE, cmd);
        settingsMenu.addSeparator();
        settingsMenu.addItem(Strings.SKOPIUJ_POPRZEDNIE_ZDARZENIE, cmd);
        settingsMenu.addItem(Strings.SKOPIUJ_POPRZEDNIE_ZDARZENIE_I_SKAN, cmd);
        settingsMenu.addItem(Strings.SKOPIUJ_TYLKO_ADRES, cmd);
        settingsMenu.addItem(Strings.SKOPIUJ_TYLKO_TYP_PISMA, cmd);
      }
    });
  }

  /**
   * Creates the nowe zdarzenie menu.
   */
  private void createThemeMenu() {

    // nowe zdarzenie
    toolbar.addComponent(new HorizontalLayout() {
      {
        setSizeUndefined();
        addStyleName(Strings.USER);

        Command cmdFont = new Command() {

          @Override
          public void menuSelected(MenuItem selectedItem) {
            String fontFamily = selectedItem.getText();
            Styles styles = Page.getCurrent().getStyles();
            styles.add(".v-app { font:" + fontFamily + " !important; }");
          }
        };

        Command cmdInline = new Command() {

          @Override
          public void menuSelected(MenuItem selectedItem) {
            if (selectedItem.isChecked()) {
              Styles styles = Page.getCurrent().getStyles();
              styles.add(".pre { white-space: nowrap !important; }");
            } else {
              Styles styles = Page.getCurrent().getStyles();
              styles.add(".pre { white-space: pre !important; }");
            }
          }
        };

        Command cmdBigger = new Command() {

          @Override
          public void menuSelected(MenuItem selectedItem) {
            if (selectedItem.isChecked()) {
              Styles styles = Page.getCurrent().getStyles();
              styles
                  .add(".v-app .v-table-cell-wrapper, .pre, .v-checkbox { font-size: 130% !important; }");
            } else {
              Styles styles = Page.getCurrent().getStyles();
              styles
                  .add(".v-app .v-table-cell-wrapper, .pre, .v-checkbox { font-size: 100% !important; }");
            }
          }
        };

        Command cmdTheme = new Command() {

          @Override
          public void menuSelected(MenuItem selectedItem) {
            String themeName = (String) selectedItem.getText();
            String themeDir = Theme.getByName(themeName);
            UI.getCurrent().setTheme(themeDir);
          }
        };

        MenuItem settingsMenu = menuBar.addItem("Wygląd", null);
        MenuItem styleMenu = settingsMenu.addItem("Styl", null);
        for (Theme t : Theme.values()) {
          styleMenu.addItem(t.getName(), cmdTheme);
        }
        MenuItem fontMenu = settingsMenu.addItem("Czcionka", null);
        String[] fonts =
            { "12px arial, sans-serif", "italic bold 12px Courier, serif",
                "12px Georgia, sans-serif" };
        for (String font : fonts) {
          fontMenu.addItem(font, cmdFont);
        }
        // IN LINE
        MenuItem inlineMenu = settingsMenu.addItem("W linii", cmdInline);
        inlineMenu.setCheckable(true);
        // BIGGER
        MenuItem biggerMenu = settingsMenu.addItem("Powiększ", cmdBigger);
        biggerMenu.setCheckable(true);
      }
    });
  }



  /**
   * Check date filter and set.
   */
  private void checkDateFilterAndSet() {
    Object obj = table.getFilterFieldValue(Transaction.DATA_STR);
    Date from = DateUtils.getBegginning(new Date());
    Date to = new Date();
    if (obj != null && obj instanceof DateInterval) {
      System.out.println((DateInterval) obj);
      DateInterval dateInterval = (DateInterval) obj;
      if (dateInterval.getFrom() != null) {
        from = DateUtils.getBegginning(dateInterval.getFrom());
        System.out.println("FROM" + from);
      }
      if (dateInterval.getTo() != null) {
        to = DateUtils.getEnd(dateInterval.getTo());
        System.out.println("TO" + to);
      }
      System.out.println(to.getTime() - from.getTime());
      if (to.getTime() - from.getTime() > DAY_IN_MILLIS) {
        to = DateUtils.getEnd(from);
        Notification.show("Data końcowa raportu taka sama jak początkowa.");
      }
    } else {
      Notification.show("Ustawiono datę dzisiejszą dla raportu.");
    }
    table.setFilterFieldValue(Transaction.DATA_STR, new DateInterval(from, to));
  }

  /**
   * Builds the raport.
   */
  private void buildRaport() {
    checkDateFilterAndSet();
    ArrayList<Transaction> ts = new ArrayList<Transaction>();
    sortTable(true);
    Collection<Object> ids = container.getItemIds();
    for (Object id : ids) {
      ts.add(container.getItem(id).getEntity());
    }
    // TODO(AM) przekazywać pdf -> stream resource tworz wewnatrz
    // pdfdocumentwindow
    fileCtx.setFileLocationStrategy(new TempFileLocationStrategy());
    StreamResource resource =
        new StreamResource(new RaportKorespondencjaPdf(ts), fileCtx.getFile(null).getName());
    ((StreamResource) resource).setMIMEType(Strings.APPLICATION_PDF);
    PdfDocumentWindow documentWindow = new PdfDocumentWindow(resource);
    UI.getCurrent().addWindow(documentWindow);
    sortTable(false);
  }

  /**
   * Builds the raport polecone.
   */
  private void buildRaportPolecone() {
    table.setFilterFieldValue(Transaction.TYP_WIADOMOSCI, TypWiadomosci.WYCHODZĄCE_POLECONE);
    checkDateFilterAndSet();
    sortTable(true);
    ArrayList<Transaction> ts = new ArrayList<Transaction>();
    for (Object id : container.getItemIds()) {
      ts.add(container.getItem(id).getEntity());
    }
    fileCtx.setFileLocationStrategy(new TempFileLocationStrategy());
    StreamResource resource =
        new StreamResource(new RaportPocztaPdf(ts), fileCtx.getFile(null).getName());
    ((StreamResource) resource).setMIMEType(Strings.APPLICATION_PDF);
    PdfDocumentWindow documentWindow = new PdfDocumentWindow(resource);
    UI.getCurrent().addWindow(documentWindow);
    sortTable(false);
  }


  /**
   * Builds the raport polecone.
   */
  private void buildRaportEtykiety() {
    // TYLKO WYCHODZĄCE
    Filter f1 = new Equal(Transaction.PRZYCHODZACY_STR, false);
    container.addContainerFilter(f1);
    checkDateFilterAndSet();
    sortTable(true);
    ArrayList<Transaction> ts = new ArrayList<Transaction>();
    for (Object id : container.getItemIds()) {
      ts.add(container.getItem(id).getEntity());
    }
    container.removeContainerFilter(f1);
    fileCtx.setFileLocationStrategy(new TempFileLocationStrategy());
    StreamResource resource =
        new StreamResource(new RaportEtykietyPdf(ts), fileCtx.getFile(null).getName());
    ((StreamResource) resource).setMIMEType(Strings.APPLICATION_PDF);
    PdfDocumentWindow documentWindow = new PdfDocumentWindow(resource);
    UI.getCurrent().addWindow(documentWindow);
    sortTable(false);
  }

  /**
   * Creates the toolbar.
   */
  private void createToolbar() {
    toolbar.setWidth(Strings.PERCENT100);
    toolbar.setSpacing(true);
    toolbar.setMargin(true);
    toolbar.addStyleName(Strings.TOOLBAR2);
    addComponent(toolbar);
  }

  /**
   * Creates the command.
   * 
   * @return the command
   */
  private Command createCommand() {
    return new Command() {
      @Override
      public void menuSelected(final MenuItem selectedItem) {
        if (Strings.BIEZACE_ZDARZENIA.equals(selectedItem.getText())) {
          buildRaport();
        } else if (Strings.RAPORT_POCZTOWY_POLECONE.equals(selectedItem.getText())) {
          buildRaportPolecone();
        } else if (Strings.RAPORT_ETYKIETY_ADRESÓW.equals(selectedItem.getText())) {
          buildRaportEtykiety();
        }
      }
    };
  }

  /**
   * Creates the clear menu.
   */
  private void createClearMenu() {
    toolbar.addComponent(new HorizontalLayout() {
      {
        setSizeUndefined();
        Command cmd = new Command() {

          @Override
          public void menuSelected(final MenuItem selectedItem) {
            setToDefaultFilters();
          }
        };
        menuBar.addItem(Strings.WYCZYŚĆ_WYSZUKIWANIE, cmd);
        cmd = new Command() {

          @Override
          public void menuSelected(final MenuItem selectedItem) {
            updateContainer();
          }
        };
//         TODO FIXME ODŚWIERZANIE DANYCH
        menuBar.addItem("Odśwież dane", cmd);
      }
    });
  }

  /**
   * Sets the to default filters.
   */
  private void setToDefaultFilters() {
    table.resetFilters();
    filterGenerator.reset();
    User u = ((DashboardUI) UI.getCurrent()).getUser();
    if (!u.hasRole(Role.POWER_USER)) {
      table.setFilterFieldValue(Transaction.USER, u.getId());
    }
    sortTable(false);
  }

  private void updateContainer() {
    container.refresh();
  }

  /**
   * Creates the raporty menu.
   */
  private void createRaportyMenu() {
    // RAPORTY menu
    toolbar.addComponent(new HorizontalLayout() {
      {
        setSizeUndefined();
        Command cmd = createCommand();
        MenuItem settingsMenu = menuBar.addItem(Strings.RAPORTY_KORESPONDENCJI, null);
        settingsMenu.setStyleName(Strings.ICON_DOC);
        settingsMenu.addItem(Strings.BIEZACE_ZDARZENIA, cmd);
        settingsMenu.addSeparator();
        settingsMenu.addItem(Strings.RAPORT_POCZTOWY_POLECONE, cmd);
        settingsMenu.addSeparator();
        settingsMenu.addItem(Strings.RAPORT_ETYKIETY_ADRESÓW, cmd);
      }
    });
  }

  /**
   * Gets the all transactions.
   * 
   * @return the all transactions
   */
  @Deprecated
  private final ArrayList<EntityItem<Transaction>> getAllTransactions() {
    ArrayList<EntityItem<Transaction>> transactionsEntities =
        new ArrayList<EntityItem<Transaction>>();
    DashboardUI dashboardUI = ((DashboardUI) UI.getCurrent());
    DbHelper dbHelper = dashboardUI.getDbHelper();
    @SuppressWarnings("unchecked")
    JPAContainer<Transaction> transactionContainer =
        dbHelper.getContainer(DbHelper.TRANSACTION_CONTAINER);
    List<Filter> filters =
        new ArrayList<Container.Filter>(transactionContainer.getContainerFilters());
    System.err.println("filters:" + filters.size());
    transactionContainer.removeAllContainerFilters();
    transactionContainer.applyFilters();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    Vector<Integer> ids = new Vector(transactionContainer.getItemIds());
    for (Integer id : ids) {
      EntityItem<Transaction> ei = transactionContainer.getItem(id);
      transactionsEntities.add(ei);
    }
    for (Filter f : filters) {
      transactionContainer.addContainerFilter(f);
    }
    transactionContainer.applyFilters();
    return transactionsEntities;
  }

  /**
   * Pokaz ustawienia.
   */
  private void pokazUstawienia() {
    Window w = new Window();
    w.setResizable(false);
    UI.getCurrent().addWindow(w);
    VerticalLayout h = new VerticalLayout();
    if (((DashboardUI) UI.getCurrent()).getUser().hasRole(Role.ADMIN)) {
      h.addComponent(ocrujPliki());
    }
    w.setContent(h);
  }

  /**
   * Ocruj pliki.
   * 
   * @return the component
   */
  private Component ocrujPliki() {
    Button b = new Button("Ocruj wszystkie pliki");
    /*b.addClickListener(e -> {
      FileLocationContext context = new FileLocationContext();
      context.setFileLocationStrategy(new SygnaturaFileLocation());
      ArrayList<EntityItem<Transaction>> transactions = getAllTransactions();
      // int i = 0;
      for (final EntityItem<Transaction> ei : transactions) {
        // if (i++ > 4) {
        // break;
        // }
        logger.info(ei.getItemId());
        final Transaction t = ei.getEntity();
        if (t.getPlikSciezka() != null && !t.getPlikSciezka().equals(Strings.EMPTY_STRING)
            && new File(t.getPlikSciezka()).exists()
            && new File(t.getPlikSciezka()).length() > 8192) {
          logger.info(t.getSygnatura());
          String s = "";
          // ocrProcessor.extractTextFromPDF(new File(t.getPlikSciezka()));
          s = StringUtils.normalizeSpace(s);
          s = StringUtils.substring(s, 0, 200);
          ei.getItemProperty(Strings.OPIS).setValue(s);
        }
      }
    });*/
    return b;
  }

  /**
   * Creates the user menu.
   */
  private void createUserMenu() {
    // User menu
    toolbar.addComponent(new HorizontalLayout() {
      {
        setSizeUndefined();
        addStyleName(Strings.USER);

        Command cmd = new Command() {
          @Override
          public void menuSelected(final MenuItem selectedItem) {
            if (selectedItem.getText().equals(Strings.WYLOGUJ)) {
              ((DashboardUI) UI.getCurrent()).afterLogout();
            } else if (selectedItem.getText().equals(Strings.USTAWIENIA)) {
              pokazUstawienia();
            }
          }
        };
        User user = ((DashboardUI) UI.getCurrent()).getUser();
        MenuItem settingsMenu = menuBar.addItem(user.getLogin(), null);
        settingsMenu.setStyleName(Strings.ICON_COG);
        settingsMenu.addItem(Strings.USTAWIENIA, cmd);
        settingsMenu.addSeparator();
        settingsMenu.addItem(Strings.WYLOGUJ, cmd);
        addComponent(menuBar);
      }
    });
  }

  /**
   * Sort table.
   * 
   * @param ascending the ascending
   */
  private void sortTable(final boolean ascending) {
    Object[] sortOrder = new Object[] { Transaction.DATA_STR };
    boolean[] sortAsc = new boolean[] { ascending };
    table.sort(sortOrder, sortAsc);
  }

}
