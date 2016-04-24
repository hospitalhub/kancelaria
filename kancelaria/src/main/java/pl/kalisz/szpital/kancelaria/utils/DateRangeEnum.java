package pl.kalisz.szpital.kancelaria.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import com.vaadin.server.FontAwesome;

public enum DateRangeEnum {
  TODAY("dziś", FontAwesome.CALENDAR_O), //
  YESTERDAY("wczoraj", FontAwesome.ARROW_CIRCLE_LEFT), //
  CURRENT_WEEK("tydzień", FontAwesome.SUN_O), //
  CURRENT_MONTH("", FontAwesome.MOON_O), //
  PREVIOUS_MONTH("", FontAwesome.ARROW_CIRCLE_O_LEFT), //
  CURRENT_YEAR("bieżący rok", FontAwesome.GLOBE), //
  PREVIOUS_YEAR("poprzedni rok", FontAwesome.CHEVRON_CIRCLE_LEFT), //
  ALL_TIME("od początku", FontAwesome.DATABASE), //
  BETWEEN_DATES("pomiędzy datami", FontAwesome.CALENDAR);

  private static final String Z_MIES_1$T_B = "z m. %1$tB";
  private String name;
  private FontAwesome icon;

  private DateRangeEnum(String caption, FontAwesome icon) {
    this.name = caption;
    this.icon = icon;
  }

  public FontAwesome getIcon() {
    return icon;
  }

  public String getName() {
    switch (this) {
      case CURRENT_MONTH:
        return String.format(Z_MIES_1$T_B, new Date());
      case PREVIOUS_MONTH:
        return String.format(Z_MIES_1$T_B, DateUtils.getLastMonth());
      default:
        return name;
    }
  }

  final static HashSet<String> names = new HashSet<>();
  final static LinkedHashMap<String, DateRangeEnum> byName = new LinkedHashMap<>();

  static {
    for (DateRangeEnum v : values()) {
      names.add(v.name);
      byName.put(v.name, v);
    }
  }

  public static DateRangeEnum getByName(String name) {
    return byName.get(name);
  }

  public static Set<String> names() {
    return names;
  }

}
