package pl.kalisz.szpital.aparatura.grid;

import org.apache.commons.lang3.NotImplementedException;

import com.vaadin.data.Container.Indexed;

import pl.kalisz.szpital.aparatura.grid.transaction.TransactionGrid;
import pl.kalisz.szpital.aparatura.grid.urzadzenie.UrzadzenieGrid;
import pl.kalisz.szpital.aparatura.grid.zlecenie.ZlecenieGrid;
import pl.kalisz.szpital.db.aparatura.Urzadzenie;
import pl.kalisz.szpital.db.aparatura.Zlecenie;
import pl.kalisz.szpital.db.kancelaria.Transaction;

public class HospitalGridFactory<T> {

	public static HospitalGrid getGrid(Indexed container, Class entityClass) {
		if (Zlecenie.class.equals(entityClass)) {
			return new ZlecenieGrid(container);
		} else if (Transaction.class.equals(entityClass)) {
			return new TransactionGrid(container);
		} else if (Urzadzenie.class.equals(entityClass)) {
			return new UrzadzenieGrid(container);
		}
		throw new NotImplementedException("Missing type " + entityClass.getCanonicalName());
	}

}
