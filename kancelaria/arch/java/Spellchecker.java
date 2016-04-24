package pl.kalisz.szpital.kancelaria.utils.other;

//import org.apache.lucene.analysis.core.SimpleAnalyzer;
//import org.apache.lucene.search.spell.PlainTextDictionary;
//import org.apache.lucene.search.spell.SpellChecker;

@Deprecated
public class Spellchecker {
  public static void main(String[] args) throws Exception {

    /*File dir = new File("c:/spellchecker/");

    Directory directory = FSDirectory.open(dir);

    @SuppressWarnings("resource")
    SpellChecker spellChecker = new SpellChecker(directory);

    IndexWriterConfig config =
        new IndexWriterConfig(Version.LUCENE_47, new SimpleAnalyzer(Version.LUCENE_47));
    File f = new File("slowa-win.txt");
    System.out.println(f.exists());
    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "Cp1252"));
    System.out.println("start indezxing");
    PlainTextDictionary dictionary = new PlainTextDictionary(in);
    spellChecker.indexDictionary(dictionary, config, true);
    System.out.println("indexed!!!");
    String wordForSuggestions = "dzidy";

    int suggestionsNumber = 3;
    String[] suggestions = spellChecker.suggestSimilar(wordForSuggestions, suggestionsNumber);

    if (suggestions != null && suggestions.length > 0) {
      for (String word : suggestions) {
        System.out.println("Did you mean:" + word);
      }
    } else {
      System.out.println("No suggestions found for word:" + wordForSuggestions);
    }
*/
  }
}
