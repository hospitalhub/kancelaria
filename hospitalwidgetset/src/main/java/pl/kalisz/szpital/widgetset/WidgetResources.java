package pl.kalisz.szpital.widgetset;

import com.vaadin.server.ClassResource;

public class WidgetResources {

	public static ClassResource getImage(String filename) {
		System.out.println();
		return new ClassResource(WidgetResources.class, "/images/" + filename);
	}

}
