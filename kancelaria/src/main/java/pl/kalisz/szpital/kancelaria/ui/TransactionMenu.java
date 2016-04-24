package pl.kalisz.szpital.kancelaria.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.enums.Theme;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.User;
import pl.kalisz.szpital.kancelaria.utils.Strings;
import pl.kalisz.szpital.kancelaria.utils.TransactionGridFilterBuilder;

@SuppressWarnings("serial")
@UIScoped
public class TransactionMenu extends MenuBar {

  @Inject
  UserSettings userSettings;

  // commands
  @Inject
  FontCommand cmdFont;
  @Inject
  UserCommand cmdUser;
  @Inject
  ThemeCommand cmdTheme;
  @Inject
  NoweZdarzenieCommand cmdNew;
  @Inject
  FilterCommand cmdFilter;
  @Inject
  RefreshComand cmdRefresh;
  @Inject
  RaportCommand cmdReport;
  @Inject
  TransactionGridFilterBuilder filters;
  @Inject
  TransactionGridDateFilter dateFilter;
  @Inject
  TransactionGridUserFilter userFilter;


  @Inject
  TransactionGrid grid;
  @Inject
  ContainerFactory dbHelper;
  JPAContainer<Transaction> container;

  @SuppressWarnings("unchecked")
  @PostConstruct
  public void init() {
    this.container = dbHelper.getContainer(ContainerFactory.TRANSACTION_CONTAINER);
    prepareMenu();
    filters.applyFilters();
  }


  private void prepareMenu() {
    // nowe zdarzenie
    addItem(Strings.NOWE, FontAwesome.PLUS_SQUARE_O, cmdNew);

    // refresh
    addItem(Strings.ODŚWIEŻ, FontAwesome.CLOUD_DOWNLOAD, cmdRefresh);

    // RAPORTY menu
    MenuItem raportMenu = addItem(Strings.RAPORTY_KORESPONDENCJI, FontAwesome.BAR_CHART_O, null);
    raportMenu.setStyleName(Strings.ICON_DOC);
    raportMenu.addItem(Strings.BIEZACE_ZDARZENIA, FontAwesome.BARS, cmdReport);
    raportMenu.addSeparator();
    raportMenu.addItem(Strings.RAPORT_POCZTOWY_POLECONE, FontAwesome.ENVELOPE, cmdReport);
    raportMenu.addSeparator();
    raportMenu.addItem(Strings.RAPORT_ETYKIETY_ADRESÓW, FontAwesome.FONT, cmdReport);

    // user
    User user = userSettings.getUser();
    MenuItem userMenu = addItem(user.getLogin(), FontAwesome.USER, null);
    // userMenu.addItem(Strings.USTAWIENIA, FontAwesome.GEARS, cmdUser);
    // theme
    MenuItem styleMenu = userMenu.addItem(Strings.STYL, FontAwesome.EYE, null);
    for (Theme t : Theme.values()) {
      styleMenu.addItem(t.getName(), cmdTheme);
    }
    userMenu.addSeparator();
    userMenu.addItem(Strings.WYLOGUJ, FontAwesome.SIGN_OUT, cmdUser);
  }



}
