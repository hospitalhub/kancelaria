package pl.kalisz.szpital.db.kancelaria.enums;

import java.util.HashSet;

/**
 * The Enum KomorkaOrganizacyjna.
 */
public enum KomorkaOrganizacyjna {

  /** The dn. */
  DN("DN", "Dyrektor"), //
  /** The dl. */
  DL("DL", "Z-ca Dyrektora ds. Lecznictwa"), //
  /** The de. */
  DE("DE", "Z-ca Dyrektora ds. Ekonomiczno - Administracyjnych"), //
  /** The dt. */
  DT("DT", "Z-ca Dyrektora ds. Eksploatacyjno - Technicznych"), //
  /** The dp. */
  DP("DP", "Z-ca Dyrektora ds. Pielęgniarstwa"), //
  /** The no. */
  NO("NO", "Dział Organizacyjny i Nadzoru"), //
  /** The N o2. */
  NO2("NO2", "Dokumentacja Medyczna"), //
  /** The N o3. */
  NO3("NO3", "Statystyka medyczna"), //
  /** The N o4. */
  NO4("NO4", "Archiwum"), //
  /** The np. */
  NP("NP", "Dział Służb Pracowniczych"), //
  /** The npk. */
  NPK("NPK", "Kadry"), //
  /** The npp. */
  NPP("NPP", "Płace"), //
  /** The nk. */
  NK("NK", "Dział Controlingu"), //
  /** The ni. */
  NI("NI", "Dział Informatyki"), //
  /** The nr. */
  NR("NR", "Dział Prawny"), //
  /** The nc. */
  NC("NC", "Dział Spraw Obronnych"), //
  /** The nb. */
  NB("NB", "Dział BHP"), //
  /** The na. */
  NA("NA", "Dział Audytu Wewnętrznego"), //
  /** The ne. */
  NE("NE", "Pielęgniarki Epidemiologiczne"), //
  /** The ns. */
  NS("NS", "Pielęgniarka Środowiskowa Przyszpitalna"), //
  /** The nzj. */
  NZJ("NZJ", "Pełnomocnik Dyrektora ds. Systemu Zarządzania Jakością "), //
  /** The npr. */
  NPR("NPR", "Pełnomocnik Praw Pacjenta"), //
  /** The nko. */
  NKO("NKO", "Pełnomocnik Dyrektora Szpitala ds. Kombatantów i Osób Represjonowanych"), //
  /** The tr. */
  TR("TR", "Dział Utrzymania Ruchu"), //
  /** The trk. */
  TRK("TRK", "Kierownik "), //
  /** The tra. */
  TRA("TRA", "Sekcja aparatury medycznej "), //
  /** The trr. */
  TRR("TRR", "Sekcja remontowa "), //
  /** The trc. */
  TRC("TRC", "Sekcja ciepłownicza "), //
  /** The tre. */
  TRE("TRE", "Sekcja elektryczna "), //
  /** The tp. */
  TP("TP", "Dział Przeciwpożarowy"), //
  /** The ee. */
  EE("EE", "Dział Ekonomiczny"), //
  /** The el. */
  EL("EL", "Dział Logistyki"), //
  /** The elz. */
  ELZ("ELZ", "Zaopatrzenie "), //
  /** The elt. */
  ELT("ELT", "Transport "), //
  /** The elm. */
  ELM("ELM", "Magazyn "), //
  /** The ez. */
  EZ("EZ", "Dział Zamówień Publicznych"), //
  /** The lp. */
  LP("LP", "Pogotowie Ratunkowe"), //
  /** The lk. */
  LK("LK", "Kierownicy"), //
  /** The lkwi. */
  LKWI("LKWI", "Oddział Chorób Wewnętrznych I i Gastroenterologii"), //
  /** The lkwii. */
  LKWII("LKWII", "Oddział Chorób Wewnętrznych II"), //
  /** The lkchi. */
  LKCHI("LKCHI", "Oddział Chirurgii Ogólnej i Naczyniowej"), //
  /** The lkchii. */
  LKCHII("LKCHII", "Oddział Chirurgii Ogólnej i Przewodu Pokarmowego"), //
  /** The lkchd. */
  LKCHD("LKCHD", "Oddział Chirurgii Dziecięcej"), //
  /** The lkchdz. */
  LKCHDZ("LKCHDZ", "Oddział Chorób Dziecięcych"), //
  /** The lkgd. */
  LKGD("LKGD", "Oddział Gastroenterologii Dziecięcej"), //
  /** The lkai. */
  LKAI("LKAI", "Oddział Anestezjologii i intensywnej Terapii"), //
  /** The lknr. */
  LKNR("LKNR", "Oddział Neurochirurgiczny"), //
  /** The lkn. */
  LKN("LKN", "Oddział Neurologiczny"), //
  /** The lko. */
  LKO("LKO", "Oddział Okulistyczny"), //
  /** The lkchsz. */
  LKCHSZ("LKCHSZ", "Oddział Chirurgii Szczękowo - Twarzowej"), //
  /** The lkot. */
  LKOT("LKOT", "Oddział Otolaryngologiczny"), //
  /** The lku. */
  LKU("LKU", "Oddział Urologiczny"), //
  /** The lkuo. */
  LKUO("LKUO", "Oddział Urazowo - Ortopedyczny"), //
  /** The lkk. */
  LKK("LKK", "Oddział Kardiologiczny"), //
  /** The lknf. */
  LKNF("LKNF", "Oddział Nefrologiczny"), //
  /** The lkr. */
  LKR("LKR", "Oddział Reumatologiczny"), //
  /** The lkrh. */
  LKRH("LKRH", "Oddział Rehabilitacyjny"), //
  /** The lkpg. */
  LKPG("LKPG", "Oddział Położniczo - Ginekologiczny"), //
  /** The lkpn. */
  LKPN("LKPN", "Oddział Patologii i Intensywnej Terapii Noworodka"), //
  /** The lkz. */
  LKZ("LKZ", "Oddział Obserwacyjno - Zakaźny"), //
  /** The lkp. */
  LKP("LKP", "Oddział Psychiatryczny"), //
  /** The lkrhd. */
  LKRHD("LKRHD", "Oddział Dzienny Rehabilitacji Dziecięcej"), //
  /** The lkbo. */
  LKBO("LKBO", "Blok Operacyjny"), //
  /** The lkh. */
  LKH("LKH", "Pracownia Hemodynamiki"), //
  /** The lksor. */
  LKSOR("LKSOR", "Szpitalny Oddział Ratunkowy"), //
  /** The lkzdo. */
  LKZDO("LKZDO", "Zakład Diagnostyki Obrazowe"), //
  /** The lkzdl. */
  LKZDL("LKZDL", "Zakład Diagnostyki Laboratoryjnej"), //
  /** The lkzmk. */
  LKZMK("LKZMK", "Zakład Mikrobiologii Klinicznej"), //
  /** The lkzp. */
  LKZP("LKZP", "Zakład Patomorfologii"), //
  /** The lozi */
  LOZI("LOZI", "Onkologiczny Zespół Interdyscyplinarny wraz z Koordynatorem"), //
  /** The lpe. */
  LPE("LPE", "Pracownia Endoskopowa"), //
  /** The lzlu. */
  LZLU("LZLU", "Zakład Leczniczego Usprawniania"), //
  /** The lka. */
  LKA("LKA", "Apteka"), //
  /** The lpeeg. */
  LPEEG("LPEEG", "Pracownia Elektrodiagnostyki"), //
  /** The lpeng. */
  LPENG("LPENG", "Pracownia ENG"), //
  /** The lkzps. */
  LKZPS("LKZPS", "Zespół Poradni Specjalistycznych"), //
  /** The lpsa. */
  LPSA("LPSA", "Poradnia Audiologiczna"), //
  /** The lpsd. */
  LPSD("LPSD", "Poradnia Dermatologiczna"), //
  /** The lpsk. */
  LPSK("LPSK", "Poradnia Kardiologiczna"), //
  /** The lpskd. */
  LPSKD("LPSKd", "Poradnia Kardiologiczna dziecięca"), //
  /** The lpsu. */
  LPSU("LPSU", "Poradnia Urologiczna"), //
  /** The lpsnr. */
  LPSNR("LPSNR", "Poradnia Neurochirurgiczna"), //
  /** The lpsdbd. */
  LPSDBD("LPSDBd", "Poradnia Diabetologiczna dla dzieci"), //
  /** The lpshod. */
  LPSHOD("LPSHOd", "Poradnia Hematologiczno Onkologiczna dla dzieci"), //
  /** The lpsh. */
  LPSH("LPSH", "Poradnia Hematologiczna"), //
  /** The lpsg. */
  LPSG("LPSG", "Poradnia Gastroenterologii"), //
  /** The lpsgd. */
  LPSGD("LPSGd", "Poradnia Gastroenterologii dziecięcej"), //
  /** The lpsnf. */
  LPSNF("LPSNF", "Poradnia Nefrologiczna"), //
  /** The lpsnfd. */
  LPSNFD("LPSNFd", "Poradnia Nefrologiczna dla dzieci"), //
  /** The lpschd. */
  LPSCHD("LPSCHd", "Poradnia Chirurgii Dziecięcej"), //
  /** The lpsch. */
  LPSCH("LPSCH", "Poradnia Chirurgii Ogólnej"), //
  /** The lpschn. */
  LPSCHN("LPSCHN", "Poradnia Chirurgii Chorób Naczyń"), //
  /** The lpsrm. */
  LPSRM("LPSRM", "Poradnia Oceny Rozwoju Małego Dziecka"), //
  /** The lpsr. */
  LPSR("LPSR", "Poradnia Reumatologiczna"), //
  /** The lpschzt. */
  LPSCHZT("LPSCHZt", "Poradnia Chorób Zakaźnych (toruńska)"), //
  /** The lpsdpt. */
  LPSDPT("LPSDPt", "Poradnia Chorób Odzwierzęcych i Pasożytniczych (toruńska)"), //
  /** The lpsuo. */
  LPSUO("LPSUO", "Poradnia Urazowo Ortopedyczna"), //
  /** The lpsp. */
  LPSP("LPSP", "Poradnia Preluksacyjna"), //
  /** The lpsszcz. */
  LPSSZCZ("LPSSZCZ", "Poradnia Chirurgii Szczękowo Twarzowej "), //
  /** The lpso. */
  LPSO("LPSO", " Poradnia Okulistyczna z Laseroterapia"), //
  /** The lpswp. */
  LPSWP("LPSWP", "Poradnia Wad Postawy"), //
  /** The lpcr. */
  LPCR("LPCR", "Centralna Rejestracja"), //
  /** The po. */
  PO("PO", "Oddziałowe"), //
  /** The powi. */
  POWI("POWI", "Oddziałowa Oddziału Chorób Wewnętrznych I i Gastroenterologii"), //
  /** The powii. */
  POWII("POWII", "Oddziałowa Oddziału Chorób Wewnętrznych II"), //
  /** The pochi. */
  POCHI("POCHI", "Oddziałowa Oddziału Chirurgii Ogólnej i Naczyniowej"), //
  /** The pochii. */
  POCHII("POCHII", "Oddziałowa Oddziału Chirurgii Ogólnej i Przewodu Pokarmowego"), //
  /** The pochd. */
  POCHD("POCHD", "Oddziałowa Oddziału Chirurgii Dziecięcej"), //
  /** The pochdz. */
  POCHDZ("POCHDZ", "Oddziałowa Oddziału Chorób Dziecięcych"), //
  /** The pogd. */
  POGD("POGD", "Oddziałowa Oddziału Gastroenterologii Dziecięcej"), //
  /** The poai. */
  POAI("POAI", "Oddziałowa Oddziału Anestezjologii i intensywnej Terapii"), //
  /** The ponr. */
  PONR("PONR", "Oddziałowa Oddziału Neurochirurgicznego"), //
  /** The pon. */
  PON("PON", "Oddziałowa Oddziału Neurologicznego"), //
  /** The poo. */
  POO("POO", "Oddziałowa Oddziału Okulistycznego"), //
  /** The pochsz. */
  POCHSZ("POCHSZ", "Oddziałowa Oddziału Chirurgii Szczękowo - Twarzowej"), //
  /** The poot. */
  POOT("POOT", "Oddziałowa Oddziału Otolaryngologicznego"), //
  /** The pou. */
  POU("POU", "Oddziałowa Oddziału Urologicznego"), //
  /** The pouo. */
  POUO("POUO", "Oddziałowa Oddziału Urazowo - Ortopedycznego"), //
  /** The pok. */
  POK("POK", "Oddziałowa Oddziału Kardiologicznego"), //
  /** The ponf. */
  PONF("PONF", "Oddziałowa Oddziału Nefrologicznego"), //
  /** The por. */
  POR("POR", "Oddziałowa Oddziału Reumatologicznego"), //
  /** The porh. */
  PORH("PORH", "Oddziałowa Oddziału Rehabilitacyjnego"), //
  /** The posor. */
  POSOR("POSOR", "Szpitalny Oddziałowa Oddziału Ratunkowego"), //
  /** The popg. */
  POPG("POPG", "Oddziałowa Oddziału Położniczo - Ginekologicznego"), //
  /** The popn. */
  POPN("POPN", "Oddziałowa Oddziału Patologii i Intensywnej Terapii Noworodka"), //
  /** The poz. */
  POZ("POZ", "Oddziałowa Oddziału Obserwacyjno - Zakaźnego"), //
  /** The pop. */
  POP("POP", "Oddziałowa Oddziału Psychiatrycznego"), //
  /** The popai. */
  POPAI("POPAI", "Oddziałowa Pododdziału Anestezjologii z Salą Intensywnego Nadzoru"), //
  /** The pobo. */
  POBO("POBO", "Oddziałowa: Blok Operacyjny"), //
  /** The pobot. */
  POBOT("POBOT", "Oddziałowa: Blok Operacyjny Toruńska"), //
  /** The pocs. */
  POCS("POCS", "Oddziałowa: Centralna Sterylizatornia"), //
  /** The poh. */
  POH("POH", "Oddziałowa: Poradnia Hemodynamiki"), //
  /** The pons. */
  PONS("PONS", "Oddziałowa: Nocna i Świąteczna Opieka Zdrowotna"), //
  /** The rozdzielnik. */
  ROZDZIELNIK("ROZDZIELNIK", "wg rozdzielnika"), //
  /** The stp. */
  STP("STP", "Samodzielne stanowisko ds. promocji"), //
  /** The L4. */
  L4("L4", "ZUS Druki L4"), //
  /** The zw. */
  ZW("ZW", "Związki Zawodowe"), //
  /** The zwns. */
  ZWNS("ZWNS", "Związki zawodowe Solidarność"), //
  /** The zwopip. */
  ZWOPIP("ZWOPiP", "Związki Zawodowe Pielęgniarek i Położnych"), //
  /** The zwl. */
  ZWL("ZWL", "Związek Zawodowy Lekarzy"), //
  /** The zwpoz. */
  ZWPOZ("ZWPOZ", "Międzyzakładowy Związek Zawodowy Pracowników Ochrony Zdrowia przy WSZ"), //
  /** The zwd. */
  ZWD("ZWD", "Związek Zawodowy Diagnostów"), //
  /** The zwrm. */
  ZWRM("ZWRM", "Związek zawodowy Ratowników Medycznych"), //
  /** The inpr. */
  INPR("INPR", "Inspektor Pracy"); //

  /** The kod. */
  private final String kod;

  /** The nazwa. */
  private final String nazwa;

  /** The Constant KODY. */
  private static final HashSet<String> KODY = new HashSet<String>();
  static {
    for (KomorkaOrganizacyjna ko : KomorkaOrganizacyjna.values()) {
      KODY.add(ko.kod.toUpperCase());
    }
  }

  /**
   * Gets the by kod.
   *
   * @param kod the kod
   * @return the by kod
   * @throws IllegalArgumentException the illegal argument exception
   */
  public static KomorkaOrganizacyjna getByKod(final String kod) throws IllegalArgumentException {
    try {
      return KomorkaOrganizacyjna.valueOf(KomorkaOrganizacyjna.class, kod.toUpperCase());
    } catch (Exception e) {
      throw new IllegalArgumentException(kod);
    }
  }

  /**
   * Contains.
   *
   * @param kod the kod
   * @return true, if successful
   */
  public static boolean contains(final String kod) {
    return KODY.contains(kod.toUpperCase());
  }

  /**
   * Instantiates a new komorka organizacyjna.
   *
   * @param skrot the skrot
   * @param nazwa the nazwa
   */
  private KomorkaOrganizacyjna(final String skrot, final String nazwa) {
    this.kod = skrot;
    this.nazwa = nazwa;
  }

  /**
   * Gets the kod.
   *
   * @return the kod
   */
  public String getKod() {
    return kod;
  }

  /**
   * Gets the nazwa.
   *
   * @return the nazwa
   */
  public String getNazwa() {
    return nazwa;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return kod + " - " + nazwa;
  }

}
