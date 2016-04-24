package pl.kalisz.szpital.kancelaria;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;

import pl.kalisz.szpital.utils.Strings;

/**
 * The Class MySystemMessagesProvider.
 */
@SuppressWarnings("serial")
public class MySystemMessagesProvider implements SystemMessagesProvider {

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.server.SystemMessagesProvider#getSystemMessages()
   */
  @Override
  public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
    CustomizedSystemMessages messages = new CustomizedSystemMessages();
    messages.setInternalErrorCaption(Strings.BLAD_SYSTEMU);
    messages.setInternalErrorMessage(Strings.ZALOGUJ_PONOWNIE);
    messages.setSessionExpiredCaption(Strings.SESJA_WYGASLA);
    messages.setSessionExpiredMessage(Strings.ZALOGUJ_PONOWNIE);
    messages.setCommunicationErrorCaption(Strings.BLAD_KOMUNIKACJI_Z_SERWEREM);
    messages.setCommunicationErrorMessage(Strings.ZALOGUJ_PONOWNIE);
    return messages;
  }
}
