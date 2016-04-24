package pl.kalisz.szpital.kancelaria.ui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.DetailsGenerator;
import com.vaadin.ui.Grid.RowReference;
import com.vaadin.ui.Label;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;

@SuppressWarnings("serial")
//@UIScoped
// SLOW!
@Deprecated
public class TransactionDetailsGenerator implements DetailsGenerator {

  public Object detailsOpenIndex = null;

  @Override
  public Component getDetails(RowReference rowReference) {
    Transaction t = new Transaction(rowReference.getItem());
    Label l1 = new Label(t.getMetaString(), ContentMode.HTML);
    return l1;
  }

  /*
   * private Component getThumbComponent(Object itemId, Transaction transaction) { PdfHandler
   * pdfHandler = new PdfHandler(transaction); File pdf = pdfHandler.getPDF();
   * 
   * Component thumbSwitcher = ThumbSwitcher.getThumb(pdf, itemId, transaction); if (pdf == null) {
   * return new Label("Brak dokumentu."); } else { return thumbSwitcher; } }
   */

}
