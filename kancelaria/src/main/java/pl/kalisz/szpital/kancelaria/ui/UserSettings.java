package pl.kalisz.szpital.kancelaria.ui;

import java.io.Serializable;

import com.vaadin.cdi.UIScoped;

import pl.kalisz.szpital.kancelaria.data.pojo.Role;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.User;

@SuppressWarnings("serial")
@UIScoped
public class UserSettings implements Serializable {

  private User user;
  
  private Transaction lastTransaction;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isAdmin() {
    if (user != null) {
      return user.hasRole(Role.POWER_USER);
    } else {
      return false;
    }
  }

  public void setLastTransaction(Transaction lastTransaction) {
    this.lastTransaction = lastTransaction;
  }
  
  public Transaction getLastTransaction() {
    return lastTransaction;
  }

}
