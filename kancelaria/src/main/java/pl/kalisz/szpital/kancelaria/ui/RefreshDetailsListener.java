package pl.kalisz.szpital.kancelaria.ui;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;

@SuppressWarnings("serial")
@Deprecated
final class RefreshDetailsListener implements ClickListener {
  /**
   * 
   */
  private final Grid grid;
  private final Object itemId;

  public RefreshDetailsListener(Grid grid,
      Object itemId) {
    this.grid = grid;
    this.itemId = itemId;
  }

  public void buttonClick(ClickEvent event) {
    grid.setDetailsVisible(itemId, false);
    grid.setDetailsVisible(itemId, true);
  }
}
