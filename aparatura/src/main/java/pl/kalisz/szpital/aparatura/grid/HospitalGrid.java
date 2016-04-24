package pl.kalisz.szpital.aparatura.grid;

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import com.vaadin.data.Container.Indexed;
import com.vaadin.ui.Grid;

@SuppressWarnings("serial")
public abstract class HospitalGrid extends Grid {

	public HospitalGrid(Indexed dataSource) {
		super(dataSource);
		prepareGrid();
		prepareColumns();
	}

	public abstract void prepareColumns();

	public void prepareGrid() {
		setSizeFull();
		setRenderers();
		setEditorEnabled(true);
		setEditorSaveCaption("Zapisz");
		setEditorCancelCaption("Anuluj");
	}

	protected void hideColumns(Object[] visibleArray) {
		Vector<String> visible = new Vector(Arrays.asList(visibleArray));
		Vector<String> hidden = new Vector();
		Collection properties = getContainerDataSource().getContainerPropertyIds();
		for (Object s : properties) {
			String id = s.toString();
			if (!visible.contains(id)) {
				System.out.println("Hide " + id);
				hidden.add(id);
			}
		}
		for (String s : hidden) {
			getContainerDataSource().removeContainerProperty(s);
			System.out.println(getContainerDataSource().getContainerPropertyIds());
			System.out.println(s + " hidden");
		}
	}

	public abstract void setRenderers();
}