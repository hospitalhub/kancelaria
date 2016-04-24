package pl.kalisz.szpital.kancelaria.ui.tables;

import java.io.File;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.data.filerepo.FilepathLocationStrategy;
import pl.kalisz.szpital.kancelaria.ui.windows.PdfDocumentWindow;
import pl.kalisz.szpital.kancelaria.ui.windows.PdfTransactionDocumentWindow;
import pl.kalisz.szpital.utils.Strings;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class TransactionActionHandler implements Handler {

	// private Action archive = new
	// Action(Strings.PRZENIES_DO_ARCHIWUM);
	private Action showPdf = new Action(Strings.DOKUMENT);
	private Action edit = new Action(Strings.EDYCJA);

	@Override
	public Action[] getActions(final Object target, final Object sender) {
		return new Action[] { showPdf, edit /* , archive */};
	}

	@Override
	public void handleAction(final Action action, final Object sender,
			final Object target) {
		KancelariaUI ui = (KancelariaUI) UI.getCurrent();
		@SuppressWarnings("unchecked")
		JPAContainer<Transaction> container = (JPAContainer<Transaction>) ui
				.getDbHelper().getContainer(DbHelper.TRANSACTION_CONTAINER);
		if (target == null || container.getItem(target) == null) {
			return;
		}
		EntityItem<Transaction> item = container.getItem(target);
		Transaction transaction = item.getEntity();
		if (action == edit) {
			VaadinSession.getCurrent().setAttribute(KancelariaUI.TRANSACTION_EDITOR, item);
//			new TransactionEditorView(item);
			UI.getCurrent().getNavigator().navigateTo(KancelariaUI.TRANSACTION_EDITOR);
		} else if (action == showPdf) {
			FileLocationContext ctx = new FileLocationContext();
			ctx.setFileLocationStrategy(new FilepathLocationStrategy());
			if (!ctx.exists(transaction)) {
				Notification.show(Strings.BRAK_PLIKU,
						transaction.getPlikSciezka(), Type.WARNING_MESSAGE);
			} else {
				File f = ctx.getFile(transaction);
				FileResource fr = new FileResource(f);
				PdfDocumentWindow window = new PdfTransactionDocumentWindow(fr);
				UI.getCurrent().addWindow(window);
			}
			// } else if (action == archive) {
			// archivise(transaction);
		}
	}
}