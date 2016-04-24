package pl.kalisz.szpital.kancelaria.data.enums;

import java.util.HashMap;

public enum Theme {

//  reindeer("standard", "reindeer"),
//  runo("kancelaria", "runo"),
//  chameleon("kameleon", "chameleon"),
  dark("ciemny", "tests-valo-dark"),
  light("jasny", "tests-valo-light"),
  flatdark("wyraźny", "tests-valo-flatdark"),
  facebook("przejrzysty", "tests-valo-facebook"),
  metro("nowoczesny", "tests-valo-metro"),
  blueprint("niebieski", "tests-valo-blueprint"),
  blueprint2("szpital", "tests-valo-blueprint2"),
  flat("duży", "tests-valo-flat"),
  reindeer("standard", "tests-valo-reindeer"),

  ;
  private final static HashMap<String, Theme> map = new HashMap<>();

  static {
    for (Theme t : Theme.values()) {
      map.put(t.getName(), t);
    }
  }

  public static Theme getByName(String name) {
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
