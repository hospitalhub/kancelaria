package pl.kalisz.szpital.kancelaria.ui.editor;

import org.vaadin.tokenfield.TokenField;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.enums.KomorkaOrganizacyjna;
import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.kancelaria.converters.StringToSetConverter;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class KOrgTokenField extends TokenField {
	
	private final ListSelect komorkaListSelect = new ListSelect();
	
	public KOrgTokenField(String caption) {
		super(caption, new VerticalLayout(), InsertPosition.BEFORE);
		createKomorkaListSelect();
		setContainerDataSource(komorkaListSelect);
		setFilteringMode(FilteringMode.CONTAINS);
		setConverter(new StringToSetConverter());
		setImmediate(true);
		setNewTokensAllowed(true);
		setInputPrompt("Kody komórek org.");
	}
	
	private void createKomorkaListSelect() {
		KancelariaUI ui = ((KancelariaUI) UI.getCurrent());
		@SuppressWarnings("unchecked")
		BeanItemContainer<KomorkaOrganizacyjna> container = (BeanItemContainer<KomorkaOrganizacyjna>) ui
				.getDbHelper().getContainer(
						DbHelper.KOMORKA_ORGANIZACYJNA_CONTAINER);
		// komorkaListSelect.setConverter(new StringToSetConverter());
		komorkaListSelect.setNewItemsAllowed(false);
		komorkaListSelect.setNullSelectionAllowed(false);
		komorkaListSelect.setContainerDataSource(container);
		komorkaListSelect.setImmediate(true);
		komorkaListSelect.setMultiSelect(true);
	}

	@Override
	public void addToken(Object tokenId) {
		super.addToken(tokenId);
	}

	@Override
	protected void onTokenInput(Object tokenId) {
		if (tokenId instanceof String) {
			for (String s : ((String) tokenId).split(" ")) {
				try {
					KomorkaOrganizacyjna ko = KomorkaOrganizacyjna.getByKod(s);
					this.addToken(ko);
				} catch (Exception e2) {
				}
			}
			this.focus();
		} else if (tokenId instanceof KomorkaOrganizacyjna) {
			addToken(tokenId);
		}
	}
}