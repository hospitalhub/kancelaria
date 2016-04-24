package pl.kalisz.szpital.kancelaria.ui;

import java.io.File;
import java.util.regex.Pattern;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;

@SuppressWarnings("serial")
class ThumbSwitcher extends HorizontalLayout {

  @SuppressWarnings("unused")
  private final static Pattern REGEX = Pattern.compile("([0-9]+).png");
  private Image thumbImage = new Image();
  private Page page;

  private final Button buttonRefresh = new Button("W kolejce... odśwież", FontAwesome.REFRESH);
  private final Button buttonGenerate = new Button("Zrób miniaturę", FontAwesome.SQUARE_O);
  private final Button buttonLeft = new Button(FontAwesome.ARROW_CIRCLE_LEFT);
  private final Button buttonRight = new Button(FontAwesome.ARROW_CIRCLE_RIGHT);
  private final Button buttonDocument = new Button(FontAwesome.FILE_TEXT);

  public void setPage(Page page) {
    this.page = page;
    File f = page.getImage().getSourceFile();
    if (f.exists()) {
      thumbImage.setSource(page.getImage());
      thumbImage.setCaption(String.format("str. %d", page.getPageNumber()));
    } else {
      thumbImage.setCaption("Brak miniatury.");
    }
  }

  private final class NextClickListener implements Button.ClickListener {

    @Override
    public void buttonClick(ClickEvent event) {
      setPage(page.getNextPage());
    }
  }


  private final class PreviousClickListener implements Button.ClickListener {

    @Override
    public void buttonClick(ClickEvent event) {
      setPage(page.getPreviousPage());
    }

  }

  public final void showRefreshButton() {
    this.addComponent(buttonRefresh);
  }

  /**
   * Gets the thumb.
   * 
   * @param source the source
   * @param itemId the item id
   * @return the thumb
   */
  public static Component getThumb(File pdf, Object itemId, Transaction transaction) {
    if (pdf == null) {
      return null;
    }

    return new ThumbSwitcher(pdf.getAbsolutePath(), itemId, transaction);
  }
  
  public ThumbSwitcher(String documentPath, Object itemId, Transaction transaction) {
    
    Page p = new Page(documentPath, 1);

//    buttonRefresh.addClickListener(new RefreshDetailsListener(grid, itemId));
    buttonGenerate.addClickListener(new GenerateButtonListener(documentPath, this));
    buttonLeft.addClickListener(new PreviousClickListener());
    buttonRight.addClickListener(new NextClickListener());
    buttonDocument.addClickListener(e -> {
      new PdfHandler(transaction).showPDF();
    });
    thumbImage.addClickListener(e -> {
      new PdfHandler(transaction).showPDF();
    });
    buttonLeft.setDescription("Poprzednia strona");
    buttonRight.setDescription("Następna strona");
    buttonDocument.setDescription("Pokaż dokument");

    if (!p.getImage().getSourceFile().exists()) {
      if (ThumbGenerator.isWaiting(documentPath)) {
        buttonDocument.setCaption("Dokument");
        this.addComponent(buttonDocument);
        this.addComponent(buttonRefresh);
      } else {
        buttonDocument.setCaption("Dokument");
        this.addComponent(buttonDocument);
        this.addComponent(buttonGenerate);
      }
    } else {
      setPage(p);
      this.addComponent(new VerticalLayout(buttonLeft, buttonRight, buttonDocument));
      this.addComponent(thumbImage);
      this.setSizeFull();
      thumbImage.setHeight(210, Unit.PIXELS);
    }


  }
}
