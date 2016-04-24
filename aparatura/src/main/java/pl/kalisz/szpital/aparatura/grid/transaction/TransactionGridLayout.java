package pl.kalisz.szpital.aparatura.grid.transaction;

import javax.annotation.PostConstruct;

import pl.kalisz.szpital.aparatura.grid.GridLayout;
import pl.kalisz.szpital.db.kancelaria.Transaction;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.cdi.UIScoped;

@SuppressWarnings("serial")
@UIScoped
public class TransactionGridLayout extends GridLayout<Transaction> {


	@PostConstruct
	public void pc() {
		super.pc();
	}
	
	public TransactionGridLayout() {
		super(Transaction.class);
		// TODO Auto-generated constructor stub
	}

	protected void updateItem(EntityItem<Transaction> z, Object id) {
		jpaContainer.getItem(id).setBuffered(false);
		z.getItemProperty(Transaction.SYGNATURA_STR).setValue(id.toString());
	}

}
