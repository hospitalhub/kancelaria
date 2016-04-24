package pl.kalisz.szpital.kancelaria.ui.editor;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;



@SuppressWarnings("serial")
class NotificationWindow extends Window {

  public final static String format = "<h3>%s Numer dokumentu</h3><hr><h1>%5d</h1>";
  // FontAwesome.FILE_TEXT.getHtml();

  public NotificationWindow(String msg) {
    super(); // Set window caption
    center();

    // Some basic content for the window
    VerticalLayout content = new VerticalLayout();
    Label label = new Label(msg, ContentMode.HTML);
    content.addComponent(label);
    content.setMargin(true);
    setContent(content);

    // Disable the close button
    setClosable(false);
    setResizable(false);
    setModal(true);

    // Trivial logic for closing the sub-window
    Button ok = new Button("OK", FontAwesome.CHECK);
    ok.addClickListener(new ClickListener() {
      public void buttonClick(ClickEvent event) {
        close(); // Close the sub-window
      }
    });
    content.addComponent(ok);
  }
}
