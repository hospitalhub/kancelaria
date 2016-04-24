package pl.kalisz.szpital.kancelaria.ui.tables;

import java.util.Arrays;

import org.tepi.filtertable.paged.PagedFilterControlConfig;

import pl.kalisz.szpital.utils.Strings;

public class TransactionTableControls extends PagedFilterControlConfig {

	/** The Constant PAGE_LENGTH. */
	private static final int PAGE_LENGTH = 30;

	public TransactionTableControls() {
		setPageLengthsAndCaptions(Arrays
				.asList(new Integer[] { PAGE_LENGTH }));
		setNext(Strings.NASTEPNA_STRONA);
		setLast(Strings.OSTATNIA);
		setFirst(Strings.PIERWSZA);
		setPrevious(Strings.POPRZEDNIA);
		setPage("");
		setItemsPerPage(Strings.TRANSAKCJI_NA_STRONE);
	}
}