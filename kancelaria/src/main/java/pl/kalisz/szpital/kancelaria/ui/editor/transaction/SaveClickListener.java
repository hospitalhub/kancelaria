package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.ui.UserSettings;
import pl.kalisz.szpital.kancelaria.utils.Broadcaster;
import pl.kalisz.szpital.kancelaria.utils.TransactionMessage;

@SuppressWarnings("serial")
@UIScoped
public class SaveClickListener implements ClickListener {

  @Inject
  TransactionForm form;

  @Inject
  TransactionWindow window;

  @Inject
  UserSettings userSettings;

  @PostConstruct
  public void init() {
    form.save.addClickListener(this);
  }


  private void broadcast(String typ, Integer id, boolean newTransaction) {
    String user = userSettings.getUser().getLogin();
    Broadcaster.broadcast(new TransactionMessage(typ, id, newTransaction, user));
  }

  @SuppressWarnings("unchecked")
  private void saveNew(Item item) {
    Transaction transaction = new Transaction(item);
    transaction.setData(new Date());
    JPAContainer<Transaction> c = form.dbHelper.getContainer(ContainerFactory.TRANSACTION_CONTAINER);
    System.out.println("XXXXX" + transaction.getAdres());
    System.out.println("XXXXX" + transaction.getTypPisma());
    System.out.println("XXXXX" + transaction.getAdres().getId());
    Object id = c.addEntity(transaction);
    broadcast(transaction.getTypPisma().getNazwa(), new Integer(id.toString()), true);
  }

  private void save() {
    try {
      form.fieldGroup.commit();
      Item item = form.fieldGroup.getItemDataSource();
      Object id = item.getItemProperty(Transaction.ID).getValue();
      // new - add to container
      if (id == null) {
        saveNew(item);
      } else {
        broadcast("", new Integer(id.toString()), false);
      }
    } catch (CommitException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void buttonClick(ClickEvent event) {
    if (form.fieldGroup.isValid()) {
      save();
      window.close();
    } else {
      Notification.show("Nie wypełniono wszystkich pól!");
    }
  }
}
