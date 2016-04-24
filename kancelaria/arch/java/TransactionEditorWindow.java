package pl.kalisz.szpital.kancelaria.ui.editor;

import java.io.File;
import java.sql.Date;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.tokenfield.TokenField;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import pl.kalisz.szpital.kancelaria.DashboardUI;
import pl.kalisz.szpital.kancelaria.data.db.DbHelper;
import pl.kalisz.szpital.kancelaria.data.enums.KomorkaOrganizacyjna;
import pl.kalisz.szpital.kancelaria.data.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.data.filerepo.FilepathLocationStrategy;
import pl.kalisz.szpital.kancelaria.data.filerepo.NumerIdFileLocation;
import pl.kalisz.szpital.kancelaria.data.filerepo.PDFReceiver;
import pl.kalisz.szpital.kancelaria.data.filerepo.ScannedFileLocationStrategy;
import pl.kalisz.szpital.kancelaria.data.pojo.Adres;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.TypPisma;
import pl.kalisz.szpital.kancelaria.ui.PdfDocumentWindow;
import pl.kalisz.szpital.kancelaria.ui.search.AdresSearchWindow;
import pl.kalisz.szpital.kancelaria.utils.Strings;
import pl.kalisz.szpital.kancelaria.utils.pdf.PDFMerger;

/**
 * The Class TransactionEditorWindow.
 */
@SuppressWarnings("serial")
public class TransactionEditorWindow extends Window {

  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger(TransactionEditorWindow.class);

  private final TextArea adresTA = new TextArea();

  /** The bottom layout. */
  private final VerticalLayout bottomLayout = new VerticalLayout();

  /** The bottom buttons layout. */
  private final HorizontalLayout bottomButtonsLayout = new HorizontalLayout();

  /** The details layout. */
  private final HorizontalLayout detailsLayout = new HorizontalLayout();

  /** The tab layout. */
  private final HorizontalLayout tabLayout = new HorizontalLayout();

  /** The content layout. */
  private final VerticalLayout contentLayout = new VerticalLayout();

  /** The top layout. */
  private final HorizontalLayout topLayout = new HorizontalLayout();

  /** The notatki layout. */
  private final HorizontalLayout notatkiLayout = new HorizontalLayout();

  /** The notatki text area. */
  private final TextArea notatkiTextArea = new TextArea();

  /** The tabsheet. */
  private final TabSheet tabsheet = new TabSheet();

  /** The typ option group. */
  private final ListSelect typOptionGroup = new ListSelect();

  /** The typ pisma select. */
  private final ListSelect typPismaSelect = buildTypPisma();

  private final TokenField komorkaTokenField = new TokenField();

  /** The context. */
  private final FileLocationContext context = new FileLocationContext();

  /** The pdf receiver. */
  private PDFReceiver pdfReceiver;

  /** The pdf upload file. */
  private File pdfUploadFile;

  /** The transaction. */
  private Transaction transaction;

  /** The adresses edited. */
  private boolean adressesEdited = false;

  /** The container. */
  private JPAContainer<Transaction> container;

  /** The table. */
  private Table table;

  private Grid komorkaList = new Grid();

  private BeanItemContainer<KomorkaOrganizacyjna> komorkaListContainer =
      new BeanItemContainer<>(KomorkaOrganizacyjna.class);

  /**
   * Checks if is adresses edited.
   * 
   * @return true, if is adresses edited
   */
  public final boolean isAdressesEdited() {
    return adressesEdited;
  }

  /**
   * Instantiates a new transaction editor window.
   * 
   * @param t the t
   * @param isTemp the is temp
   */
  public TransactionEditorWindow(final Transaction t, final boolean isTemp) {
    transaction = t;
    initTransactionContainer();
    buildWindow();
    addTyp(t.getTypWiadomosci());
    addKomorkaLayout(t.getOdbiorca());
    addTypPisma(t.getTypPisma());
    addAdres(t.getAdres());
    addTresc(t.getOpis());
    addScan(isTemp);
    addSaveButton();
    addTabs();
    addKeyListeners();
    setSizesAndStyles();
    topLayout.setSpacing(true);
  }

  /**
   * Inits the transaction container.
   */
  @SuppressWarnings("unchecked")
  private void initTransactionContainer() {
    try {
      DashboardUI dashboardUI = (DashboardUI) UI.getCurrent();
      DbHelper dbHelper = dashboardUI.getDbHelper();
      int type = DbHelper.ADDRESS_BOUND_TRANSACTION_CONTAINER;
      container = (JPAContainer<Transaction>) dbHelper.getContainer(type);
      table = new Table();
      table.setContainerDataSource(container);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds the tabs.
   */
  private void addTabs() {
    tabLayout.addComponent(tabsheet);
    notatkiLayout.addComponent(notatkiTextArea);
    tabsheet.addTab(detailsLayout, Strings.KORESPONDENCJA);
    table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
    table.setFooterVisible(false);
    table.setVisibleColumns(new Object[] { Transaction.TYP_WIADOMOSCI, Transaction.ID,
        Transaction.TYP_PISMA_STR, Transaction.ODBIORCA_STR, Transaction.DATA_STR });
    table.setHeight(90, Unit.PIXELS);
    table.setWidth(100, Unit.PERCENTAGE);
    detailsLayout.addComponent(table);
    tabsheet.addTab(notatkiLayout, Strings.NOTATKI);
  }

  /**
   * Adds the adres.
   * 
   * @param a the a
   */
  private void addAdres(final Adres a) {
    VerticalLayout adresLayout = new VerticalLayout();
    adresLayout.setSizeFull();
    adresTA.setCaption(Strings.ADRES);
    setAdres(a);
    setLinkedTransactions(a);
    adresTA.setImmediate(true);
    adresTA.setReadOnly(true);
    adresTA.setSizeFull();
    adresTA.setHeight(100, Unit.PERCENTAGE);
    adresLayout.addComponent(adresTA);
    String adresButtonCaption = a == null ? Strings.WYBIERZ_ADRES : Strings.ZMIEN_ADRES;
    final Button adresyButton = new Button(adresButtonCaption);
    adresyButton.addClickListener(new ClickListener() {
      @Override
      public void buttonClick(final ClickEvent event) {
        showAdresSearchWindow();
      }
    });
    adresyButton.setStyleName(Strings.SMALL);
    adresLayout.addComponent(adresyButton);
    adresLayout.setExpandRatio(adresTA, 3);
    adresLayout.setExpandRatio(adresyButton, 1);
    adresLayout.setComponentAlignment(adresyButton, Alignment.MIDDLE_CENTER);
    topLayout.addComponent(adresLayout);
    topLayout.setExpandRatio(adresLayout, 1.2f);
  }

  /**
   * Builds the typ pisma.
   *
   * @param ui the ui
   * @return the list select
   */
  public ListSelect buildTypPisma() {
    DashboardUI dashboardUI = ((DashboardUI) UI.getCurrent());
    @SuppressWarnings("unchecked")
    JPAContainer<TypPisma> typPismaContainer =
        dashboardUI.getDbHelper().getContainer(DbHelper.TYP_PISMA_CONTAINER);
    final ListSelect typPismaSelect = new ListSelect(Strings.TYP_PISMA, typPismaContainer);
    typPismaSelect.setNewItemsAllowed(false);
    typPismaSelect.setNullSelectionAllowed(false);
    typPismaSelect.setItemCaptionPropertyId(TypPisma.NAZWA);
    typPismaSelect.setImmediate(true);
    return typPismaSelect;
  }

  /**
   * Sets the linked transactions.
   * 
   * @param adres the new linked transactions
   */
  private void setLinkedTransactions(final Adres adres) {
    container.removeAllContainerFilters();
    container.addContainerFilter(new Equal(Transaction.ADRES, adres));
    container.applyFilters();
    container.sort(new Object[] { Transaction.ID }, new boolean[] { false });
  }

  /**
   * Sets the adres.
   * 
   * @param adres the new adres
   */
  private void setAdres(final Adres adres) {
    if (adres != null) {
      adresTA.setReadOnly(false);
      adresTA.setValue(adres.toString());
      adresTA.setReadOnly(true);
      if (transaction != null) {
        transaction.setAdres(adres);
      }
    }
  }

  /**
   * Adds the key listeners.
   */
  private void addKeyListeners() {
    addShortcutListener(new ShortcutListener(Strings.EMPTY_STRING, KeyCode.ESCAPE, null) {

      @Override
      public void handleAction(final Object sender, final Object target) {
        close();
      }
    });
  }

  /**
   * Adds the komorka layout.
   * 
   * @param value the value
   */
  private void addKomorkaLayout(final String value) {
    final VerticalLayout komorkaLayout = new VerticalLayout();
    IndexedContainer container = new IndexedContainer();
    ComboBox komorkaTokenField = new ComboBox();
    for (KomorkaOrganizacyjna ko : KomorkaOrganizacyjna.values()) {
      container.addItem(ko);
    }
    if (value != null && !value.equals("")) {
      for (String s : value.split(" ")) {
        // komorkaTokenField.addToken(KomorkaOrganizacyjna.getByKod(s));
        komorkaListContainer.addItem(KomorkaOrganizacyjna.getByKod(s));
      }
    }

    komorkaTokenField.setCaption("Komórka organizacyjna");
    komorkaTokenField.setInputPrompt("Komórka org.");
    komorkaTokenField.setWidth(195, Unit.PIXELS);
    // komorkaTokenField.setTokenInsertPosition(InsertPosition.AFTER);
    komorkaTokenField.setContainerDataSource(container);
    komorkaTokenField.setFilteringMode(FilteringMode.CONTAINS);
    komorkaTokenField.setNewItemsAllowed(true);
    komorkaTokenField.setNewItemHandler(new NewItemHandler() {
      @Override
      public void addNewItem(String newItemCaption) {
        for (String s : ((String) newItemCaption).split(" ")) {
          try {
            KomorkaOrganizacyjna ko = KomorkaOrganizacyjna.getByKod(s);
            komorkaListContainer.addItem(ko);
            komorkaTokenField.setValue(null);
          } catch (Exception e2) {
          }
        }
      }
    });
    komorkaList.setHeaderVisible(false);
    GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(komorkaListContainer);
    
    komorkaList.setContainerDataSource(gpc);

    komorkaTokenField.addValidator(new Validator() {

      @Override
      public void validate(Object value) throws InvalidValueException {
        if (value != null) {
          if (value instanceof String) {
            for (String s : ((String) value).split(" ")) {
              try {
                KomorkaOrganizacyjna ko = KomorkaOrganizacyjna.getByKod(s);
                komorkaListContainer.addItem(ko);
              } catch (Exception e2) {
              }
            }
          } else if (value instanceof KomorkaOrganizacyjna) {
            komorkaListContainer.addItem(value);
          }
        }
      }
    });
    komorkaLayout.addComponent(komorkaTokenField);
    komorkaLayout.addComponent(komorkaList);
    komorkaTokenField.setSizeFull();
    komorkaList.setSizeFull();
    komorkaList.setHeight(110,Unit.PIXELS);
    topLayout.addComponent(komorkaLayout);
    topLayout.setExpandRatio(komorkaLayout, 2);
  }

  /**
   * Adds the save button.
   */
  private void addSaveButton() {
    final Button zapiszButton = new Button(Strings.ZAPISZ);
    zapiszButton.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(final ClickEvent event) {
        logger.debug("button click");
        if (typOptionGroup.getValue() == null) {
          Notification.show(Strings.NIE_WYBRANO_TYPU_ZDARZENIA, Strings.WYBIERZ_TYP,
              Notification.Type.WARNING_MESSAGE);
        } else if (!walidacjaKomorkaOrg()) {
          Notification.show(Strings.NIEPRAWIDLOWA_WARTOSC, Strings.POPRAW_KOMORKI_ORG,
              Notification.Type.WARNING_MESSAGE);
        } else if (transaction.getAdres() == null) {
          Notification.show(Strings.BRAK_ADRESU, Strings.WYBIERZ_ADRES,
              Notification.Type.WARNING_MESSAGE);
        } else {
          context.setFileLocationStrategy(new FilepathLocationStrategy());
          if (pdfUploadFile == null && !context.exists(transaction)) {
            System.out.println("BRAK??? " + transaction.getPlikSciezka());
            ConfirmDialog.show(UI.getCurrent(), Strings.BRAK_SKANU, Strings.CZY_ZAPISAC_BEZ_SKANU,
                "Tak", "Nie", new ConfirmDialog.Listener() {
              @Override
              public void onClose(final ConfirmDialog dialog) {
                if (dialog.isConfirmed()) {
                  zapisz(transaction);
                }
              }
            });
          } else {
            logger.debug("zapisz start");
            zapisz(transaction);
          }
        }
      }

    });
    Label fill = new Label();
    bottomButtonsLayout.addComponent(fill);
    bottomButtonsLayout.setExpandRatio(fill, 10);
    bottomButtonsLayout.addComponent(zapiszButton);
    bottomButtonsLayout.setSizeFull();
    bottomLayout.addComponent(bottomButtonsLayout);
  }

  /**
   * Adds the scan.
   * 
   * @param temp the temp
   */
  private void addScan(final boolean temp) {
    final Upload upload = new Upload();
    if (temp) {
      pdfReceiver = new PDFReceiver();
    } else {
      pdfReceiver = new PDFReceiver(transaction);
    }
    upload.setReceiver(pdfReceiver);
    upload.setStyleName(Strings.STYL_SEKRETARIAT);
    upload.setButtonCaption(Strings.ZALACZ_PLIK);
    upload.addSucceededListener(new SucceededListener() {

      @Override
      public void uploadSucceeded(final SucceededEvent event) {
        upload.setVisible(false);
        pdfUploadFile = pdfReceiver.getFile();
        Notification.show(Strings.DOKUMENT_PRZESLANY, pdfUploadFile.getAbsolutePath(),
            Notification.Type.HUMANIZED_MESSAGE);
      }
    });
    upload.setImmediate(true);
    Button podgladSkanu = new Button(Strings.POBIERZ_SKAN);
    podgladSkanu.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(final ClickEvent event) {
        String name = ((DashboardUI) UI.getCurrent()).getUser().getLogin();
        try {
          int i = PDFMerger.merger(name);
          if (i > 1) {
            Notification.show(Strings.POLACZONO_SKANOW + i);
          }
        } catch (Exception e) {
          Notification.show(Strings.SKANER, Strings.BRAK_SKANU, Notification.Type.ERROR_MESSAGE);
          return;
        }
        context.setFileLocationStrategy(new ScannedFileLocationStrategy());
        pdfUploadFile = context.getFile(name);
        if (pdfUploadFile.exists()) {
          PdfDocumentWindow documentWindow = new PdfDocumentWindow(new FileResource(pdfUploadFile));
          UI.getCurrent().addWindow(documentWindow);
        } else {
          Notification.show(Strings.BRAK_DOKUMENTU);
        }
      }
    });
    bottomButtonsLayout.addComponent(podgladSkanu);
    bottomButtonsLayout.addComponent(upload);
  }

  /**
   * Adds the tresc.
   * 
   * @param value the value
   */
  private void addTresc(final String value) {
    notatkiTextArea.setValue(value);
  }

  /**
   * Adds the typ.
   * 
   * @param typWiadomosci the typ wiadomosci
   */
  private void addTyp(final TypWiadomosci typWiadomosci) {
    typOptionGroup.setCaption(Strings.TYP_WIADOMOSCI);
    typOptionGroup.setNewItemsAllowed(false);
    typOptionGroup.setNullSelectionAllowed(false);
    typOptionGroup.setNewItemHandler(new NewItemHandler() {

      @Override
      public void addNewItem(final String newItemCaption) {
        typOptionGroup.addItem(newItemCaption);
      }
    });

    for (TypWiadomosci t : TypWiadomosci.values()) {
      // Archiwalny ma przychodzacy na null - nie jest wyswietlany
      if (t.isPrzychodzacy() != null) {
        typOptionGroup.addItem(t.getNazwa());
      }
    }
    typOptionGroup.setImmediate(true);
    if (typWiadomosci != null) {
      typOptionGroup.setValue(typWiadomosci.getNazwa());
    }
    topLayout.addComponent(typOptionGroup);
  }

  /**
   * Adds the typ pisma.
   * 
   * @param value the value
   */
  private void addTypPisma(final TypPisma value) {
    typPismaSelect.setValue(value.getNazwa());
    topLayout.addComponent(typPismaSelect);
  }

  /**
   * Builds the window.
   */
  private void buildWindow() {
    setCaption("Zdarzenie");
    setResizable(false);
    setDraggable(false);
    setModal(true);
    center();
    contentLayout.addComponent(topLayout);
    topLayout.setSpacing(true);
    contentLayout.addComponent(bottomLayout);
    bottomLayout.setSpacing(true);
    bottomLayout.addComponent(tabLayout);
    tabLayout.setSpacing(true);
    setContent(contentLayout);
    UI.getCurrent().addWindow(this);
    this.focus();
  }

  /**
   * Gets the komorka.
   * 
   * @return the komorka
   */
  private String getKomorka() {
    @SuppressWarnings("unchecked")
    LinkedHashSet<KomorkaOrganizacyjna> set = new LinkedHashSet<>();
    for (Object id : komorkaListContainer.getItemIds()) {
      set.add(komorkaListContainer.getItem(id).getBean());
    }
    if (set == null || set.size() == 0) {
      Notification.show(Strings.BRAK_KODU, Strings.WYBIERZ_KOMORKE_ORGANIZACYJNA,
          Notification.Type.WARNING_MESSAGE);
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (KomorkaOrganizacyjna ko : set) {
      sb.append(ko.getKod());
      sb.append(" ");
    }
    System.out.println(sb);
    return sb.toString();
  }

  /**
   * Gets the opis.
   * 
   * @return the opis
   */
  private String getOpis() {
    return notatkiTextArea.getValue().toString();
  }

  /**
   * Gets the typ.
   * 
   * @return the typ
   */
  private String getTyp() {
    return typOptionGroup.getValue().toString();
  }

  /**
   * Gets the typ pisma.
   * 
   * @return the typ pisma
   */
  private TypPisma getTypPisma() {
    String tps = typPismaSelect.getValue().toString();
    TypPisma tp = new TypPisma(tps);
    return tp;
  }

  /**
   * Sets the last saved transaction.
   * 
   * @param transaction the new last saved transaction
   */
  private void setLastSavedTransaction(final Transaction transaction) {
    ((DashboardUI) UI.getCurrent()).setLastTransaction(transaction);
  }

  /**
   * Sets the sizes and styles.
   */
  private void setSizesAndStyles() {
    topLayout.setSizeFull();
    topLayout.setHeight(180, Unit.PIXELS);
    typPismaSelect.setHeight(120, Unit.PIXELS);
    typPismaSelect.setSizeFull();
    topLayout.setExpandRatio(typOptionGroup, 2.1f);
    topLayout.setExpandRatio(typPismaSelect, 1.7f);
    topLayout.setSpacing(true);
    tabLayout.setWidth(650, Unit.PIXELS);
    notatkiLayout.setSizeFull();
    notatkiTextArea.setSizeFull();
    komorkaTokenField.focus();
  }

  /**
   * Show adres search window.
   */
  private void showAdresSearchWindow() {
    final DashboardUI dashboardUI = (DashboardUI) UI.getCurrent();

    final AdresSearchWindow adresSearchWindow = new AdresSearchWindow();
    adresSearchWindow.addCloseListener(new CloseListener() {
      @Override
      public void windowClose(final CloseEvent e) {
        Adres adres = adresSearchWindow.getAdres();
        adressesEdited = adresSearchWindow.isAdresEdited();
        if (adres != null) {
          setAdres(adres);
          setLinkedTransactions(adres);
        }
      }
    });
    dashboardUI.addWindow(adresSearchWindow);
  }

  /**
   * Show err.
   */
  private void showErr() {
    Notification.show(Strings.COS_POSZLO_NIE_TAK, Strings.PROSZE_ZGLOSIC_BLAD,
        Notification.Type.ERROR_MESSAGE);
  }

  /**
   * Walidacja komorka org.
   * 
   * @return true, if successful
   */
  private boolean walidacjaKomorkaOrg() {

    String komorkaStr = getKomorka().replaceAll("[\t ,]+", " ");
    if (komorkaStr.equals("")) {
      return false;
    }
    return true;
  }

  // TODO(AM) testy do zrobienia - 1 utworzenie zdarzenia 2 skopiowanie zdarzenia z wszystkim 3
  // edycja
  // TODO(AM) 4 przeniesienie do kosza 5 przeniesienie do innego typu wiadomosci (zewn -> wewn)
  // TODO(AM) rzeczy do sprawdzenia: utworzenie, sygnatura, zmieniona tresc

  /**
   * Zapisz.
   * 
   * @param transaction the transaction
   */
  private void zapisz(final Transaction transaction) {
    TypWiadomosci typWiadomosci = TypWiadomosci.getByTytul(getTyp());
    boolean isNewTransaction = transaction.getId() == null;
    if (isNewTransaction) {
      Date data = new Date(System.currentTimeMillis());
      transaction.setData(data);
      transaction.setTypWiadomosci(typWiadomosci);
    }
    transaction.setTypPisma(getTypPisma());
    transaction.setOdbiorca(getKomorka());
    transaction.setOpis(getOpis());
    transaction.setTypWiadomosci(typWiadomosci);
    transaction.setSygnatura(null);
    DashboardUI dashboardUI = ((DashboardUI) UI.getCurrent());
    @SuppressWarnings("unchecked")
    JPAContainer<Transaction> container =
        dashboardUI.getDbHelper().getContainer(DbHelper.TRANSACTION_CONTAINER);
    try {
      Integer id = (Integer) container.addEntity(transaction);
      transaction.setId(id);
      // BUG: ksiegowosc i typ pisma
      String name = ((DashboardUI) UI.getCurrent()).getUser().getLogin();
      if (name.equals("ksiegowosc") && transaction.getTypPisma().getNazwa().equals("PISMO")) {
        TypPisma tpFaktura = new TypPisma("FAKTURA");
        container.getContainerProperty(transaction.getId(), "typPisma").setValue(tpFaktura);
      }
      // BUG1END
      zapiszPlik(transaction);
      container.commit();
      // Notification.show("Numer dokumentu: " + transaction.getId().toString(),
      // Notification.Type.ERROR_MESSAGE);
      TransactionNumberNotificationWindow sub =
          new TransactionNumberNotificationWindow(transaction.getId());
      UI.getCurrent().addWindow(sub);
      close();
      logger.debug("sygnatura");
      setLastSavedTransaction(transaction);
      logger.debug("zapisywanie transakcji");
      // Broadcaster
      // .broadcast(name + " dodaje " + transaction.getTypPisma() + " nr " + transaction.getId());
    } catch (Exception e) {
      showErr();
    }
  }

  /**
   * Gets the transaction.
   * 
   * @return the transaction
   */
  public final Transaction getTransaction() {
    return transaction;
  }

  /**
   * Zapisz plik.
   * 
   * @param t the t
   * @return the string
   */
  private String zapiszPlik(final Transaction t) {
    logger.debug("zapisz plik");
    // dodano plik
    if (pdfUploadFile != null && pdfUploadFile.exists()) {
      System.out.println("File: " + pdfUploadFile);
      try {
        context.setFileLocationStrategy(new NumerIdFileLocation());
        final File newFile = context.getFile(t.getId());
        System.out.println(newFile.getAbsolutePath());
        logger.debug("before thread");
        new Thread() {
          public void run() {
            logger.debug("before move");
            pdfUploadFile.renameTo(newFile);
            logger.debug("after move");
          };
        }.start();
        logger.debug("zwraca nowy plik");
        return newFile.getAbsolutePath();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }
    if (t.getPlikSciezka() != null && !t.getPlikSciezka().equals("")) {
      // skopiowano zdarzenie wraz z plikiem
      return t.getPlikSciezka();
    }
    return null;
  }

}
