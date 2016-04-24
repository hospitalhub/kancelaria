package pl.kalisz.szpital.kancelaria;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * The Class MobileCheckUI.
 */
@SuppressWarnings("serial")
@Theme(Reindeer.THEME_NAME)
@Title("w")
public class MobileCheckUI extends UI {

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
   */
  @Override
  protected void init(final VaadinRequest request) {
    setWidth("400px");
    setContent(new VerticalLayout() {

      {
        setMargin(true);
        addComponent(new Label("<h1>QuickTickets Dashboard</h1>"
            + "<h3>This application is not designed for mobile devices.</h3>"
            + "<p>If you wish, you can continue to <a href=\"" + request.getContextPath()
            + request.getPathInfo() + "?mobile=false\">load it anyway</a>.</p>", ContentMode.HTML));
      }
    });

  }
}
