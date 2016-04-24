package pl.kalisz.szpital.kancelaria.ui;

import java.io.File;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.FileResource;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.data.filerepo.FilepathLocationStrategy;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.Strings;

@SuppressWarnings("serial")
public class PdfHandler {

  /** The file ctx. */
  private final FileLocationContext fileCtx = new FileLocationContext();
  private final Transaction transaction;

  public PdfHandler(Transaction transaction) {
    this.transaction = transaction;
  }

  public File getPDF() {
    fileCtx.setFileLocationStrategy(new FilepathLocationStrategy());
    if (!fileCtx.exists(transaction)) {
      return null;
    } else {
      return fileCtx.getFile(transaction);
    }
  }

//  private void scroll() {
//    String iframe = "\"//div[@id='pdf-browser-frame-id']/iframe\"";
//    String path = "\"//html/body\"";
//    String javascript = "function getElementByXpath(path, doc)"
//        + " { return document.evaluate(path, doc, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; }"
//        + " var iframe = getElementByXpath(" + iframe + ", document);"
//        + "var iframeDocument =  iframe.contentWindow.document;" // iframe.contentDocument ||
//        + " console.log('xxxzzz' + JSON.stringify(iframe.contentWindow.document.innerHtml));"
//        + " var embed = getElementByXpath(" + path + ", iframeDocument);"
//        + " console.log('zzzxxx' + JSON.stringify(embed.innerHTML));";
//    com.vaadin.server.Page.getCurrent().getJavaScript().execute(javascript);
//  }

  public void showPDF() {
    File f = getPDF();
    if (f == null) {
      Notification.show(Strings.BRAK_PLIKU, transaction.getPlikSciezka(), Type.WARNING_MESSAGE);
    } else {
      PdfDocumentWindow window = new PdfTransactionDocumentWindow(new FileResource(f));
      UI.getCurrent().addWindow(window);
//      scroll();
    }
  }


}
