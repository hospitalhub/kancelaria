package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;

import pl.kalisz.szpital.kancelaria.data.enums.KomorkaOrganizacyjna;

@SuppressWarnings("serial")
@UIScoped
public class KOChangeListener implements ValueChangeListener {

  @Inject
  TransactionForm form;

  @PostConstruct
  public void init() {
    form.komorkiOrganizacyjne.addValueChangeListener(this);
  }

  @Override
  public void valueChange(ValueChangeEvent event) {
    form.dekretacjeContainer.addBean((KomorkaOrganizacyjna) event.getProperty().getValue());
  }
}
