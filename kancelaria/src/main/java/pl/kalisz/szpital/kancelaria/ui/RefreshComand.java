package pl.kalisz.szpital.kancelaria.ui;

import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;

@SuppressWarnings("serial")
@UIScoped
final class RefreshComand implements Command {

  @Inject
  ContainerFactory containerFactory;
  
  @Override
  public void menuSelected(final MenuItem selectedItem) {
    containerFactory.getContainer(ContainerFactory.TRANSACTION_CONTAINER).refresh();
  }
}
