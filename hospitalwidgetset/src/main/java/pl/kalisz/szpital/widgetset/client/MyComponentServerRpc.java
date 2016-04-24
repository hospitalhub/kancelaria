package pl.kalisz.szpital.widgetset.client;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

public interface MyComponentServerRpc extends ServerRpc {

    public void clicked(MouseEventDetails mouseDetails);

}