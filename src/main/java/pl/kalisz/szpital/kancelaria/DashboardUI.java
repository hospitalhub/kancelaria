package pl.kalisz.szpital.kancelaria;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.appfoundation.authorization.Permissions;
import org.vaadin.appfoundation.authorization.jpa.JPAPermissionManager;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.WebBrowser;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import pl.kalisz.szpital.kancelaria.data.db.DbHelper;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.User;
import pl.kalisz.szpital.kancelaria.other.MyConverterFactory;
import pl.kalisz.szpital.kancelaria.ui.TransactionView;
import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * The Class DashboardUI.
 */
@SuppressWarnings("serial")
@Theme("reindeer")
@Title("WSZ Sekretariat")
public class DashboardUI extends UI {

  private static final int FULL = 100;

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(DashboardUI.class);

  /** The user. */
  private User user = null;

  /** The content. */
  private CssLayout content = new CssLayout();

  /** The db helper. */
  private DbHelper dbHelper = new DbHelper();

  /** The last transaction. */
  private Transaction lastTransaction;

  /** The login layout. */
  private AbsoluteLayout loginLayout;

  /** The menu. */
  private CssLayout menu = new CssLayout();

  /** The nav. */
  private Navigator nav;

  /** The root. */
  private CssLayout root = new CssLayout();

  /** The ip. */
  private String ip;

  /** The routes. */
  private HashMap<String, Class<? extends View>> routes =
      new HashMap<String, Class<? extends View>>() {
        {
          put("/zdarzenia", TransactionView.class);
          // put("/adresy", AdresView.class);
          // put("/raporty", RaportView.class);
          // put("/terminarz", ScheduleView.class);
        }
      };

  /** The view name to menu button. */
  private HashMap<String, Button> viewNameToMenuButton = new HashMap<String, Button>();

  /**
   * Gets the user.
   * 
   * @return the user
   */
  public final User getUser() {
    return user;
  }

  /**
   * After logout.
   */
  public final void afterLogout() {
    root.removeAllComponents();
    ClassResource x = new ClassResource(this.getClass(), "images/koniec.jpg");
    System.out.println(x.getClass());
    Image img = new Image("", x);
    img.setSizeFull();
    root.addComponent(img);
    UI.getCurrent().getPage().setTitle("bye");
  }

  /**
   * Sets the user.
   * 
   * @param user the new user
   */
  private final void setUser(final User user) {
    this.user = user;
  }

  private final String getIP() {
    return this.ip;
  }

  /**
   * Builds the login view.
   */
  private void buildLoginView() {
    addStyleName("login");

    loginLayout = new AbsoluteLayout();
    loginLayout.setSizeFull();
    loginLayout.addStyleName("login-layout");
    ClassResource x = new ClassResource(this.getClass(), "images/szpital.jpg");
    System.out.println(x.getClass());
    Image img = new Image(Strings.EMPTY_STRING, x);
    img.setSizeFull();
    loginLayout.addComponent(img);
    root.addComponent(loginLayout);

    final CssLayout loginPanel = new CssLayout();
    loginPanel.addStyleName("login-panel");

    HorizontalLayout labels = new HorizontalLayout();
    labels.setWidth("100%");
    labels.setMargin(true);
    labels.addStyleName("labels");
    loginPanel.addComponent(labels);

    HorizontalLayout fields = new HorizontalLayout();
    fields.setSpacing(true);
    fields.setMargin(true);
    fields.addStyleName("fields");

    final TextField username = new TextField("Użytkownik");
    username.setId("user");
    username.focus();
    username.setStyleName("user_pass");
    fields.addComponent(username);

    final PasswordField password = new PasswordField("Hasło");
    password.setId("pass");
    password.setStyleName("user_pass");
    fields.addComponent(password);

    final Button signin = new Button("Wejdź");
    signin.addStyleName("default");
    signin.setId("submitb");
    fields.addComponent(signin);
    fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

    final ShortcutListener enter = new ShortcutListener("Sign In", KeyCode.ENTER, null) {

      private static final long serialVersionUID = 2293051646396517631L;

      @Override
      public void handleAction(final Object sender, final Object target) {
        signin.click();
      }
    };

    signin.addClickListener(new ClickListener() {
      @Override
      public void buttonClick(final ClickEvent event) {
        @SuppressWarnings("unchecked")
        JPAContainer<User> uc = dbHelper.getContainer(DbHelper.USER_CONTAINER);
        uc.addContainerFilter(new Compare.Equal(User.LOGIN, username));
        Iterator<Object> ids = (Iterator<Object>) uc.getItemIds().iterator();
        User user = null;
        if (ids.hasNext()) {
          Integer id = (Integer) ids.next();
          user = uc.getItem(id).getEntity();
        }

        if (user != null && username.getValue() != null && password.getValue() != null
            && user.getPassword().equals(password.getValue())) {
          signin.removeShortcutListener(enter);
          setUser(user);
          uc.removeAllContainerFilters();
          LOGGER.info(user + ":" + getIP());
          buildMainView();
        } else {
          if (loginPanel.getComponentCount() > 2) {
            // Remove the previous error message
            loginPanel.removeComponent(loginPanel.getComponent(2));
          }
          // Add new error message
          Label error = new Label(Strings.ERR_USER_PASS, ContentMode.HTML);
          error.addStyleName("error");
          error.setSizeUndefined();
          error.addStyleName("light");
          error.setWidth(FULL, Unit.PERCENTAGE);
          // Add animation
          error.addStyleName("v-animate-reveal");
          loginPanel.addComponent(error);
          username.focus();
          LOGGER.info(String.format("Nieudane logowanie na %s %s", user, getIP()));
        }
      }
    });

    signin.addShortcutListener(enter);

    loginPanel.addComponent(fields);

    loginLayout.addComponent(loginPanel, "left: 42%; top: 56%;");
    // loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
  }

  /**
   * Builds the main view.
   */
  private void buildMainView() {

    nav = new Navigator(this, content);

    for (String route : routes.keySet()) {
      nav.addView(route, routes.get(route));
    }

    removeStyleName("login");
    root.removeComponent(loginLayout);

    root.addComponent(new HorizontalLayout() {
      {
        setSizeFull();
        addStyleName("main-view");

        // Content
        addComponent(content);
        content.setSizeFull();
        content.addStyleName("view-content");
        setExpandRatio(content, 1);
      }

    });

    menu.removeAllComponents();

    for (final String view : new String[] { "zdarzenia", "adresy",
        /* "raporty" ,"sales", "terminarz" */ }) {
      String s = view.substring(0, 1).toUpperCase() + view.substring(1).replace('-', ' ');
      Button b = new NativeButton(s);
      b.addStyleName("icon-" + view);
      b.addClickListener(new ClickListener() {
        private static final long serialVersionUID = 5101681528000551581L;

        @Override
        public void buttonClick(final ClickEvent event) {
          // clearMenuSelection();
          event.getButton().addStyleName("selected");
          if (!nav.getState().equals("/" + view)) {
            nav.navigateTo("/" + view);
          }
        }
      });

      menu.addComponent(b);

      viewNameToMenuButton.put("/" + view, b);
    }
    menu.addStyleName("menu");
    menu.setHeight("100%");
    menu.setVisible(false);

    viewNameToMenuButton.get("/zdarzenia").setHtmlContentAllowed(true);
    nav.navigateTo("/zdarzenia");
    menu.getComponent(1).addStyleName("selected");

    nav.addViewChangeListener(new ViewChangeListener() {

      @Override
      public void afterViewChange(final ViewChangeEvent event) {}

      @Override
      public boolean beforeViewChange(final ViewChangeEvent event) {
        return true;
      }
    });
  }

  /**
   * Gets the db helper.
   * 
   * @return the db helper
   */
  public final DbHelper getDbHelper() {
    return dbHelper;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
   */
  @Override
  protected final void init(final VaadinRequest request) {
    Permissions.initialize(this, new JPAPermissionManager());
    getSession().setConverterFactory(new MyConverterFactory());
    VaadinService.getCurrent().setSystemMessagesProvider(new MySystemMessagesProvider());

    WebBrowser b = (WebBrowser) getUI().getPage().getWebBrowser();
    this.ip = b.getAddress();
    LOGGER.info("IP: " + this.ip);

    setLocale(new Locale("pl", "PL"));

    setContent(root);
    root.setSizeFull();

    Label bg = new Label();
    bg.setSizeUndefined();
    root.addComponent(bg);

    buildLoginView();
  }

  /**
   * Sets the address count.
   * 
   * @param count the new address count
   */
  @SuppressWarnings("unused")
  private void setAddressCount(final int count) {
    viewNameToMenuButton.get("/adresy")
        .setCaption("Adresy<span class=\"badge\">" + count + "</span>");
  }

  /**
   * Gets the last transaction.
   * 
   * @return the last transaction
   */
  public final Transaction getLastTransaction() {
    return lastTransaction;
  }

  /**
   * Sets the last transaction.
   * 
   * @param lastTransaction the new last transaction
   */
  public final void setLastTransaction(final Transaction lastTransaction) {
    this.lastTransaction = lastTransaction;
  }

}
