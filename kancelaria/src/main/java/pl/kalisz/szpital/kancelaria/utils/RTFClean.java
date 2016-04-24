package pl.kalisz.szpital.kancelaria.utils;

/**
 * The Class RTFClean.
 */
public class RTFClean {

  /** The Constant RTF. */
  private static final String[] RTF = {
      "{rtf1ansiansicpg1250deff0{fonttbl{f0fnilfcharset238{*fname Arial;}Arial CE;}}",
      "{rtf1ansideff0{fonttbl{f0fnilfcharset238{*fname Arial;}Arial CE;}}",
      "viewkind4uc1pardlang1045f0fs16", "\npar }", "par "};

  /** The Constant PL. */
  private static final String[][] PL = {

  {"'b9", "ą"}, // /**/
      {"'ea", "ę"}, // /**/
      {"'e6", "ć"}, // /**/
      {"'b3", "ł"}, // /**/
      {"'f1", "ń"}, // /**/
      {"'f3", "ó"}, // /**/
      {"'9c", "ś"}, // /**/
      {"'bf", "ż"}, // /**/
      {"'9f", "ź"}, // /**/

      {"'a5", "Ą"}, // /**/
      {"'ca", "Ę"}, // /**/
      {"'a3", "Ł"}, // /**/
      {"'d1", "Ń"}, // /**/
      {"'d3", "Ó"}, // /**/
      {"'8c", "Ś"}, // /**/
      {"'af", "Ż"}, // /**/
      {"'8f", "Ź"}, // /**/

  };

  /**
   * Removes the rtf.
   * 
   * @param s the s
   * @return the string
   */
  public static String removeRTF(String s) {
    for (String rtf : RTF) {
      s = s.replace(rtf, "");
    }
    for (String[] pl : PL) {
      s = s.replaceAll(pl[0], pl[1]);
    }
    return s;
  }
}
