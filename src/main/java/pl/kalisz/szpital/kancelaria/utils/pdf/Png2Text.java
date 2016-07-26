package pl.kalisz.szpital.kancelaria.utils.pdf;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kalisz.szpital.kancelaria.utils.File2StringUtil;
import pl.kalisz.szpital.kancelaria.utils.LocalProcess;

/**
 * The Class Png2Text.
 */
public class Png2Text extends Thread {

  /** The Constant OVERWRITE. */
  public static final boolean OVERWRITE = true;

  /** The overwrite. */
  private final boolean overwrite;

  /** The png. */
  private final File png;

  /** The Constant logger. */
  static final Logger LOGGER = LoggerFactory.getLogger(Png2Text.class);

  /** The Constant TESS_PATH. */
  private static final String TESS_PATH = "tesseract -l pol -psm 1 %s stdout";

  /** The result. */
  private String result;

  /**
   * Gets the result.
   * 
   * @return the result
   */
  public String getResult() {
    return result;
  }

  /**
   * Instantiates a new png2 text.
   * 
   * @param png the png
   * @param overwrite the overwrite
   */
  protected Png2Text(File png, boolean overwrite) {
    this.png = png;
    this.overwrite = overwrite;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  @Override
  public void run() {
    Charset.forName("UTF-8");
    String command = String.format(TESS_PATH, png.getName());
    File dir = png.getParentFile();
    File textFile = new File(png.getAbsolutePath() + ".txt");
    if (!textFile.exists() || (textFile.exists() && overwrite)) {
      result = LocalProcess.getResult(command, dir);
      File2StringUtil.toFile(textFile, result);
      LOGGER.info("Extracted from: " + png.getAbsolutePath());
    } else {
      try {
        result = File2StringUtil.readFromFile(textFile.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      }
      LOGGER.info("Already extracted:" + png.getAbsolutePath());
    }
  }


  /**
   * The Class X.
   */
  static class X implements Observer {

    /** The done. */
    public boolean done = false;

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable arg0, Object arg1) {
      System.out.println(arg1.toString());
      done = true;
    }
  }

  /**
   * The main method.
   * 
   * @param args the arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    OCRProcessor ocrProcessor = new OCRProcessor();
    X x = new X();
    ocrProcessor.addObserver(x);
    ocrProcessor.extractTextFromPDF(new File("/volume2/Kancelaria/Przychodzące zewnętrzne/"
        + "29_04_2014/11375_signed.pdf")); // 07_01_2014/93.pdf
    System.out.println("done!");
  }

}
