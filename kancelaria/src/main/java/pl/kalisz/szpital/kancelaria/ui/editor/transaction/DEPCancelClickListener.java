package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@Deprecated
final class DEPCancelClickListener extends TransactionFormButtonListener {

  public DEPCancelClickListener(TransactionWindow window) {
    super(window);
  }

  @Override
  public void buttonClick(ClickEvent event) {
    window.close();
  }
}