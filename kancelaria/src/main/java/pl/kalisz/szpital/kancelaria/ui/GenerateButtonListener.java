package pl.kalisz.szpital.kancelaria.ui;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
final class GenerateButtonListener implements ClickListener {
  private final String filePath;
  private final ThumbSwitcher thumbSwitcher;
  
  public GenerateButtonListener(String filePath, ThumbSwitcher ts) {
    this.filePath = filePath;
    this.thumbSwitcher = ts;
  }
  
  @Override
  public void buttonClick(ClickEvent event) {
    ThumbGenerator.generateThumb(filePath);
    for (Object l : event.getButton().getListeners(event.getClass())) {
      event.getButton().removeClickListener((ClickListener) l);
    }
    event.getButton().setVisible(false);
    thumbSwitcher.showRefreshButton();
  }
}