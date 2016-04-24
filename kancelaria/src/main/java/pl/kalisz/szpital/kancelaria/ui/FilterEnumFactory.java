package pl.kalisz.szpital.kancelaria.ui;

import pl.kalisz.szpital.kancelaria.utils.DateRangeEnum;
import pl.kalisz.szpital.kancelaria.utils.OrganisationEnum;

public class FilterEnumFactory {

  public static Enum getFilter(String name) {
    OrganisationEnum org = OrganisationEnum.getByName(name);
    DateRangeEnum date = DateRangeEnum.getByName(name);
    if (org != null) {
      return org;
    } else if (date != null) {
      return date;
    } else {
      return null;
    }
  }

}
