package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
abstract class TransactionFormButtonListener implements Button.ClickListener {

  protected final TransactionWindow window;
  
  public TransactionFormButtonListener(TransactionWindow window) {
    this.window = window;
  }
  @Override
  public void buttonClick(ClickEvent event) {}
}