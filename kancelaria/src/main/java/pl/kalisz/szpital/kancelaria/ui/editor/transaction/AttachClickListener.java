package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
final class AttachClickListener extends TransactionFormButtonListener {

  public AttachClickListener(TransactionWindow window) {
    super(window);
  }

  @Override
  public void buttonClick(ClickEvent event) {
    window.close();
  }
}