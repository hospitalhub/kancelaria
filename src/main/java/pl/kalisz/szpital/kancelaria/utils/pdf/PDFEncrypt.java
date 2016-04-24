package pl.kalisz.szpital.kancelaria.utils.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfEncryptor;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

// TODO(AM) http://stackoverflow.com/questions/1936189/itext-disable-printing-copying-saving
/**
 * The Class PDFEncrypt.
 */
public class PDFEncrypt {

  /** The Constant ENCRYPTION_AES128. */
  private static final boolean ENCRYPTION_AES128 = false;
  
  /** The user password. */
  private static String userPassword = "blabla";
  
  /** The owner password. */
  private static String ownerPassword = "bleble";

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws DocumentException the document exception
   */
  public static void main(String[] args) throws FileNotFoundException, IOException,
      DocumentException {
    encrypt("/volume2/Kancelaria/Wychodzące/07_01_2014/0117.pdf",
        "/volume2/Kancelaria/Wychodzące/07_01_2014/0117a.pdf");
  }

  /**
   * Encrypt.
   *
   * @param inputFilePath the input file path
   * @param outputFilePath the output file path
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws DocumentException the document exception
   */
  public static void encrypt(String inputFilePath, String outputFilePath)
      throws FileNotFoundException, IOException, DocumentException {
    File inputFile = new File(inputFilePath);
    File outputFile = new File(outputFilePath);
    int permissions = PdfWriter.ALLOW_PRINTING;
    // | PdfWriter.ALLOW_COPY;

    PdfReader reader = new PdfReader(new FileInputStream(inputFile));
    PdfEncryptor.encrypt(reader, new FileOutputStream(outputFile), ENCRYPTION_AES128, userPassword,
        ownerPassword, permissions);
  }

}
