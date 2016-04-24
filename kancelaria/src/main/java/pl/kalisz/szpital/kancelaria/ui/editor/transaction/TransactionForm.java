package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.declarative.Design;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.enums.KomorkaOrganizacyjna;
import pl.kalisz.szpital.kancelaria.data.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.data.pojo.Address;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.TypPisma;

@SuppressWarnings("serial")
@UIScoped
@DesignRoot
public class TransactionForm extends Panel {

  // fields
  Button scan;
  Button save;
  Button cancel;
  ComboBox komorkiOrganizacyjne;
  ListSelect typWiadomosci;
  ListSelect typPisma;

  AddressField<Address> adres;
  TextArea dekretacje;
  TextArea notatki;
  Upload attach;

  @Inject
  ContainerFactory dbHelper;

  // containers
  JPAContainer<TypPisma> typPismaContainer;
  BeanItemContainer<TypWiadomosci> twContainer;
  BeanItemContainer<KomorkaOrganizacyjna> koContainer;
  BeanItemContainer<KomorkaOrganizacyjna> dekretacjeContainer;
  FieldGroup fieldGroup;

  public void setTransaction(Item transaction) {
    this.fieldGroup = new FieldGroup(transaction);
    bindData();
  }

  @PostConstruct
  public void init() {
    prepareDesign();
    prepareContainers();
    addValidation();
  }

  private void prepareDesign() {
    Design.read(this);
    System.out.println("DESIGN!");
  }

  private void addValidation() {
    typWiadomosci.setNullSelectionAllowed(false);
  }

  private void bindData() {
    fieldGroup.bind(typWiadomosci, Transaction.TYP_WIADOMOSCI);
    fieldGroup.bind(typPisma, Transaction.TYP_PISMA_STR);
    fieldGroup.bind(notatki, Transaction.OPIS_STR);
    fieldGroup.bind(adres, Transaction.ADDRESS);
    fieldGroup.bind(dekretacje, Transaction.ODBIORCA_STR);
  }

  @SuppressWarnings("unchecked")
  private void prepareContainers() {

    // set containers
    typPismaContainer = dbHelper.getContainer(ContainerFactory.TYP_PISMA_CONTAINER);
    koContainer = new BeanItemContainer<>(KomorkaOrganizacyjna.class);
    twContainer = new BeanItemContainer<>(TypWiadomosci.class);
    dekretacjeContainer = new BeanItemContainer<>(KomorkaOrganizacyjna.class);

    // fill values
    for (TypWiadomosci t : TypWiadomosci.values()) {
      twContainer.addItem(t);
    }
    for (KomorkaOrganizacyjna ko : KomorkaOrganizacyjna.values()) {
      koContainer.addItem(ko);
    }

    // bind containers
    typPisma.setContainerDataSource(typPismaContainer);
    typWiadomosci.setContainerDataSource(twContainer);
    komorkiOrganizacyjne.setContainerDataSource(koContainer);

    // converters
    TypPismaConverter<Object, TypPisma> typPismaConverter =
        new TypPismaConverter<Object, TypPisma>();
    typPisma.setConverter(typPismaConverter);

    // properties
    komorkiOrganizacyjne.setNewItemsAllowed(true);
    komorkiOrganizacyjne.setFilteringMode(FilteringMode.CONTAINS);

    // icons
    cancel.setIcon(FontAwesome.TIMES);
    save.setIcon(FontAwesome.CHECK);
    attach.setImmediate(true);
    scan.setIcon(FontAwesome.PRINT);
  }

}
