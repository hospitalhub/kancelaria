package pl.kalisz.szpital.kancelaria.ui;

import pl.kalisz.szpital.kancelaria.utils.Strings;

public enum ReportType {
  BIEZACE(Strings.BIEZACE_ZDARZENIA), 
  POCZTOWY(Strings.RAPORT_POCZTOWY_POLECONE), 
  ETYKIETY(Strings.RAPORT_ETYKIETY_ADRESÓW);
  
  private ReportType(String name) {
    this.name = name;
  }
  private String name;
  public static ReportType getByName(String name) {
    for(ReportType rt : values()) {
      if (rt.name.equals(name)) return rt;
    }
    return null;
  }
}