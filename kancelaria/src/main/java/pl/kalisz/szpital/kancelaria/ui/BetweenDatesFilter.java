package pl.kalisz.szpital.kancelaria.ui;

import javax.inject.Inject;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.util.filter.Between;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.DateUtils;
import pl.kalisz.szpital.kancelaria.utils.TransactionGridFilterBuilder;

@SuppressWarnings("serial")
@UIScoped
public class BetweenDatesFilter extends Window {

  @Inject
  ContainerFactory containerFactory;

  public BetweenDatesFilter() {
    VerticalLayout content = new VerticalLayout();
    DateField start = new DateField("Od", DateUtils.getFirstDayOfMonth());
    DateField end = new DateField("Do", DateUtils.getTonight());
    content.addComponent(start);
    content.addComponent(end);
    Button b = new Button("OK");
    b.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        try {
          JPAContainer<Transaction> c =
              containerFactory.getContainer(containerFactory.TRANSACTION_CONTAINER);
          c.removeContainerFilters(Transaction.DATA_STR);
          c.addContainerFilter(new Between(Transaction.DATA_STR, start.getValue(), end.getValue()));
          System.out.println(end.getValue());
          close();
        } catch (Exception e) {
          Notification.show("Wybierz obydwie daty");
        }
      }
    });
    content.addComponent(b);
    setClosable(false);
    setModal(true);
    setResizable(false);
    setContent(content);
    center();
  }

}
