package pl.kalisz.szpital.widgetset;

import com.vaadin.data.Item;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.FontIcon;

@SuppressWarnings("serial")
public final class FontIconGenerator extends PropertyValueGenerator<String> {
	   
    private final FontIcon fontIcon;
    private final String valuePropertyId;
   
    public FontIconGenerator(FontIcon icon, String valuePropertyId) {
        this.fontIcon = icon;
        this.valuePropertyId = valuePropertyId;
    }
   
    @Override
    public String getValue(Item item, Object itemId, Object propertyId) {
        return String.format("%s %s", fontIcon.getHtml(), item.getItemProperty(valuePropertyId));
    }
    @Override
    public Class<String> getType() {
        return String.class;
    }
}