package pl.kalisz.szpital.kancelaria.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.themes.ValoTheme;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.utils.Strings;
import pl.kalisz.szpital.widgetset.WidgetResources;

@SuppressWarnings("serial")
@CDIView("login")
public class LoginView extends AbsoluteLayout implements View {

  public final static String NAME = "login";

  @Inject
  ContainerFactory dbHelper;

  @Inject
  LoginPanel loginPanel;

  @Inject
  UserSettings userSettings;

  @Inject
  LoginClickListener loginClickListener;

  private static final String LOGIN_PANEL_POSITION = "left: 42%; top: 56%;";

  private Image img = new Image(Strings.EMPTY_STRING, WidgetResources.getImage("szpital.jpg"));

  @PostConstruct
  public void init() {
    setSizeFull();
    img.setSizeFull();
    addComponent(img);
    addComponent(loginPanel, LOGIN_PANEL_POSITION);
  }

  @Override
  public void enter(ViewChangeEvent event) {
    loginPanel.user.focus();
    loginPanel.addStyleName(ValoTheme.PANEL_WELL);
    loginPanel.addStyleName(ValoTheme.TABSHEET_FRAMED);
    loginPanel.addStyleName(ValoTheme.TEXTFIELD_LARGE);
  }

}
