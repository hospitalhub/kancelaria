package pl.kalisz.szpital.kancelaria.ui.editor;

import java.io.File;

import org.vaadin.appfoundation.authentication.SessionHandler;

import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.data.filerepo.NumerIdFileLocation;
import pl.kalisz.szpital.kancelaria.data.filerepo.PDFReceiver;
import pl.kalisz.szpital.kancelaria.data.filerepo.ScannedFileLocationStrategy;
import pl.kalisz.szpital.kancelaria.utils.pdf.PDFMerger;
import pl.kalisz.szpital.utils.Strings;

import com.vaadin.server.FileResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class FilepathCustomField extends CustomField<String> {
	/** The context. */
	private final FileLocationContext context = new FileLocationContext();
	/** The pdf receiver. */
	private PDFReceiver pdfReceiver;
	/** The pdf upload file. */
	private File pdfUploadFile;
	final Upload upload = new Upload();
	private Button loadScan = new Button(Strings.POBIERZ_SKAN);
	Button noFileButton = new Button("Bez pliku");
	VerticalLayout mainLayout = new VerticalLayout();

	protected final BrowserFrame browser = new BrowserFrame();

	public void setValue(String newFieldValue)
			throws com.vaadin.data.Property.ReadOnlyException,
			com.vaadin.data.util.converter.Converter.ConversionException {
		System.out.println("SET VALUE");
		try {
			File f = new File(newFieldValue.toString());
			browser.setSource(new FileResource(f));
			super.setValue(newFieldValue);
		} catch (Exception e) {
			System.err.println("No file attached");
			System.err.println(e.getMessage());
		}
	}

	@Override
	protected Component initContent() {
		if (getValue() != null && !getValue().toString().equals("")) {
			setValue(getValue());
		}
			
		pdfReceiver = new PDFReceiver();
		upload.setReceiver(pdfReceiver);
		upload.setStyleName(Strings.STYL_SEKRETARIAT);
		upload.setButtonCaption(Strings.ZALACZ_PLIK);
		upload.addSucceededListener(new SucceededListener() {

			@Override
			public void uploadSucceeded(final SucceededEvent event) {
				pdfUploadFile = pdfReceiver.getFile();
				setValue(pdfUploadFile.getAbsolutePath());
				Notification.show(Strings.DOKUMENT_PRZESLANY,
						pdfUploadFile.getAbsolutePath(),
						Notification.Type.HUMANIZED_MESSAGE);
			}
		});
		upload.setImmediate(true);

		loadScan.addClickListener(event -> {
			String name = SessionHandler.get().getUsername();
			System.out.println("USER " + name);

			try {
				int i = PDFMerger.merger(name);
				if (i > 1) {
					Notification.show(Strings.POLACZONO_SKANOW + i);
				}
			} catch (Exception e) {
				Notification.show(Strings.SKANER, Strings.BRAK_SKANU,
						Notification.Type.ERROR_MESSAGE);
				return;
			}
			context.setFileLocationStrategy(new ScannedFileLocationStrategy());
			pdfUploadFile = context.getFile(name);
			setValue(pdfUploadFile.getAbsolutePath());
			if (pdfUploadFile.exists()) {
				browser.setSource(new FileResource(pdfUploadFile));
			} else {
				Notification.show(Strings.BRAK_DOKUMENTU);
			}
		});
		noFileButton.addClickListener(event -> {
			setValue("");
		});


		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponent(loadScan);
		buttonLayout.addComponent(upload);
		buttonLayout.addComponent(noFileButton);

		mainLayout.addComponent(buttonLayout);
		mainLayout.addComponent(browser);
		browser.setSizeFull();
		mainLayout.setExpandRatio(browser, 15);
		mainLayout.setSizeFull();

		return mainLayout;
	}

	@Override
	public Class<? extends String> getType() {
		return String.class;
	}

	/**
	 * Zapisz plik.
	 * 
	 * @param t
	 *            the t
	 * @return the string
	 */
	@Deprecated
	private String zapiszPlik(final Transaction t) {
		TransactionForm.LOGGER.trace("zapisz plik");
		// dodano plik
		if (pdfUploadFile != null && pdfUploadFile.exists()) {
			System.out.println("File: " + pdfUploadFile);
			try {
				context.setFileLocationStrategy(new NumerIdFileLocation());
				final File newFile = context.getFile(t.getId());
				System.out.println(newFile.getAbsolutePath());
				TransactionForm.LOGGER.trace("before thread");
				new Thread() {
					public void run() {
						TransactionForm.LOGGER.trace("before move");
						pdfUploadFile.renameTo(newFile);
						TransactionForm.LOGGER.trace("after move");
					};
				}.start();
				TransactionForm.LOGGER.trace("zwraca nowy plik");
				return newFile.getAbsolutePath();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		if (t.getPlikSciezka() != null && !t.getPlikSciezka().equals("")) {
			// skopiowano zdarzenie wraz z plikiem
			return t.getPlikSciezka();
		}
		return null;
	}
}