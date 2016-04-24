package pl.kalisz.szpital.kancelaria.utils;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.filter.IsNull;
import com.vaadin.data.util.filter.Not;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;

public enum OrganisationEnum {

  MY("moje", FontAwesome.USER, false), EVERYONES("wszystkich", FontAwesome.USERS, true);
  private OrganisationEnum(String caption, Resource icon, boolean adminRights) {
    this.name = caption;
    this.adminRights = adminRights;
    this.icon = icon;
  }

  public String getName() {
    return name;
  }

  public boolean needAdminRights() {
    return adminRights;
  }

  public Resource getIcon() {
    return icon;
  }
  
  private boolean adminRights;
  private String name;
  private Resource icon;

  public Filter getFilter(Object user) {
    switch (this) {
      case MY:
        return new Equal(Transaction.USER, user);
      case EVERYONES:
        return new Not(new IsNull(Transaction.USER));
      default:
        return null;
    }
  }

  final static LinkedHashSet<String> names = new LinkedHashSet<>();
  final static LinkedHashMap<String, OrganisationEnum> byName = new LinkedHashMap<>();

  static {
    for (OrganisationEnum v : values()) {
      names.add(v.name);
      byName.put(v.name, v);
    }
  }

  public static OrganisationEnum getByName(String name) {
    return byName.get(name);
  }

  public static Set<String> names() {
    return names;
  }

}
