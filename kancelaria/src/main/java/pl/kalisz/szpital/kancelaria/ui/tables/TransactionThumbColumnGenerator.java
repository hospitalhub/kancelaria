package pl.kalisz.szpital.kancelaria.ui.tables;

import java.io.File;

import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.kancelaria.data.filerepo.FilepathLocationStrategy;

import com.vaadin.data.Item;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

public class TransactionThumbColumnGenerator implements
		CustomTable.ColumnGenerator {

	@Override
	public Object generateCell(final CustomTable source, final Object itemId,
			final Object columnId) {
		// return getThumb(source, itemId);
		return null;
	}

	/**
	 * Gets the thumb.
	 * 
	 * @param source
	 *            the source
	 * @param itemId
	 *            the item id
	 * @return the thumb
	 */
	private Component getThumb(final CustomTable source, final Object itemId) {
		Item i = source.getItem(itemId);
		Transaction t = new Transaction(i);
		File f = new FilepathLocationStrategy().getFile(t);
		File thum = new File(f.getAbsolutePath() + ".png");
		if (f.exists() && !thum.exists()) {
			// println("thumbnail (should be) generated here");
		}
		if (thum == null || !thum.exists()) {
			return new Label("-");
		}

		Image e = new Image(thum.getName(), new FileResource(thum));
		e.setWidth("64px");
		e.setStyleName("hover_resize");
		return e;
	}

}