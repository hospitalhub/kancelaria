package pl.kalisz.szpital.kancelaria.data.enums;

import java.util.HashMap;

public enum Theme {

  reindeer("standard", "reindeer"),

  // dark("ciemny", "tests-valo-dark"),

  // light("jasny", "tests-valo-light"),

  chameleon("kamele", "chameleon"),

  runo("kancelaria", "runo"),

  // facebook("przejrzysty", "tests-valo-facebook"),

  // metro("nowoczesny", "tests-valo-metro"),

  book("księga", "tests-book"),

  // flat("duży", "tests-valo-flat"),

  liferay("świetlny", "liferay"),

  // base("podstawowy", "base"),

  // blueprint("niebieski", "tests-valo-blueprint"),

  // flatdark("wyraźny", "tests-valo-flatdark");
  ;
  private final static HashMap<String, String> map = new HashMap<>();

  static {
    for (Theme t : Theme.values()) {
      map.put(t.getName(), t.getDir());
    }
  }

  public static String getByName(String name) {
    return map.get(name);
  }

  private Theme(String name, String dir) {
    this.dir = dir;
    this.name = name;
  }

  private String name;
  private String dir;

  public String getName() {
    return name;
  }

  public String getDir() {
    return dir;
  }
}
