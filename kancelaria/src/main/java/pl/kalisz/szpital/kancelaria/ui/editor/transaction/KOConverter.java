package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import java.io.Serializable;
import java.util.Locale;
import java.util.TreeSet;

import com.vaadin.data.util.converter.Converter;

import pl.kalisz.szpital.kancelaria.data.enums.KomorkaOrganizacyjna;

@SuppressWarnings("serial")
class KOSet implements Serializable {
  private TreeSet<KomorkaOrganizacyjna> set = new TreeSet<>();

  public KOSet(TreeSet<KomorkaOrganizacyjna> set) {
    this.set = set;
  }

  public TreeSet<KomorkaOrganizacyjna> getSet() {
    return set;
  }
}


@SuppressWarnings("serial")
public class KOConverter implements Converter<KOSet, String> {

  @Override
  public String convertToModel(KOSet value, Class<? extends String> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {
    StringBuffer sb = new StringBuffer();
    for (KomorkaOrganizacyjna ko : value.getSet()) {
      sb.append(ko.getKod());
      sb.append(" ");
    }
    return sb.toString().trim();
  }

  @Override
  public KOSet convertToPresentation(String value, Class<? extends KOSet> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {
    TreeSet<KomorkaOrganizacyjna> v = new TreeSet<>();
    for (String s : value.split(" ")) {
      try {
        KomorkaOrganizacyjna ko = KomorkaOrganizacyjna.getByKod(s);
        v.add(ko);
      } catch (Exception e2) {
      }
    }
    return new KOSet(v);
  }

  @Override
  public Class<String> getModelType() {
    return String.class;
  }

  @Override
  public Class<KOSet> getPresentationType() {
    return KOSet.class;
  }



}
