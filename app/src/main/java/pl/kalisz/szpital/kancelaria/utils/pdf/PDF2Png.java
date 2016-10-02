/*
 * 
 */
package pl.kalisz.szpital.kancelaria.utils.pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PDFImage.
 */
public class PDF2Png extends Thread {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(PDF2Png.class);

  /** The Constant password. */
  private static final String password = "";

  /** The pdf files. */
  private final String pdfFile;

  /** The files. */
  private ArrayList<File> files;


  /**
   * Decrypt.
   * 
   * @param document the document
   * @throws CryptographyException the cryptography exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void decrypt(PDDocument document) throws CryptographyException, IOException {
    if (document.isEncrypted()) {
      try {
        document.decrypt(password);
      } catch (InvalidPasswordException e) {
        logger.error("Error: The document is encrypted.");
      }
    }

  }

  /**
   * The main method.
   * 
   * @param args the arguments
   */
  public static void main(String[] args) {
    String files[] =
        { "/volume2/Kancelaria/Przychodzące zewnętrzne/29_04_2014/11374.pdf",
            "/volume2/Kancelaria/Przychodzące zewnętrzne/29_04_2014/11375.pdf",
            "/volume2/Kancelaria/Przychodzące zewnętrzne/29_04_2014/11379.pdf" };
    ExecutorService service = Executors.newFixedThreadPool(8);
    for (String file : files) {
      PDF2Png pdf2Png = new PDF2Png(file);
      service.submit(pdf2Png);
    }
    service.shutdown();
  }

  public static ArrayList<File> execute(String filePath) {
    long start = System.currentTimeMillis();
    ArrayList<File> png = new ArrayList<File>();
    try {
      PDF2Png pdf2Png = new PDF2Png(filePath);
      System.out.println("pdf2" + filePath);
      pdf2Png.start();
      pdf2Png.join();
      png = pdf2Png.getFiles();
    } catch (Exception e) {
      e.printStackTrace();
    }
    logger.info("done in: " + (System.currentTimeMillis() - start));
    return png;
  }


  /**
   * Pdf2pic.
   * 
   * @param pdfFile the pdf file
   * @param endPage the end page
   * @return the array list
   * @throws InterruptedException the interrupted exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws CryptographyException the cryptography exception
   */
  private static ArrayList<File> pdf2pic(String pdfFile) throws InterruptedException, IOException,
      CryptographyException {
    final ArrayList<File> files = new ArrayList<File>();
    PDDocument document = null;
    document = PDDocument.load(pdfFile);
    // document.print();
    decrypt(document);
    @SuppressWarnings("unchecked")
    final ArrayList<PDPage> pages = (ArrayList<PDPage>) document.getDocumentCatalog().getAllPages();
    int i = 0;
    for (final PDPage page : pages) {
      final String filename = pdfFile + "_" + ++i;
      File f = Page2Png.extract(page, filename, Page2Png.PNG_RESOLUTION);
      files.add(f);
      logger.info("writing " + f.getAbsolutePath());
    }
    try {
      document.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return files;
  }

  /**
   * Pdf thum.
   * 
   * @param pdfFile the pdf file
   * @return the array list
   */
  // public static ArrayList<File> pdfThum(String pdfFile) {
  // try {
  // ArrayList<File> files = new ArrayList<File>();
  // File f = pdf2pic(pdfFile, 1).get(0);
  // files.add(f);
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // return null;
  // }

  /**
   * Instantiates a new PDF image.
   * 
   * @param type the type
   * @param pdfFiles the pdf files
   */
  private PDF2Png(String pdfFile) {
    this.pdfFile = pdfFile;
  }

  /**
   * Gets the files.
   * 
   * @return the files
   */
  public ArrayList<File> getFiles() {
    return files;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
    try {
      files = PDF2Png.pdf2pic(pdfFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
