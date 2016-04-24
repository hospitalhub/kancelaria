package pl.kalisz.szpital.kancelaria.ui;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.UI;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.Strings;

@UIScoped
public class RaportCommand implements Command {
  
  @Inject
  TransactionGrid grid;

  @Inject
  CheckReportParams brConfirmation;
  
  JPAContainer<Transaction> container;
  
  @Inject
  ContainerFactory dbHelper; 
  
  @PostConstruct
  public void init() {
    container = dbHelper.getContainer(ContainerFactory.TRANSACTION_CONTAINER);
  }

  private ArrayList<Transaction> getTransactions() {
    ArrayList<Transaction> ts = new ArrayList<Transaction>();
    Collection<?> ids = grid.getSelectedRows();
    // nothing selected => get all ids
    if (ids.size() == 0) {
      ids = container.getItemIds();
    }
    for (Object id : ids) {
      ts.add(container.getItem(id).getEntity());
    }
    return ts;
  }

  private void showReport(String reportName) {
    StreamResource resource = null;
    grid.sort(Transaction.DATA_STR, SortDirection.ASCENDING);
    ReportFactory reportFactory = new ReportFactory();
    ReportType reportType = ReportType.getByName(reportName);
    resource = reportFactory.getReport(reportType, getTransactions());
    ((StreamResource) resource).setMIMEType(Strings.APPLICATION_PDF);
    PdfDocumentWindow documentWindow = new PdfDocumentWindow(resource);
    UI.getCurrent().addWindow(documentWindow);
    grid.sort(Transaction.DATA_STR, SortDirection.DESCENDING);
  }

  @Override
  public void menuSelected(final MenuItem selectedItem) {
    if (brConfirmation.checkParams()) {
      String reportName = selectedItem.getText();
      showReport(reportName);
    }
  }
}