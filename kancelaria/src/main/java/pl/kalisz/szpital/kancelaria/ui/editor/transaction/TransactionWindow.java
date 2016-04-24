package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Item;
import com.vaadin.ui.Window;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;

@SuppressWarnings("serial")
@UIScoped
public class TransactionWindow extends Window {

  @Inject
  TransactionForm tf;

  public void setTransaction(Item transaction) {
    tf.setTransaction(transaction);
    setTitle(transaction.getItemProperty(Transaction.ID).getValue());
  }

  private void setTitle(Object value) {
    if (value == null) {
      setCaption("Nowe zdarzenie");
    } else {
      setCaption("Zdarzenie " + value);
    }
  }

  @PostConstruct
  public void init() {
    this.setContent(tf);
  }

  public TransactionWindow() {
    // properties
    setResizable(false);
    setModal(true);
    setDraggable(false);
  }

}
