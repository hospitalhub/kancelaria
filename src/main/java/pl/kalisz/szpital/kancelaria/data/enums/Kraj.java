package pl.kalisz.szpital.kancelaria.data.enums;

import java.util.HashSet;

/**
 * The Enum Kraj.
 */
public enum Kraj {

  /** The af. */
  AF("Afganistan"), //
  /** The al. */
  AL("Albania"), //
  /** The dz. */
  DZ("Algieria"), //
  /** The ad. */
  AD("Andora"), //
  /** The ao. */
  AO("Angola"), //
  /** The ai. */
  AI("Anguilla"), //
  /** The aq. */
  AQ("Antarktyda"), //
  /** The ag. */
  AG("Antigua i Barbuda"), //
  /** The sa. */
  SA("Arabia Saudyjska"), //
  /** The ar. */
  AR("Argentyna"), //
  /** The am. */
  AM("Armenia"), //
  /** The aw. */
  AW("Aruba"), //
  /** The au. */
  AU("Australia"), //
  /** The at. */
  AT("Austria"), //
  /** The az. */
  AZ("Azerbejdżan"), //
  /** The bs. */
  BS("Bahamy"), //
  /** The bh. */
  BH("Bahrajn"), //
  /** The bd. */
  BD("Bangladesz"), //
  /** The bb. */
  BB("Barbados"), //
  /** The be. */
  BE("Belgia"), //
  /** The bz. */
  BZ("Belize"), //
  /** The bj. */
  BJ("Benin"), //
  /** The bm. */
  BM("Bermudy"), //
  /** The bt. */
  BT("Bhutan"), //
  /** The by. */
  BY("Białoruś"), //
  /** The bo. */
  BO("Boliwia"), //
  /** The ba. */
  BA("Bośnia i Hercegowina"), //
  /** The bw. */
  BW("Botswana"), //
  /** The br. */
  BR("Brazylia"), //
  /** The bn. */
  BN("Brunei Darussalam"), //
  /** The io. */
  IO("Brytyjskie Terytorium Oceanu Indyjskiego"), //
  /** The bg. */
  BG("Bułgaria"), //
  /** The bf. */
  BF("Burkina Faso"), //
  /** The bi. */
  BI("Burundi"), //
  /** The xc. */
  XC("Ceuta"), //
  /** The cl. */
  CL("Chile"), //
  /** The cn. */
  CN("Chiny"), //
  /** The hr. */
  HR("Chorwacja"), //
  /** The cy. */
  CY("Cypr"), //
  /** The td. */
  TD("Czad"), //
  /** The me. */
  ME("Czarnogóra"), //
  /** The dk. */
  DK("Dania"), //
  /** The dm. */
  DM("Dominika"), //
  /** The do. */
  DO("Dominikana"), //
  /** The dj. */
  DJ("Dżibuti"), //
  /** The eg. */
  EG("Egipt"), //
  /** The ec. */
  EC("Ekwador"), //
  /** The er. */
  ER("Erytrea"), //
  /** The ee. */
  EE("Estonia"), //
  /** The et. */
  ET("Etiopia"), //
  /** The fj. */
  FJ("Fidżi Republika"), //
  /** The ph. */
  PH("Filipiny"), //
  /** The fi. */
  FI("Finlandia"), //
  /** The tf. */
  TF("Francuskie Terytorium Południowe"), //
  /** The fr. */
  FR("Francja"), //
  /** The ga. */
  GA("Gabon"), //
  /** The gm. */
  GM("Gambia"), //
  /** The gh. */
  GH("Ghana"), //
  /** The gi. */
  GI("Gibraltar"), //
  /** The gr. */
  GR("Grecja"), //
  /** The gd. */
  GD("Grenada"), //
  /** The gl. */
  GL("Grenlandia"), //
  /** The ge. */
  GE("Gruzja"), //
  /** The gu. */
  GU("Guam"), //
  /** The gy. */
  GY("Gujana"), //
  /** The gt. */
  GT("Gwatemala"), //
  /** The gn. */
  GN("Gwinea"), //
  /** The gq. */
  GQ("Gwinea Równikowa"), //
  /** The ht. */
  HT("Haiti"), //
  /** The es. */
  ES("Hiszpania"), //
  /** The hn. */
  HN("Honduras"), //
  /** The hk. */
  HK("Hongkong"), //
  /** The in. */
  IN("Indie"), //
  /** The id. */
  ID("Indonezja"), //
  /** The iq. */
  IQ("Irak"), //
  /** The ir. */
  IR("Iran"), //
  /** The ie. */
  IE("Irlandia"), //
  /** The is. */
  IS("Islandia"), //
  /** The il. */
  IL("Izrael"), //
  /** The jm. */
  JM("Jamajka"), //
  /** The jp. */
  JP("Japonia"), //
  /** The ye. */
  YE("Jemen"), //
  /** The jo. */
  JO("Jordania"), //
  /** The ky. */
  KY("Kajmany"), //
  /** The kh. */
  KH("Kambodża"), //
  /** The cm. */
  CM("Kamerun"), //
  /** The ca. */
  CA("Kanada"), //
  /** The qa. */
  QA("Katar"), //
  /** The kz. */
  KZ("Kazachstan"), //
  /** The ke. */
  KE("Kenia"), //
  /** The kg. */
  KG("Kirgistan"), //
  /** The ki. */
  KI("Kiribati"), //
  /** The co. */
  CO("Kolumbia"), //
  /** The km. */
  KM("Komory"), //
  /** The cg. */
  CG("Kongo"), //
  /** The kp. */
  KP("Koreańska Republika Ludowo-Demokratyczna"), //
  /** The xk. */
  XK("Kosowo"), //
  /** The cr. */
  CR("Kostaryka"), //
  /** The cu. */
  CU("Kuba"), //
  /** The kw. */
  KW("Kuwejt"), //
  /** The la. */
  LA("Laos"), //
  /** The ls. */
  LS("Lesotho"), //
  /** The lb. */
  LB("Liban"), //
  /** The lr. */
  LR("Liberia"), //
  /** The ly. */
  LY("Libia"), //
  /** The li. */
  LI("Liechtenstein"), //
  /** The lt. */
  LT("Litwa"), //
  /** The lu. */
  LU("Luksemburg"), //
  /** The lv. */
  LV("Łotwa"), //
  /** The mk. */
  MK("Macedonia"), //
  /** The mg. */
  MG("Madagaskar"), //
  /** The yt. */
  YT("Majotta"), //
  /** The mo. */
  MO("Makau"), //
  /** The mw. */
  MW("Malawi"), //
  /** The mv. */
  MV("Malediwy"), //
  /** The my. */
  MY("Malezja"), //
  /** The ml. */
  ML("Mali"), //
  /** The mt. */
  MT("Malta"), //
  /** The mp. */
  MP("Mariany Północne"), //
  /** The ma. */
  MA("Maroko"), //
  /** The mr. */
  MR("Mauretania"), //
  /** The mu. */
  MU("Mauritius"), //
  /** The mx. */
  MX("Meksyk"), //
  /** The xl. */
  XL("Melilla"), //
  /** The fm. */
  FM("Mikronezja"), //
  /** The md. */
  MD("Mołdowa"), //
  /** The mn. */
  MN("Mongolia"), //
  /** The ms. */
  MS("Montserrat"), //
  /** The mz. */
  MZ("Mozambik"), //
  /** The mm. */
  MM("Myanmar (Burma)"), //
  /** The na. */
  NA("Namibia"), //
  /** The nr. */
  NR("Nauru"), //
  /** The np. */
  NP("Nepal"), //
  /** The nl. */
  NL("Niderlandy"), //
  /** The de. */
  DE("Niemcy"), //
  /** The ne. */
  NE("Niger"), //
  /** The ng. */
  NG("Nigeria"), //
  /** The ni. */
  NI("Nikaragua"), //
  /** The nu. */
  NU("Niue"), //
  /** The nf. */
  NF("Norfolk"), //
  /** The no. */
  NO("Norwegia"), //
  /** The nc. */
  NC("Nowa Kaledonia"), //
  /** The nz. */
  NZ("Nowa Zelandia"), //
  /** The om. */
  OM("Oman"), //
  /** The pk. */
  PK("Pakistan"), //
  /** The pw. */
  PW("Palau"), //
  /** The pa. */
  PA("Panama"), //
  /** The pg. */
  PG("Papua Nowa Gwinea"), //
  /** The py. */
  PY("Paragwaj"), //
  /** The pe. */
  PE("Peru"), //
  /** The pn. */
  PN("Pitcairn"), //
  /** The pf. */
  PF("Polinezja Francuska"), //
  /** The pl. */
  PL("Polska"), //
  /** The gs. */
  GS("Południowa Georgia i Południowe Wyspy Sandwich"), //
  /** The pt. */
  PT("Portugalia"), //
  /** The cz. */
  CZ("Republika Czeska"), //
  /** The kr. */
  KR("Republika Korei"), //
  /** The za. */
  ZA("Rep.Połud.Afryki"), //
  /** The cf. */
  CF("Rep.Środkowoafryańska"), //
  /** The ru. */
  RU("Rosja"), //
  /** The rw. */
  RW("Rwanda"), //
  /** The eh. */
  EH("Sahara Zachodnia"), //
  /** The bl. */
  BL("Saint Barthelemy"), //
  /** The ro. */
  RO("Rumunia"), //
  /** The sv. */
  SV("Salwador"), //
  /** The ws. */
  WS("Samoa"), //
  /** The as. */
  AS("Samoa Amerykańskie"), //
  /** The sm. */
  SM("San Marino"), //
  /** The sn. */
  SN("Senegal"), //
  /** The xs. */
  XS("Serbia"), //
  /** The sc. */
  SC("Seszele"), //
  /** The sl. */
  SL("Sierra Leone"), //
  /** The sg. */
  SG("Singapur"), //
  /** The sz. */
  SZ("Suazi"), //
  /** The sk. */
  SK("Słowacja"), //
  /** The si. */
  SI("Słowenia"), //
  /** The so. */
  SO("Somalia"), //
  /** The lk. */
  LK("Sri Lanka"), //
  /** The pm. */
  PM("St. Pierre i Miquelon"), //
  /** The vc. */
  VC("St.Vincent i Grenadyny"), //
  /** The us. */
  US("Stany Zjedn. Ameryki"), //
  /** The sd. */
  SD("Sudan"), //
  /** The ss. */
  SS("Sudan Południowy"), //
  /** The sr. */
  SR("Surinam"), //
  /** The sy. */
  SY("Syria"), //
  /** The ch. */
  CH("Szwajcaria"), //
  /** The se. */
  SE("Szwecja"), //
  /** The tj. */
  TJ("Tadżykistan"), //
  /** The th. */
  TH("Tajlandia"), //
  /** The tw. */
  TW("Tajwan"), //
  /** The tz. */
  TZ("Tanzania"), //
  /** The tg. */
  TG("Togo"), //
  /** The tk. */
  TK("Tokelau"), //
  /** The to. */
  TO("Tonga"), //
  /** The tt. */
  TT("Trynidad i Tobago"), //
  /** The tn. */
  TN("Tunezja"), //
  /** The tr. */
  TR("Turcja"), //
  /** The tm. */
  TM("Turkmenistan"), //
  /** The tc. */
  TC("Wyspy Turks i Caicos"), //
  /** The tv. */
  TV("Tuvalu"), //
  /** The ug. */
  UG("Uganda"), //
  /** The ua. */
  UA("Ukraina"), //
  /** The uy. */
  UY("Urugwaj"), //
  /** The uz. */
  UZ("Uzbekistan"), //
  /** The vu. */
  VU("Vanuatu"), //
  /** The wf. */
  WF("Wallis i Futuna"), //
  /** The va. */
  VA("Watykan"), //
  /** The ve. */
  VE("Wenezuela"), //
  /** The hu. */
  HU("Węgry"), //
  /** The gb. */
  GB("Wielka Brytania"), //
  /** The vn. */
  VN("Wietnam"), //
  /** The it. */
  IT("Włochy"), //
  /** The tl. */
  TL("Wschodni Timor"), //
  /** The ci. */
  CI("Wybrzeże Kości Słoniowej"), //
  /** The bv. */
  BV("Wyspa Bouveta"), //
  /** The cx. */
  CX("Wyspa Bożego Narodzenia"), //
  /** The ck. */
  CK("Wyspy Cooka"), //
  /** The hm. */
  HM("Wyspy Heard i McDonald"), //
  /** The fo. */
  FO("Wyspy Owcze"), //
  /** The sb. */
  SB("Wyspy Salomona"), //
  /** The st. */
  ST("Wyspy Św.Tomasza i Książęca"), //
  /** The zm. */
  ZM("Zambia"), //
  /** The cv. */
  CV("Zielony Przylądek"), //
  /** The zw. */
  ZW("Zimbabwe"), //
  /** The ae. */
  AE("Zjednoczone Emiraty Arabskie");

  /** The nazwa. */
  private String nazwa;

  /** The Constant KODY. */
  private static final HashSet<String> KRAJE = new HashSet<String>();
  static {
    for (Kraj k : Kraj.values()) {
      KRAJE.add(k.getNazwa().toUpperCase());
    }
  }

  /**
   * Gets the nazwa.
   * 
   * @return the nazwa
   */
  public String getNazwa() {
    return nazwa;
  }

  /**
   * Instantiates a new kraj.
   * 
   * @param nazwa the nazwa
   */
  private Kraj(String nazwa) {
    this.nazwa = nazwa;
  }

}
