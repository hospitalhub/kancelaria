package pl.kalisz.szpital.kancelaria.raport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import pl.kalisz.szpital.kancelaria.data.pojo.Adres;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.Strings;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;

/**
 * The Class RaportPocztaPdf.
 */
@SuppressWarnings("serial")
public class RaportPocztaPdf implements StreamSource {

  /** The os. */
  private final ByteArrayOutputStream os = new ByteArrayOutputStream();

  /** The bf. */
  private static BaseFont bf = null;

  /** The modified_font. */
  private static Font modifiedfont = null;

  /**
   * Instantiates a new raport poczta pdf.
   * 
   * @param trans the trans
   */
  public RaportPocztaPdf(List<Transaction> trans) {
    Document document = null;
    try {
      bf = BaseFont.createFont("Helvetica", "ISO-8859-2", false);
      modifiedfont = new Font(bf, 10, Font.NORMAL);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    try {
      document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
      PdfWriter writer = PdfWriter.getInstance(document, os);
      writer.setPageEvent(new RaportPocztaPdfFooter());
      document.open();
      Paragraph p = new Paragraph(Strings.SZPITAL, modifiedfont);
      document.add(p);
      PdfPTable table = createTable1(trans);
      table.setSpacingBefore(5);
      table.setSpacingAfter(5);
      document.add(table);
      document.add(createTableDatownik());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (document != null) {
        document.close();
      }
    }
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
    PdfPTable table = new PdfPTable(10);
    table.setWidthPercentage(100);
    table.setWidths(new int[] {1, 4, 4, 3, 1, 2, 2, 2, 2, 3});
    PdfPCell cell;
    cell = new PdfPCell(new Phrase(Strings.POTWIERDZENIE));
    cell.setColspan(10);
    table.addCell(cell);
    String h = new String(Strings.HEADER.getBytes(), "UTF-8");
    for (String s : h.split(";")) {
      table.addCell(new Phrase(s, modifiedfont));
    }
    int i = 1;
    for (Transaction t : trans) {
      table.addCell(new Phrase("" + i++, modifiedfont));
      Adres adres = t.getAdres();
      table.addCell(new Phrase("" + adres.getNazwa(), modifiedfont));
      table.addCell(new Phrase("" + adres.getUlica(), modifiedfont));
      table.addCell(new Phrase("" + adres.getMiasto() + " " + adres.getKod(), modifiedfont));
      table.addCell(new Phrase("P", modifiedfont));
      table.addCell(new Phrase("", modifiedfont));
      table.addCell(new Phrase("", modifiedfont));
      table.addCell(new Phrase("", modifiedfont));
      table.addCell(new Phrase("", modifiedfont));
      table.addCell(new Phrase(t.getId().toString(), modifiedfont));
    }
    return table;
  }

  /**
   * Creates the table datownik.
   * 
   * @return the pdf p table
   * @throws DocumentException the document exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static PdfPTable createTableDatownik() throws DocumentException,
      UnsupportedEncodingException {
    PdfPTable table = new PdfPTable(2);
    table.setWidthPercentage(40);
    table.setHorizontalAlignment(Element.ALIGN_CENTER);
    PdfPCell cell;
    cell = new PdfPCell(new Paragraph(Strings.DATOWNIK, modifiedfont));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);
    cell = new PdfPCell(new Paragraph(Strings.KWOTA, modifiedfont));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);
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
