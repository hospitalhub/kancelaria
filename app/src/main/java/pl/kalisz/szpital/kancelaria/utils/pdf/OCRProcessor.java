package pl.kalisz.szpital.kancelaria.utils.pdf;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class OCRProcessor.
 */
public class OCRProcessor extends Observable {
  
  /** The Constant logger. */
  static final Logger LOGGER = LoggerFactory.getLogger(OCRProcessor.class);

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

}
