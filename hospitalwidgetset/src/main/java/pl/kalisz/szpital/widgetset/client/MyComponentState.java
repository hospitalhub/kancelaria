package pl.kalisz.szpital.widgetset.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

@SuppressWarnings("serial")
public class MyComponentState extends AbstractComponentState {

	@DelegateToWidget
    public String text;

}