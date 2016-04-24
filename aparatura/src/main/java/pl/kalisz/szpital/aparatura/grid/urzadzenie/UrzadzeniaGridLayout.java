package pl.kalisz.szpital.aparatura.grid.urzadzenie;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;

import pl.kalisz.szpital.aparatura.grid.GridLayout;
import pl.kalisz.szpital.db.aparatura.Urzadzenie;

@SuppressWarnings("serial")
public class UrzadzeniaGridLayout extends GridLayout<Urzadzenie> {


	public UrzadzeniaGridLayout(Class clazz) {
		super(clazz);
	}

	protected void updateItem(EntityItem<Urzadzenie> z, Object id) {
		jpaContainer.getItem(id).setBuffered(false);
	}

}
