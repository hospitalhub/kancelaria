package pl.kalisz.szpital.hospitalwidgetset;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

/**
 * unused
 */
@SuppressWarnings("serial")
@Deprecated
public class ExampleUIProvider extends UIProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.server.UIProvider#getUIClass(com.vaadin.server.
	 * UIClassSelectionEvent)
	 */
	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		// if (event.getRequest().getParameter("mobile") != null
		// && event.getRequest().getParameter("mobile").equals("false")) {
		// return DashboardUI.class;
		// return UI.class;
		// }

		// try {
		// if (event.getRequest().getHeader("user-agent") != null
		// &&
		// event.getRequest().getHeader("user-agent").toLowerCase().contains("mobile")
		// &&
		// !event.getRequest().getHeader("user-agent").toLowerCase().contains("ipad"))
		// {
		// return MobileCheckUI.class;
		// }
		// } catch (Exception e) {
		// System.err.println("mobilecheckui" + e.getMessage());
		// }

		return UI.class;
	}
}
