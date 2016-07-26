package pl.kalisz.szpital.kancelaria;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@SuppressWarnings("serial")
@WebServlet(value = "/*", asyncSupported = true, initParams = {
		@WebInitParam(name = "productionMode", value = "false") })
@VaadinServletConfiguration(productionMode = false, ui = pl.kalisz.szpital.kancelaria.DashboardUI.class, widgetset = "pl.kalisz.szpital.kancelaria.data.DashboardWidgetSet")
public class KancelariaServlet extends VaadinServlet {
}
