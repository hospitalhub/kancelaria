package pl.kalisz.szpital.aparatura.grid;

import java.util.List;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class HospitalFieldFactory extends DefaultFieldGroupFieldFactory {
	@Override
	public <T extends Field> T createField(Class<?> type, Class<T> fieldType) {
		if (String.class.equals(type)) {
			TextField tf = new TextField();
			tf.setNullRepresentation("");
			return (T) tf;
		} else if (List.class.equals(type)) {
			return (T) new ListSelect();
		}
		return super.createField(type, fieldType);
	}
}