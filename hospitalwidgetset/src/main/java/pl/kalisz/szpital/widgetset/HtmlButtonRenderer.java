package pl.kalisz.szpital.widgetset;

import com.vaadin.ui.renderers.ClickableRenderer;

@SuppressWarnings("serial")
public class HtmlButtonRenderer extends ClickableRenderer<String> {

	/**
	 * Creates a new button renderer.
	 *
	 * @param nullRepresentation
	 *            the textual representation of {@code null} value
	 */
	public HtmlButtonRenderer(String nullRepresentation) {
		super(String.class, nullRepresentation);
	}

	/**
	 * Creates a new button renderer and adds the given click listener to it.
	 * 
	 * @param listener
	 *            the click listener to register
	 * @param nullRepresentation
	 *            the textual representation of {@code null} value
	 */
	public HtmlButtonRenderer(RendererClickListener listener, String nullRepresentation) {
		this(nullRepresentation);
		System.out.println("adding click listenre1!!!!!");
		addClickListener(listener);
	}

	/**
	 * Creates a new button renderer.
	 */
	public HtmlButtonRenderer() {
		this("");
	}

	/**
	 * Creates a new button renderer and adds the given click listener to it.
	 *
	 * @param listener
	 *            the click listener to register
	 */
	public HtmlButtonRenderer(RendererClickListener listener) {
		this(listener, "");
	}

	@Override
	public String getNullRepresentation() {
		return super.getNullRepresentation();
	}

}