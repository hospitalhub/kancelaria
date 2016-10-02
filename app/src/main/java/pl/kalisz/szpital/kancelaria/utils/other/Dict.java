package pl.kalisz.szpital.kancelaria.utils.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class Dict.
 */
public class Dict {

  /** The Constant WORD. */
  private static final String WORD = "([\\p{L}\\d\\p{Punct}]+)";
  
  /** The Constant WHITESPACE. */
  private static final String WHITESPACE = "(\\s)";
  
  /** The Constant PATTERN_WORD. */
  private static final Pattern PATTERN_WORD = Pattern.compile(WORD + WHITESPACE);

  /**
   * Fix.
   *
   * @param text the text
   * @return the string
   */
  public static String fix(String text) {
    Matcher matcher = PATTERN_WORD.matcher(text);
    StringBuffer buffer = new StringBuffer();
    while (matcher.find()) {
      String word = matcher.group(1);
      buffer.append(word);
      buffer.append(matcher.group(2));
    }
    return buffer.toString();
  }
}
