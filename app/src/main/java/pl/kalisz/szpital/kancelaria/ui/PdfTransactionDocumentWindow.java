package pl.kalisz.szpital.kancelaria.ui;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.kalisz.szpital.kancelaria.DashboardUI;
import pl.kalisz.szpital.kancelaria.data.pojo.Role;
import pl.kalisz.szpital.kancelaria.utils.other.Dict;
import pl.kalisz.szpital.kancelaria.utils.pdf.OCRProcessor;
import pl.kalisz.szpital.kancelaria.utils.pdf.PDF2Png;
import pl.kalisz.szpital.kancelaria.utils.pdf.Sign;

import com.itextpdf.text.DocumentException;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class PdfTransactionDocumentWindow.
 */
@SuppressWarnings("serial")
public class PdfTransactionDocumentWindow extends PdfDocumentWindow implements Observer {

  /** The ocr processor. */
  private final OCRProcessor ocrProcessor = new OCRProcessor();

  /**
   * Instantiates a new pdf transaction document window.
   * 
   * @param resource the resource
   */
  public PdfTransactionDocumentWindow(Resource resource) {
    super(resource);
    if (resource instanceof FileResource) {
      File f = ((FileResource) resource).getSourceFile();
      if (((DashboardUI) UI.getCurrent()).getUser().hasRole(Role.ADMIN)) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(getSignButton(f));
        layout.addComponent(getOCRButton(f));
        layout.addComponent(getPicButton(f));
        content.addComponent(layout);
      }
      e.setSizeFull();
    }
    ocrProcessor.addObserver(this);
  }


  /**
   * Gets the OCR button.
   * 
   * @param f the f
   * @return the OCR button
   */
  private Component getOCRButton(final File f) {
    Button button = new Button("Na tekst");
    button.setSizeUndefined();
    button.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        try {
          System.out.println("ocruje");
          ocrProcessor.extractTextFromPDF(f);
        } catch (GeneralSecurityException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    return button;
  }

  /**
   * Gets the pic button.
   * 
   * @param f the f
   * @return the pic button
   */
  private Component getPicButton(final File f) {
    Button button = new Button("Na obrazek");
    button.setSizeUndefined();
    button.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        System.out.println("podpisuje");
        String pathOrig = f.getAbsolutePath();
        File png = PDF2Png.execute(pathOrig).get(0);
        e.setSource(new FileResource(png));
      }
    });
    return button;
  }

  /**
   * Sign.
   * 
   * @param f the f
   * @param p the p
   */
  private void sign(File f) {
    try {
      Sign sign = new Sign();
      String pathOrig = f.getAbsolutePath();
      String pathSigned = pathOrig.replace(".pdf", "_signed.pdf");
      sign.signPdfFirstTime(pathOrig, pathSigned, true);
      e.setSource(new FileResource(new File(pathSigned)));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds the overlay.
   * 
   * @param f the f
   */
  private final void addOverlay(final File f) {
    final VerticalLayout overlayLayout = new VerticalLayout();
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < 10000; i++) {
      buffer.append(" " + i);
    }
    Label l = new Label(buffer.toString());
    l.setWidth("80%");

    overlayLayout.addComponent(l);
    overlayLayout.addLayoutClickListener(new LayoutClickListener() {

      @Override
      public void layoutClick(LayoutClickEvent event) {
        System.out.println("ov" + event.getRelativeX() + " " + event.getRelativeY());
        sign(f);
      }
    });
    content.addComponent(overlayLayout, "left: 100px; width: 200px; top: 20px; height: 100px;");
  }

  /**
   * Gets the sign button.
   * 
   * @param f the f
   * @return the sign button
   */
  private Component getSignButton(final File f) {
    Button button = new Button("Podpisz");
    button.setSizeUndefined();
    button.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        System.out.println("podpisuje");
        addOverlay(f);
      }
    });
    return button;
  }



  /**
   * Gets the adres pattern.
   * 
   * @return the adres pattern
   */
  private String getAdresPattern() {
    // XXX Str buff
    String polskieZnaki = "ĄĘŁÓŃĆŚŻŹąęłóńćśżź";
    String ulica = "(ul.|al.)?([A-Za-z " + polskieZnaki + "]+[0-9]+[A-Za-z ]?)";
    String kod = "([0-9]{2} ?- ?[0-9]{3})";
    String miasto = "(Kalisz|Warszawa|Poznań|Łódź|Wrocław|[A-Za-z " + polskieZnaki + "]+)";
    String przerwa = "[\\s\\n,\\.]{1,5}";
    String adresPatternString1 = ulica + przerwa + kod + przerwa + miasto;
    String adresPatternString2 = kod + przerwa + miasto + przerwa + ulica;
    return "(" + adresPatternString1 + ")|(" + adresPatternString2 + ")";
  }

  /**
   * Find address.
   * 
   * @param s the s
   */
  private void findAddress(String s) {
    Pattern pattern = Pattern.compile(getAdresPattern());
    Matcher matcher = pattern.matcher(s);
    while (matcher.find()) {
      System.out.println("ADRES: " + matcher.group(0));
    }
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
   */
  @Override
  public void update(Observable o, Object arg) {
    Notification.show("gotowe");
    final String s = arg.toString();
    findAddress(s);
    final TextArea ta = new TextArea("Treść");
    ta.setValue(s);
    ta.setWidth("50%");
    ta.setHeight("200px");
    content.addComponent(ta, "right: 0px; bottom: 0px;");
    Button b = new Button("popraw pisownie");
    b.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        String s1;
        s1 = Dict.fix(s);
        ta.setValue(s1);
      }
    });
    content.addComponent(b, "right:0px; bottom: 0px;");
  }
}
