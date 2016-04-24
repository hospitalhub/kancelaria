package pl.kalisz.szpital.kancelaria.ui.windows;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;

/**
 * The Class PdfTransactionDocumentWindow.
 */
@SuppressWarnings("serial")
public class PdfTransactionDocumentWindow extends PdfDocumentWindow {

	/**
	 * Instantiates a new pdf transaction document window.
	 * 
	 * @param resource
	 *            the resource
	 */
	public PdfTransactionDocumentWindow(Resource resource) {
		super(resource);
		if (resource instanceof FileResource) {
			File f = ((FileResource) resource).getSourceFile();
			e.setSizeFull();
		}
		
	}

	
}
