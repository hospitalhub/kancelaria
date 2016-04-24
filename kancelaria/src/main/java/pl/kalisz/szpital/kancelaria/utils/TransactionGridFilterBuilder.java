package pl.kalisz.szpital.kancelaria.utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Container.Filter;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.ui.TransactionGrid;
import pl.kalisz.szpital.kancelaria.ui.UserSettings;

@UIScoped
public class TransactionGridFilterBuilder {

  @Inject
  TransactionGrid grid;

  @Inject
  UserSettings userSettings;

  @Inject
  ContainerFactory containerFactory;

  @PostConstruct
  public void init() {

  }

  private Filter getDateFilter() {
    return DateRange.getFilter(dateRangeEnum);
  }

  private Filter getOrganisationFilter() {
    return organisationEnum.getFilter(userSettings.getUser());
  }

  public void applyFilters() {
    JPAContainer<Transaction> container =
        containerFactory.getContainer(ContainerFactory.TRANSACTION_CONTAINER);
//    container.setApplyFiltersImmediately(false);
//    container.removeAllContainerFilters();
//    container.addContainerFilter(getDateFilter());
//    container.addContainerFilter(getOrganisationFilter());
//    Notification.show("Dokumenty: " + toString(), Type.TRAY_NOTIFICATION);
//    container.applyFilters();
//    container.setApplyFiltersImmediately(true);
  }

  public void set(Object o) {
    if (o instanceof DateRangeEnum) {
      this.dateRangeEnum = (DateRangeEnum) o;
    } else if (o instanceof OrganisationEnum) {
      this.organisationEnum = (OrganisationEnum) o;
    } 
  }

  // DEFAULT VALUES
  public DateRangeEnum dateRangeEnum = DateRangeEnum.TODAY;
  public OrganisationEnum organisationEnum = OrganisationEnum.MY;

  @Override
  public String toString() {
    return String.format("%s %s", organisationEnum.getName(), dateRangeEnum.getName());
  }

}
