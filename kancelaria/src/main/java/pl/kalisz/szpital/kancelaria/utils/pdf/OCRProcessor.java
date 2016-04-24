package pl.kalisz.szpital.kancelaria.utils.pdf;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.data.filerepo.SygnaturaFileLocation;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * The Class OCRProcessor.
 */
public class OCRProcessor extends Observable {
  
  /** The Constant logger. */
  static final Logger LOGGER = Logger.getLogger(OCRProcessor.class);

  /* (non-Javadoc)
   * @see java.util.Observable#addObserver(java.util.Observer)
   */
  @Override
  public synchronized void addObserver(Observer o) {
    super.addObserver(o);
  }

  /**
   * Extract text from pdf.
   *
   * @param pdf the pdf
   * @throws Exception the exception
   */
  public void extractTextFromPDF(final File pdf) throws Exception {
    new Thread() {
      @Override
      public void run() {
        int cores = Runtime.getRuntime().availableProcessors();
        LOGGER.debug("Cores/Processors:" + cores);
        ExecutorService service = Executors.newFixedThreadPool(cores);
        ArrayList<File> pngs = PDF2Png.execute(pdf.getAbsolutePath());
        StringBuffer buffer = new StringBuffer();
        ArrayList<Png2Text> processors = new ArrayList<Png2Text>();
        for (File pngFile : pngs) {
          Png2Text ocrProcessor = new Png2Text(pngFile, false);
          processors.add(ocrProcessor);
          service.submit(ocrProcessor);
        }
        service.shutdown();
        while (!service.isTerminated()) {
          waitABit();
        }
        LOGGER.debug("done - terminated");
        for (Png2Text ocrProcessor : processors) {
          buffer.append(ocrProcessor.getResult());
        }
        LOGGER.debug("Extracted text:" + StringUtils.abbreviate(buffer.toString(), 30));
        setChanged();
        notifyObservers(buffer.toString());
      }
    }.start();
  }

  /**
   * Wait a bit.
   */
  private static final void waitABit() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  

  /**
   * Ocruj pliki.
   * 
   * @return the component
   */
  public Component ocrujPliki(ArrayList<Transaction> transactions) {
    Button b = new Button("Ocruj wszystkie pliki");
    b.addClickListener(e -> {
      FileLocationContext context = new FileLocationContext();
      context.setFileLocationStrategy(new SygnaturaFileLocation());
      // int i = 0;
      for (final Transaction t : transactions) {
        if (t.getPlikSciezka() != null && !t.getPlikSciezka().equals(Strings.EMPTY_STRING)
            && new File(t.getPlikSciezka()).exists()
            && new File(t.getPlikSciezka()).length() > 8192) {
          System.out.println(t.getSygnatura());
          String s = "";
          try {
            extractTextFromPDF(new File(t.getPlikSciezka()));
          } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
          s = StringUtils.normalizeSpace(s);
          s = StringUtils.substring(s, 0, 200);
        }
      }
    });
    return b;
  }


}
