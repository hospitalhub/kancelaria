package pl.kalisz.szpital.kancelaria.ui;

import javax.annotation.PostConstruct;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@SuppressWarnings("serial")
@UIScoped
@DesignRoot
public class LoginPanel extends VerticalLayout {

  TextField user;
  PasswordField pass;
  Button signin;

  @PostConstruct
  public void init() {
    Design.read(this);
  }


}
