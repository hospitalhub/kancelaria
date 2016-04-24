package pl.kalisz.szpital.kancelaria.ui.views;

import org.apache.log4j.Logger;
import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.authentication.exceptions.AccountLockedException;
import org.vaadin.appfoundation.authentication.exceptions.InvalidCredentialsException;
import org.vaadin.appfoundation.authentication.util.AuthenticationUtil;

import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.utils.Strings;

import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ClassResource;
import com.vaadin.server.WebBrowser;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class LoginView extends LoginForm implements View {

	private static final Logger LOGGER = Logger.getLogger(LoginView.class);

	private Label error = new Label(Strings.ERR_USER_PASS, ContentMode.HTML);

	@Override
	public void enter(ViewChangeEvent event) {
		setSizeFull();
	}

	@Override
	protected Component createContent(TextField userNameField,
			PasswordField passwordField, Button loginButton) {
		AbsoluteLayout loginLayout = new AbsoluteLayout();
		loginLayout.addStyleName(Strings.LOGIN_LAYOUT_STYLE_NAME);
		loginLayout.setSizeFull();

		ClassResource x = new ClassResource(KancelariaUI.class,
				Strings.IMAGES_SZPITAL_JPG);
		Image img = new Image(Strings.EMPTY_STRING, x);
		img.setSizeFull();
		loginLayout.addComponent(img);

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);

		layout.addComponent(userNameField);
		layout.addComponent(passwordField);
		layout.addComponent(loginButton);
		layout.addComponent(createErrorField());
		layout.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);
		layout.setWidth(Strings._100_PERCENT);

		final CssLayout loginPanel = new CssLayout();
		loginPanel.addComponent(layout);
		loginPanel.addStyleName(Strings.LOGIN_PANEL_STYLE_NAME);

		loginLayout.addComponent(loginPanel, "left: 42%; top: 56%;");

		return loginLayout;
	}

	private Component createErrorField() {
		error.setSizeUndefined();
		error.setVisible(false);
		error.setWidth(Strings.FULL_100, Unit.PERCENTAGE);
		return error;
	}

	@Override
	protected Button createLoginButton() {
		final Button signin = new Button(Strings.SUBMIT_MSG);
		signin.addStyleName(Strings.DEFAULT_STYLE_NAME);
		signin.setId(Strings.SUBMITBUTTON_FIELD_ID);
		signin.addClickListener(event -> {
			Notification.show("siema");
		});
		return signin;
	}

	@Override
	protected PasswordField createPasswordField() {
		final PasswordField password = new PasswordField(Strings.PASSWORD_MSG);
		password.setId(Strings.PASSWORD_FIELD_ID);
		password.setStyleName(Strings.USER_PASS);
		return password;
	}

	@Override
	protected TextField createUserNameField() {
		final TextField username = new TextField(Strings.USER_MSG);
		username.setId(Strings.USER_ID);
		username.focus();
		username.setStyleName(Strings.USER_PASS);
		return username;
	}

	@Override
	protected void login(String userName, String password) {

		WebBrowser b = (WebBrowser) getUI().getPage().getWebBrowser();
		LOGGER.info(String.format(Strings.ADDRESS_IP_LOG_MSG, b.getAddress()));

		try {
			User u = AuthenticationUtil.authenticate(userName, password);
			LOGGER.info(String.format(Strings.ZALOGOWANO_LOG_MSG, userName,
					b.getAddress()));
			UI.getCurrent().getNavigator().navigateTo("zdarzenia");
		} catch (InvalidCredentialsException e) {
			error.setVisible(true);
			LOGGER.info(String.format(Strings.NIEUDANE_LOGOWANIE_LOG_MSG,
					userName, b.getAddress()));
		} catch (AccountLockedException e) {
			e.printStackTrace();
		}

	}

}