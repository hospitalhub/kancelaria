package pl.kalisz.szpital.kancelaria.utils;

public class TransactionMessage {
  String typ;
  Object id;
  boolean isNew;
  String userLogin;

  @Override
  public String toString() {
    String msg =
        String.format("%s %s %s %s", userLogin.toUpperCase(), isNew ? "dodaje" : "edytuje", typ, id);
    return msg;
  }
  
  public Object getId() {
    return id;
  }
  
  public String getUserLogin() {
    return userLogin;
  }

  public TransactionMessage(String typ, Object id, boolean isNew, String userLogin) {
    super();
    System.out.println(id + " id " + id.getClass());
    this.typ = typ;
    this.id = id;
    this.isNew = isNew;
    this.userLogin = userLogin;
  }

}