package pl.kalisz.szpital.hr.table;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import pl.kalisz.szpital.hr.DBHelper;
import pl.kalisz.szpital.hr.data.entity.Kontraktowiec;
import pl.kalisz.szpital.hr.data.entity.Specjalizacja;

import com.vaadin.data.Container;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Field;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class HRGrid extends Grid {

	private Container kontraktowiecContainer;

	/**
	 * 
	 * @param dbHelper
	 */
	public HRGrid(DBHelper dbHelper) throws Exception {
		Container sc;
		sc = dbHelper.getContainer(Specjalizacja.class);
		kontraktowiecContainer = dbHelper.getContainer(Kontraktowiec.class);
		setContainerDataSource((Container.Indexed)kontraktowiecContainer);
		// XXX
		setImmediate(true);
		setEditorEnabled(true);
		setSizeFull();
//		FieldGroup fieldGroup = new FieldGroup();
//		TextField tf = new TextField("Nip");
//		fieldGroup.bind(tf, "Nip");
		
//		setEditorFieldGroup(fieldGroup);
		setEditorFieldFactory(new DefaultFieldGroupFieldFactory() {
			
			@Override
			public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
				System.out.println("qwe " + dataType);
				System.out.println("qwe " + fieldType);
				if (dataType.getCanonicalName().contains("Set")) {
				TextField tf = new TextField();
				tf.setConverter(new Converter<String,Set>() {

					@Override
					public Set convertToModel(String value,
							Class<? extends Set> targetType, Locale locale)
							throws com.vaadin.data.util.converter.Converter.ConversionException {
						Set s = new HashSet();
						s.add(value);
						return s;
					}

					@Override
					public String convertToPresentation(Set value,
							Class<? extends String> targetType, Locale locale)
							throws com.vaadin.data.util.converter.Converter.ConversionException {
						return value.toString();
					}

					@Override
					public Class<Set> getModelType() {
						return Set.class;
					}

					@Override
					public Class<String> getPresentationType() {
						return String.class;
					}
					
				});
				return (T) tf;
				}
				return super.createField(dataType, fieldType);
			}
		});
		// XXX

	}

}
