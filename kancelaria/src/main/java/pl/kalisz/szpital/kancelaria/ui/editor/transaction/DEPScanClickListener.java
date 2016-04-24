package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.data.filerepo.PDFReceiver;
import pl.kalisz.szpital.kancelaria.data.filerepo.ScannedFileLocationStrategy;
import pl.kalisz.szpital.kancelaria.ui.PdfDocumentWindow;
import pl.kalisz.szpital.kancelaria.utils.Strings;
import pl.kalisz.szpital.kancelaria.utils.pdf.PDFMerger;

@SuppressWarnings("serial")
@Deprecated
final class DEPScanClickListener extends TransactionFormButtonListener {

  public DEPScanClickListener(TransactionWindow window) {
    super(window);
  }

  @Override
  public void buttonClick(ClickEvent event) {
    addScan(false);
  }
  
  private final FileLocationContext context = new FileLocationContext();

  private PDFReceiver pdfReceiver;

  private File pdfUploadFile;

  /**
   * Adds the save button.
   */

  private void addScan(final boolean temp) {
    final Upload upload = new Upload();
    if (temp) {
      pdfReceiver = new PDFReceiver();
    } else {
//      pdfReceiver = new PDFReceiver(transaction);
    }
    upload.setReceiver(pdfReceiver);
    upload.setStyleName(Strings.STYL_SEKRETARIAT);
    upload.setButtonCaption(Strings.ZALACZ_PLIK);
    upload.addSucceededListener(new SucceededListener() {

      @Override
      public void uploadSucceeded(final SucceededEvent event) {
        upload.setVisible(false);
        pdfUploadFile = pdfReceiver.getFile();
        Notification.show(Strings.DOKUMENT_PRZESLANY, pdfUploadFile.getAbsolutePath(),
            Notification.Type.HUMANIZED_MESSAGE);
      }
    });
    upload.setImmediate(true);
    Button podgladSkanu = new Button(Strings.POBIERZ_SKAN);
    podgladSkanu.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(final ClickEvent event) {
//        String name = ((DashboardUI) UI.getCurrent()).getUser().getLogin();
        // FIXME XXX !!!
        String name = "QWE";
        try {
          int i = PDFMerger.merger(name);
          if (i > 1) {
            Notification.show(Strings.POLACZONO_SKANOW + i);
          }
        } catch (Exception e) {
          Notification.show(Strings.SKANER, Strings.BRAK_SKANU, Notification.Type.ERROR_MESSAGE);
          return;
        }
        context.setFileLocationStrategy(new ScannedFileLocationStrategy());
        pdfUploadFile = context.getFile(name);
        if (pdfUploadFile.exists()) {
          PdfDocumentWindow documentWindow = new PdfDocumentWindow(new FileResource(pdfUploadFile));
          UI.getCurrent().addWindow(documentWindow);
        } else {
          Notification.show(Strings.BRAK_DOKUMENTU);
        }
      }
    });
  }

  
}