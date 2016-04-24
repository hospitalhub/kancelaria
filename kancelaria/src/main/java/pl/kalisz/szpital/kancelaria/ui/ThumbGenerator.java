package pl.kalisz.szpital.kancelaria.ui;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import pl.kalisz.szpital.kancelaria.utils.pdf.PDF2Png;

public class ThumbGenerator {

  private final static ExecutorService executor = Executors.newFixedThreadPool(2);
  @SuppressWarnings("rawtypes")
  private final static HashMap<String, Future> thumbsFutures = new HashMap<>();

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static void generateThumb(String filePath) {
    Future f = executor.submit(new PDF2Png(filePath));
    thumbsFutures.put(filePath, f);
  }

  public static boolean isWaiting(String documentPath) {
    return (thumbsFutures.get(documentPath) != null);
  }


}
