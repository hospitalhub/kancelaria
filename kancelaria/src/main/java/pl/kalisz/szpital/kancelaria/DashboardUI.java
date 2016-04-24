package pl.kalisz.szpital.kancelaria;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.server.Constants;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import pl.kalisz.szpital.kancelaria.other.MyConverterFactory;
import pl.kalisz.szpital.kancelaria.ui.LoginView;
import pl.kalisz.szpital.kancelaria.ui.NavigationEvent;
import pl.kalisz.szpital.kancelaria.ui.TransactionGrid;
import pl.kalisz.szpital.kancelaria.ui.UserSettings;
import pl.kalisz.szpital.kancelaria.ui.search.AdresSearchWindow;
import pl.kalisz.szpital.kancelaria.utils.Broadcaster;
import pl.kalisz.szpital.kancelaria.utils.Strings;
import pl.kalisz.szpital.kancelaria.utils.Broadcaster.BroadcastListener;
import pl.kalisz.szpital.kancelaria.utils.TransactionMessage;

@SuppressWarnings("serial")
@Theme("tests-valo-reindeer")
@Title("Sekretariat")
@CDIUI("")
@Push
@Default
// @JavaScript({"https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js", "batman.js"})
public class DashboardUI extends UI implements BroadcastListener // vaadin-push
{

  @WebServlet(value = "/*", asyncSupported = true,
      initParams = { //
          @WebInitParam(name = VaadinSession.UI_PARAMETER, value = DashboardUI.UI),
          @WebInitParam(name = Constants.SERVLET_PARAMETER_UI_PROVIDER,
              value = DashboardUI.PROVIDER),
          @WebInitParam(name = TIMEOUT, value = "60"),
          @WebInitParam(name = Constants.PARAMETER_WIDGETSET, value = DashboardUI.WIDGETSET),
          @WebInitParam(name = Constants.SERVLET_PARAMETER_PRODUCTION_MODE, value = "true") })
  public static class Servlet extends VaadinCDIServlet {
  }

  public static final String PROVIDER = "com.vaadin.cdi.CDIUIProvider";
  public static final String UI = "pl.kalisz.szpital.kancelaria.DashboardUI";
  public static final String WIDGETSET = "pl.kalisz.szpital.widgetset.HospitalWidgetSet";
  public static final String TIMEOUT = "session-timeout";

  @Inject
  private UserSettings userSettings;

  @Inject
  private TransactionGrid grid;

  @Inject
  private javax.enterprise.event.Event<NavigationEvent> navigationEvent;
  
  @Inject
  AdresSearchWindow adresSearchWindow;
  
  public AdresSearchWindow getAdresSearchWindow() {
    return adresSearchWindow;
  }

  /**
   * After logout.
   */
  public final void afterLogout() {}

  @PostConstruct
  public void init() {}

  @Override
  protected final void init(final VaadinRequest request) {

    getSession().setConverterFactory(new MyConverterFactory());
    VaadinService.getCurrent().setSystemMessagesProvider(new MySystemMessagesProvider());
    setLocale(new Locale("pl"));
    addStyles();

    navigationEvent.fire(new NavigationEvent(LoginView.NAME));
    // VAADIN-PUSH
    Broadcaster.register(this);

  }


  @Override
  public void detach() {
    Broadcaster.unregister(this);
    super.detach();
  }

  /**
   * VAADIN-PUSH
   */
  @Override
  public void receiveBroadcast(TransactionMessage message) {
    access(new Runnable() {
      @Override
      public void run() {
        if (userSettings != null & grid != null) {
          String currentLogin = userSettings.getUser().getLogin();
          String msgLogin = message.getUserLogin();
          if (!currentLogin.equals(msgLogin)) {
            Notification.show(message.toString(), Type.TRAY_NOTIFICATION);
            grid.addNewItem(message.getId());
          }
        }
      }
    });
  }

  private void addStyles() {
    // break line stylees (adres \n)
    Styles styles = Page.getCurrent().getStyles();
    styles.add(".pre { white-space: pre !important; }");
    // hide select-all checkbox
    styles.add(Strings.CHECKBOX_ALL_STYLE);
    styles.add(".new-item { font-weight: 900 !important; font-style: italic;}");
  }


}
