package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
@UIScoped
public class CancelClickListener implements ClickListener {

  @Inject
  TransactionForm form;
  
  @Inject
  TransactionWindow window;
  
  @PostConstruct
  public void init() {
    form.cancel.addClickListener(this);
  }

  @Override
  public void buttonClick(ClickEvent event) {
    form.fieldGroup.discard();
    window.close();
  }
}
