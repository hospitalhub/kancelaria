package pl.kalisz.szpital.kancelaria.ui;

import javax.inject.Inject;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.enums.Theme;
import pl.kalisz.szpital.kancelaria.data.pojo.User;

@SuppressWarnings("serial")
@UIScoped
final class ThemeCommand implements Command {
  @Inject
  TransactionGrid grid;

  @Inject
  UserSettings userSettings;

  @Inject
  ContainerFactory dbHelper;

  private void commitUserTheme(Theme theme) {
    JPAContainer<User> c = dbHelper.getContainer(ContainerFactory.USER_CONTAINER);
    c.getItem(userSettings.getUser().getId()).getItemProperty(User.THEME).setValue(theme);
    c.commit();
  }

  @Override
  public void menuSelected(MenuItem selectedItem) {
    String themeName = (String) selectedItem.getText();
    Theme theme = Theme.getByName(themeName);
    // Fix grid details styling bug
    UI.getCurrent().setTheme(theme.getDir());
    commitUserTheme(theme);

  }
}
