package pl.kalisz.szpital.aparatura.grid.zlecenie;

import java.util.Date;

import javax.annotation.PostConstruct;

import pl.kalisz.szpital.aparatura.grid.GridLayout;
import pl.kalisz.szpital.db.aparatura.Zlecenie;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.cdi.UIScoped;

@SuppressWarnings("serial")
@UIScoped
public class ZlecenieGridLayout extends GridLayout<Zlecenie> {

	@PostConstruct
	public void pc() {
		System.out.println("ZG PC");
		super.pc();
	}
	
	public ZlecenieGridLayout() {
		super(Zlecenie.class);
		System.out.println("ZG CONSTR");
	}

	protected void updateItem(EntityItem<Zlecenie> z, Object id) {
		jpaContainer.getItem(id).setBuffered(false);
		z.getItemProperty(Zlecenie.NRZLECENIA).setValue(id.toString());
		z.getItemProperty(Zlecenie.DATAZLECENIA).setValue(new Date());
	}

}
