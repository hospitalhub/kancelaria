package pl.kalisz.szpital.kancelaria.data.filerepo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.utils.Strings;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
// TODO(AM) testy napisać na ilość plików łączonych
// TODO(AM) testy na na wielkość przesyłanego pliku

/**
 * The Class PDFReceiver.
 */
@SuppressWarnings("serial")
public class PDFReceiver implements Receiver {

	/** The file. */
	protected File file;

	/**
	 * Gets the file.
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @see com.vaadin.ui.Upload.Receiver#receiveUpload(java.lang.String,
	 *      java.lang.String)
	 */
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (final java.io.FileNotFoundException e) {
			new Notification(Strings.NIE_UDALO_SIE_OTWORZYC, e.getMessage(),
					Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
			return null;
		}
		return fos;
	}

	/**
	 * Instantiates a new PDF receiver.
	 * 
	 * @param zdarzenie
	 *            the zdarzenie
	 */
	@Deprecated
	public PDFReceiver(Transaction zdarzenie) {
		file = new SygnaturaFileLocation().getFile(zdarzenie);
	}

	public PDFReceiver(Long id) {
		new NumerIdFileLocation().getFile(id);
	}

	/**
	 * Instantiates a new PDF receiver.
	 */
	public PDFReceiver() {
		file = new TempFileLocationStrategy().getFile(null);
	}

}
