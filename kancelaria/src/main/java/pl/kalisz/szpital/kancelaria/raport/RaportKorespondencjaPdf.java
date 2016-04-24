package pl.kalisz.szpital.kancelaria.raport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;

/**
 * The Class RaportKorespondencjaPdf.
 * 
 * This class creates a PDF with the iText library. This class implements the StreamSource interface
 * which defines the getStream method.
 */
@SuppressWarnings("serial")
public class RaportKorespondencjaPdf implements StreamSource {

  /** The Constant HEADER. */
  private static final String HEADER = "Data; Numer; Nadawca; Typ; Org.; Data odb.; Odebrano";

  /** The os. */
  private final ByteArrayOutputStream os = new ByteArrayOutputStream();

  /** The bf. */
  private static BaseFont bf = null;

  /** The modified_font. */
  private static Font modifiedfont = null;

  /** The Constant sdf. */
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Instantiates a new raport korespondencja pdf.
   * 
   * @param trans the trans
   */
  public RaportKorespondencjaPdf(List<Transaction> trans) {
    Document document = null;
    try {
      bf = BaseFont.createFont("Helvetica", "ISO-8859-2", false);
      modifiedfont = new Font(bf, 9, Font.NORMAL);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    try {
      document = new Document(PageSize.A4, 50, 50, 50, 50);
      PdfWriter writer = PdfWriter.getInstance(document, os);
      writer.setPageEvent(new RaportKorespondencjaPdfFooter());
      document.open();
      PdfPTable table = createTable1(trans);
      table.setSpacingBefore(5);
      table.setSpacingAfter(5);
      document.add(table);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (document != null) {
        document.close();
      }
    }
  }

  /**
   * Format odbiorca.
   * 
   * @param odbiorca the odbiorca
   * @return the string
   */
  private static final String formatOdbiorca(String odbiorca) {
    return odbiorca.replaceAll("[\t \n]+", "\n\n");
  }

  /**
   * Creates the table1.
   * 
   * @param trans the trans
   * @return the pdf p table
   * @throws DocumentException the document exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static PdfPTable createTable1(List<Transaction> trans) throws DocumentException,
      UnsupportedEncodingException {
    PdfPTable table = new PdfPTable(7);
    table.setWidthPercentage(100);
    table.setWidths(new int[] {3, 2, 9, 3, 3, 3, 3});
    String h = new String(HEADER.getBytes(), "UTF-8");
    for (String s : h.split(";")) {
      table.addCell(new Phrase(s, modifiedfont));
    }
    for (Transaction t : trans) {
      table.addCell(new Phrase(sdf.format(t.getData()), modifiedfont));
      table.addCell(new Phrase("" + t.getId(), modifiedfont));
      table.addCell(new Phrase(t.getAdres().toString(), modifiedfont));
      table.addCell(new Phrase(t.getTypPisma().getNazwa(), modifiedfont));
      table.addCell(new Phrase(formatOdbiorca(t.getOdbiorca()), modifiedfont));
      table.addCell(new Phrase("", modifiedfont));
      table.addCell(new Phrase("", modifiedfont));
    }
    return table;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.server.StreamResource.StreamSource#getStream()
   */
  @Override
  public InputStream getStream() {
    return new ByteArrayInputStream(os.toByteArray());
  }
}
