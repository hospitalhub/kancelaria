package pl.kalisz.szpital.kancelaria.ui.windows;

import com.vaadin.server.Resource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Window;

/**
 * The Class PdfDocumentWindow.
 */
@SuppressWarnings("serial")
public class PdfDocumentWindow extends Window {

  /** The content. */
  protected AbsoluteLayout content = new AbsoluteLayout();
  
  /** The e. */
  protected final BrowserFrame e = new BrowserFrame();

  /**
   * Instantiates a new pdf document window.
   *
   * @param resource the resource
   */
  public PdfDocumentWindow(Resource resource) {
    setSizeFull();
    setResizable(false);
    setDraggable(false);
    center();
    content.setHeight("100%");
    setContent(content);
    if (resource != null) {
      e.setSizeFull();
      e.setHeight("100%");
      e.setSource(resource);
      content.addComponent(e);
    }
  }

}
