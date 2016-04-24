package pl.kalisz.szpital.db.kancelaria;

import java.util.Collection;
import java.util.Date;

/**
 * The Class Kontraktowiec.
 */
public class Kontraktowiec {

  /** The imie. */
  String imie;

  /** The nazwisko. */
  String nazwisko;

  /** The regon. */
  String regon;

  /** The numer prawa wykonywania zawodu. */
  String numerPrawaWykonywaniaZawodu;

  /** The dane wpisu izby lekarskiej. */
  String daneWpisuIzbyLekarskiej;

  /** The data podpisania umowy. */
  Date dataPodpisaniaUmowy;

  /** The specjalizacje. */
  Collection<Object> specjalizacje;
  // nazwa badania / data wykonania / data wazn
  /** The badania. */
  Collection<Object> badania;

  /** The umowy. */
  Collection<Object> umowy;
  // nazwa / data podpisania / data waznosci
  /** The ubezpieczenia oc. */
  Collection<Object> ubezpieczeniaOC;

}
