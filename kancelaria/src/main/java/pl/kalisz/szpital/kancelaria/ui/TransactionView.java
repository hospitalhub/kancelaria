package pl.kalisz.szpital.kancelaria.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import pl.kalisz.szpital.kancelaria.utils.Broadcaster;
import pl.kalisz.szpital.kancelaria.utils.Strings;
import pl.kalisz.szpital.kancelaria.utils.TransactionMessage;
import pl.kalisz.szpital.kancelaria.utils.Broadcaster.BroadcastListener;

/**
 * The Class TransactionView.
 */
@SuppressWarnings("serial")
@CDIView("transaction")
public class TransactionView extends VerticalLayout implements View {

  public final static String NAME = "transaction";

  // ui
  @Inject
  TransactionGrid grid;
  @Inject
  TransactionMenu menuBar;
  @Inject
  UserSettings userSettings;

  @Override
  public final void enter(final ViewChangeEvent event) {}

  @PostConstruct
  public void init() {
    setSizeFull();
    addComponent(menuBar);
    setComponentAlignment(menuBar, Alignment.TOP_LEFT);
    addComponent(grid);
    setExpandRatio(grid, 100);
  }

}
