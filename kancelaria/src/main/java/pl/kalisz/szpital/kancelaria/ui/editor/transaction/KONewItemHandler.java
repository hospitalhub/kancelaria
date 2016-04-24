package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbstractSelect.NewItemHandler;

@SuppressWarnings("serial")
@UIScoped
public class KONewItemHandler implements NewItemHandler {

  @Inject
  TransactionForm form;
  private final static KOConverter converter = new KOConverter();

  @PostConstruct
  public void init() {
    form.komorkiOrganizacyjne.setNewItemHandler(this);
  }

  @Override
  public void addNewItem(String newItemCaption) {
    KOSet koset = converter.convertToPresentation(newItemCaption, KOSet.class, Locale.getDefault());
    form.dekretacjeContainer.addAll(koset.getSet());
    form.komorkiOrganizacyjne.valueChange(new ValueChangeEvent() {

      @Override
      public Property getProperty() {
        form.komorkiOrganizacyjne.setValue(koset.getSet());
        return form.komorkiOrganizacyjne;
      }
    });
  }

}
