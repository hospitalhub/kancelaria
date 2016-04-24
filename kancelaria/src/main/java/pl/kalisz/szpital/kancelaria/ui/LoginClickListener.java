package pl.kalisz.szpital.kancelaria.ui;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.enums.Theme;
import pl.kalisz.szpital.kancelaria.data.pojo.User;

@SuppressWarnings("serial")
@UIScoped
public class LoginClickListener implements ClickListener {

  private static final Logger LOGGER = Logger.getLogger(LoginView.class);

  @Inject
  ContainerFactory dbHelper;
  @Inject
  private UserSettings settings;
  @Inject
  private LoginPanel loginPanel;
  
  private JPAContainer<User> userContainer;
  private String ip;
  
  @SuppressWarnings("unchecked")
  @PostConstruct
  public void init() {
    loginPanel.signin.addClickListener(this);
    userContainer = dbHelper.getContainer(ContainerFactory.USER_CONTAINER);
  }

  private void setIP() {
    try {
      WebBrowser b = UI.getCurrent().getPage().getWebBrowser();
      ip = b.getAddress();
    } catch (Exception e) {
      ip = "Arch Stanton";
    }
  }

  public static final String NAME = "login";

  @Override
  public void buttonClick(final ClickEvent event) {
    setIP();
    userContainer.addContainerFilter(new Compare.Equal(User.LOGIN, loginPanel.user));
    Iterator<Object> ids = (Iterator<Object>) userContainer.getItemIds().iterator();
    User user = null;
    if (ids.hasNext()) {
      Integer id = (Integer) ids.next();
      user = userContainer.getItem(id).getEntity();
    }

    if (user != null && loginPanel.user.getValue() != null
        && loginPanel.pass.getValue() != null
        && user.getPassword().equals(loginPanel.pass.getValue())) {
      loginOK(user);
    } else {
      loginError(loginPanel.user.getValue());
    }
  }

  private void setTheme(User user) {
    Theme t = user.getTheme();
    if (t != null) {
      try {
        UI.getCurrent().setTheme(t.getDir());
      } catch (Exception e) {
        LOGGER.error("Problem setting " + user + " theme.");
      }
    }
  }

  private void loginOK(User user) {
    settings.setUser(user);
    setTheme(user);
    LOGGER.info("IP: " + ip);
    UI.getCurrent().getNavigator().navigateTo(TransactionView.NAME);
  }

  private void loginError(String user) {
    Notification.show("Błędny użytkownik lub hasło", Notification.Type.WARNING_MESSAGE);
    loginPanel.user.focus();
    LOGGER.info(String.format("Nieudane logowanie na %s %s", user, ip));
  }
}
