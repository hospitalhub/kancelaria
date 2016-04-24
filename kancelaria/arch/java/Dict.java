package pl.kalisz.szpital.kancelaria.utils.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dict {

  private static final String ASPELL = "echo \"%s\" | aspell -a -p pl";
  private final static String WORD = "([\\p{L}\\d\\p{Punct}]+)";
  private final static String WHITESPACE = "(\\s)";
  private final static Pattern PATTERN_WORD = Pattern.compile(WORD + WHITESPACE);
  private final static Pattern PATTERN_ASPELL = Pattern.compile(".+\n& .+: (\\w+), ");
  private static Dict d = null;

  private Dict() {}

  private final static Dict getInstance() {
    if (d == null) {
      d = new Dict();
    }
    return d;
  }

  public static void main(String[] args) {
    System.out
        .println(parse("@(#) International Ispell Version 3.1.20 (but really Aspell 0.60.7-20110707)\n& festyyn 2 0: festyny, festyn, festyn, festyn, festyn"));
  }

  private final static String parse(String s) {
    Matcher matcher = PATTERN_ASPELL.matcher(s);
    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return null;
    }
  }

  public static String fix(String text) {
    Matcher matcher = PATTERN_WORD.matcher(text);
    StringBuffer buffer = new StringBuffer();
    while (matcher.find()) {
      String word = matcher.group(1);
      // String command = String.format(ASPELL, word);
      // String s = LocalProcess.getResult(command, new File("."));
      // String result = parse(s);
      // System.out.println(s);
      // System.out.println(result);
      // if (result != null) {
      // buffer.append(result);
      // System.out.println(word + " => " + result);
      // } else {
      buffer.append(word);
      // }
      buffer.append(matcher.group(2));

    }
    return buffer.toString();
  }
}
