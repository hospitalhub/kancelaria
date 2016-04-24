/*
 * 
 */
package pl.kalisz.szpital.kancelaria.utils.pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * The Class PDFImage.
 */
public class PDF2Png implements Callable {

  private final static ExecutorService service = Executors.newFixedThreadPool(2);

  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger(PDF2Png.class);

  /** The Constant password. */
  private static final String password = "";

  /** The pdf files. */
  private final String pdfFile;

  /** The files. */
  private ArrayList<File> files;

  public PDF2Png(String filePath) {
    this.pdfFile = filePath;
  }

  private final static HashMap<String, Future> status = new HashMap<>();

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
    String files[] = { "/volume2/Kancelaria/00000031.pdf", "/volume2/Kancelaria/00000005.pdf",
        "/volume2/Kancelaria/00000004.pdf", "/volume2/Kancelaria/00000021.pdf",
        "/volume2/Kancelaria/00000053.pdf", };
    for (String file : files) {
      System.out.println("start " + file);
      PDF2Png pdf2Png = new PDF2Png(file);
      Future<Object> f = service.submit(pdf2Png);
      status.put(file, f);
    }
    service.shutdown();
  }

  public static ArrayList<File> execute(String filePath) {
    long start = System.currentTimeMillis();
    ArrayList<File> png = new ArrayList<File>();
    try {
      PDF2Png pdf2Png = new PDF2Png(filePath);
      // pdf2Png.start();
      // pdf2Png.join();
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
  private static ArrayList<File> pdf2pic(String pdfFile)
      throws InterruptedException, IOException, CryptographyException {
    final ArrayList<File> files = new ArrayList<File>();
    PDDocument document = null;
    document = PDDocument.load(pdfFile);
    System.out
        .println(Thread.currentThread().getName() + " " + pdfFile + document.getNumberOfPages());
    // document.print();
    decrypt(document);
    @SuppressWarnings("unchecked")
    final ArrayList<PDPage> pages = (ArrayList<PDPage>) document.getDocumentCatalog().getAllPages();
    int i = 0;
    for (final PDPage page : pages) {
      System.out.println(Thread.currentThread().getName() + " " + pdfFile + "  " + i);
      for (String s : status.keySet()) {
        System.out.println(s + " : " + status.get(s).isDone());
      }
      final String filename = pdfFile + "_" + ++i;
      File f = Page2Png.extract(page, filename, Page2Png.PNG_RESOLUTION);
      files.add(f);
      logger.info(Thread.currentThread() + " (" + Thread.activeCount() + ")" + f.getAbsolutePath());
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
  // this.pdfFile = pdfFile;
  // private PDF2Png(String pdfFile) {
  // }

  /**
   * Gets the files.
   * 
   * @return the files
   */
  public ArrayList<File> getFiles() {
    return files;
  }

  @Override
  public Object call() throws Exception {
    try {
      files = PDF2Png.pdf2pic(pdfFile);
      return files.size();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // XXX
  public Integer getNumOfPages(File pdf) {
    try {
      PDDocument doc = PDDocument.load(pdf);
      Integer pages = doc.getNumberOfPages();
      doc.close();
      return pages;
    } catch (Exception e) {
      return -1;
    }
  }


}
