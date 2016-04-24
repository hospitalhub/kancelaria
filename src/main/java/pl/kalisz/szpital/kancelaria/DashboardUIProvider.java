package pl.kalisz.szpital.kancelaria;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

/**
 * The Class DashboardUIProvider.
 */
@SuppressWarnings("serial")
public class DashboardUIProvider extends UIProvider {

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.server.UIProvider#getUIClass(com.vaadin.server.UIClassSelectionEvent)
   */
  @Override
  public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
    if (event.getRequest().getParameter("mobile") != null
        && event.getRequest().getParameter("mobile").equals("false")) {
      return DashboardUI.class;
    }

    try {
      if (event.getRequest().getHeader("user-agent") != null
          && event.getRequest().getHeader("user-agent").toLowerCase().contains("mobile")
          && !event.getRequest().getHeader("user-agent").toLowerCase().contains("ipad")) {
        return MobileCheckUI.class;
      }
    } catch (Exception e) {
      System.err.println("mobilecheckui" + e.getMessage());
    }

    return DashboardUI.class;
  }
}
