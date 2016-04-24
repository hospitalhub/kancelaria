package pl.kalisz.szpital.kancelaria.ui;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.db.kancelaria.enums.KomorkaOrganizacyjna;
import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.data.filerepo.SygnaturaFileLocation;
import pl.kalisz.szpital.kancelaria.ui.windows.PdfDocumentWindow;
import pl.kalisz.szpital.kancelaria.utils.other.Dict;
import pl.kalisz.szpital.kancelaria.utils.pdf.OCRProcessor;
import pl.kalisz.szpital.kancelaria.utils.pdf.PDF2Png;
import pl.kalisz.szpital.kancelaria.utils.pdf.Sign;
import pl.kalisz.szpital.utils.Strings;

import com.itextpdf.text.DocumentException;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DeprecatedUIComponentsFactory implements Observer {

	private final OCRProcessor ocrProcessor = new OCRProcessor();

	// DODAJ do klasy gdzie jest ocr button
	// ocrProcessor.addObserver(this);

	/**
	 * Gets the OCR button.
	 * 
	 * @param f
	 *            the f
	 * @return the OCR button
	 */
	@Deprecated
	private Component getOCRButton(final File f) {
		Button button = new Button("Na tekst");
		button.setSizeUndefined();
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					System.out.println("ocruje");
					ocrProcessor.extractTextFromPDF(f);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return button;
	}

	/**
	 * Gets the pic button.
	 * 
	 * @param f
	 *            the f
	 * @return the pic button
	 */
	@Deprecated
	private Component getPicButton(final File f, PdfDocumentWindow e) {
		Button button = new Button("Na obrazek");
		button.setSizeUndefined();
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("podpisuje");
				String pathOrig = f.getAbsolutePath();
				File png = PDF2Png.execute(pathOrig).get(0);
//				e.setSource(new FileResource(png));
			}
		});
		return button;
	}

	/**
	 * Sign.
	 * 
	 * @param f
	 *            the f
	 * @param p
	 *            the p
	 */
	private void sign(File f) {
		try {
			Sign sign = new Sign();
			String pathOrig = f.getAbsolutePath();
			String pathSigned = pathOrig.replace(".pdf", "_signed.pdf");
			sign.signPdfFirstTime(pathOrig, pathSigned, true);
//			e.setSource(new FileResource(new File(pathSigned)));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the overlay.
	 * 
	 * @param f
	 *            the f
	 */
	private final Component addOverlay(final File f) {
		final VerticalLayout overlayLayout = new VerticalLayout();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 10000; i++) {
			buffer.append(" " + i);
		}
		Label l = new Label(buffer.toString());
		l.setWidth("80%");

		overlayLayout.addComponent(l);
		overlayLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				System.out.println("ov" + event.getRelativeX() + " "
						+ event.getRelativeY());
				sign(f);
			}
		});
		// absolute layout
		//"left: 100px; width: 200px; top: 20px; height: 100px;");
		return overlayLayout;
	}

	/**
	 * Gets the sign button.
	 * 
	 * @param f
	 *            the f
	 * @return the sign button
	 */
	@Deprecated
	private Component getSignButton(final File f) {
		Button button = new Button("Podpisz");
		button.setSizeUndefined();
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("podpisuje");
				addOverlay(f);
			}
		});
		return button;
	}

	/**
	 * Gets the adres pattern.
	 * 
	 * @return the adres pattern
	 */
	private String getAdresPattern() {
		// XXX Str buff
		String polskieZnaki = "ĄĘŁÓŃĆŚŻŹąęłóńćśżź";
		String ulica = "(ul.|al.)?([A-Za-z " + polskieZnaki
				+ "]+[0-9]+[A-Za-z ]?)";
		String kod = "([0-9]{2} ?- ?[0-9]{3})";
		String miasto = "(Kalisz|Warszawa|Poznań|Łódź|Wrocław|[A-Za-z "
				+ polskieZnaki + "]+)";
		String przerwa = "[\\s\\n,\\.]{1,5}";
		String adresPatternString1 = ulica + przerwa + kod + przerwa + miasto;
		String adresPatternString2 = kod + przerwa + miasto + przerwa + ulica;
		return "(" + adresPatternString1 + ")|(" + adresPatternString2 + ")";
	}

	/**
	 * Find address.
	 * 
	 * @param s
	 *            the s
	 */
	private void findAddress(String s) {
		Pattern pattern = Pattern.compile(getAdresPattern());
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			System.out.println("ADRES: " + matcher.group(0));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		Notification.show("gotowe");
		final String s = arg.toString();
		findAddress(s);
		final TextArea ta = new TextArea("Treść");
		ta.setValue(s);
		ta.setWidth("50%");
		ta.setHeight("200px");
//		content.addComponent(ta, "right: 0px; bottom: 0px;");
		Button b = new Button("popraw pisownie");
		b.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				String s1;
				s1 = Dict.fix(s);
				ta.setValue(s1);
			}
		});
//		content.addComponent(b, "right:0px; bottom: 0px;");
	}
	
	

	/**
	 * Ocruj pliki.
	 * 
	 * @return the component
	 */
	private Component ocrujPliki() {
		Button b = new Button("Ocruj wszystkie pliki");
		b.addClickListener(e -> {
			FileLocationContext context = new FileLocationContext();
			context.setFileLocationStrategy(new SygnaturaFileLocation());
			ArrayList<EntityItem<Transaction>> transactions = getAllTransactions();
			// int i = 0;
			for (final EntityItem<Transaction> ei : transactions) {
				// if (i++ > 4) {
				// break;
				// }
//				LOGGER.info(ei.getItemId());
				final Transaction t = ei.getEntity();
				if (t.getPlikSciezka() != null
						&& !t.getPlikSciezka().equals(Strings.EMPTY_STRING)
						&& new File(t.getPlikSciezka()).exists()
						&& new File(t.getPlikSciezka()).length() > 8192) {
//					LOGGER.info(t.getSygnatura());
					String s = "";
					// ocrProcessor.extractTextFromPDF(new
					// File(t.getPlikSciezka()));
					s = StringUtils.normalizeSpace(s);
					s = StringUtils.substring(s, 0, 200);
					ei.getItemProperty(Strings.OPIS).setValue(s);
				}
			}
		});
		return b;
	}

	/**
	 * Gets the all transactions.
	 * 
	 * @return the all transactions
	 */
	@Deprecated
	private final ArrayList<EntityItem<Transaction>> getAllTransactions() {
		ArrayList<EntityItem<Transaction>> transactionsEntities = new ArrayList<EntityItem<Transaction>>();
		KancelariaUI ui = ((KancelariaUI) UI.getCurrent());
		DbHelper dbHelper = ui.getDbHelper();
		@SuppressWarnings("unchecked")
		JPAContainer<Transaction> transactionContainer = (JPAContainer<Transaction>) dbHelper
				.getContainer(DbHelper.TRANSACTION_CONTAINER);
		List<Filter> filters = new ArrayList<Container.Filter>(
				transactionContainer.getContainerFilters());
//		LOGGER.debug("filters:" + filters.size());
		transactionContainer.removeAllContainerFilters();
		transactionContainer.applyFilters();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Vector<Integer> ids = new Vector(transactionContainer.getItemIds());
		for (Integer id : ids) {
			EntityItem<Transaction> ei = transactionContainer.getItem(id);
			transactionsEntities.add(ei);
		}
		for (Filter f : filters) {
			transactionContainer.addContainerFilter(f);
		}
		transactionContainer.applyFilters();
		return transactionsEntities;
	}
	
	// TODO(AM) tu przeniesc filtry !! zrobic nadklase dla obiektow
		// komponent + filtr

		/**
		 * Builds the filter date.
		 *
		 * @param date
		 *            the date
		 * @return the popup date field
		 */
		public static PopupDateField buildFilterDate(Date date) {
			PopupDateField filterDate = new PopupDateField();
			filterDate.setImmediate(true);
			filterDate.setDateFormat(Strings.DD_MM_YY);
			filterDate.setResolution(Resolution.DAY);
			filterDate.setLocale(new Locale("pl", "PL"));
			filterDate.setDescription(Strings.DATA);
			filterDate.setValue(date);
			return filterDate;
		}
	//
//		/**
//		 * Builds the typ pisma.
//		 *
//		 * @param ui
//		 *            the ui
//		 * @return the list select
//		 */
//		public static ListSelect buildTypPisma() {
//			KancelariaUI dashboardUI = ((KancelariaUI) UI.getCurrent());
//			@SuppressWarnings("unchecked")
//			JPAContainer<TypPisma> typPismaContainer = (JPAContainer<TypPisma>) dashboardUI
//					.getDbHelper().getContainer(DbHelper.TYP_PISMA_CONTAINER);
//			ListSelect typPismaSelect = new ListSelect(Transaction.TYP_PISMA_STR,
//					typPismaContainer);
//			typPismaSelect.setNewItemsAllowed(false);
//			typPismaSelect.setNullSelectionAllowed(false);
//			typPismaSelect.setItemCaptionPropertyId(TypPisma.NAZWA);
//			typPismaSelect.setImmediate(true);
//			return typPismaSelect;
//		}

		/**
		 * Builds the komorka combo.
		 *
		 * @param newItemsAllowed
		 *            the new items allowed
		 * @return the combo box
		 */
		public static ComboBox buildKomorkaCombo(boolean newItemsAllowed) {
			final ComboBox komorkaCombo = new ComboBox();
			komorkaCombo.setWidth(Strings.PERCENT92);
			for (KomorkaOrganizacyjna s : KomorkaOrganizacyjna.values()) {
				Object itemId = komorkaCombo.addItem(s);
				komorkaCombo.setItemCaption(itemId, s.toString());
			}
			komorkaCombo.setNewItemsAllowed(newItemsAllowed);
			komorkaCombo.setNullSelectionAllowed(false);
			komorkaCombo.setImmediate(true);
			komorkaCombo.setCaption(Strings.KOMORKA_ORG);
			komorkaCombo.setInputPrompt(Strings.WYSZUKIWANIE_KOMOREK_ORG);
			komorkaCombo.addValidator(new NullValidator(
					Strings.WYBIERZ_KOMORKE_ORGANIZACYJNA, false));
			komorkaCombo.setInputPrompt(Strings.WYSZUKIWANIE_KOMOREK_ORG);
			komorkaCombo.setFilteringMode(FilteringMode.CONTAINS);
			return komorkaCombo;
		}

	
}
