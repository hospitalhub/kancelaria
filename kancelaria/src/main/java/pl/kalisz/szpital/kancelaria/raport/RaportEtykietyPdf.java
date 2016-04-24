package pl.kalisz.szpital.kancelaria.raport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import pl.kalisz.szpital.kancelaria.data.pojo.Address;
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
public class RaportEtykietyPdf implements StreamSource {

  /** The os. */
  private final ByteArrayOutputStream os = new ByteArrayOutputStream();

  /** The bf. */
  private static BaseFont bf = null;

  /** The modified_font. */
  private static Font modifiedBold12 = null;
  private static Font modified8 = null;
  private static Font modified12 = null;

  /**
   * Instantiates a new raport poczta pdf.
   * 
   * @param trans the trans
   */
  public RaportEtykietyPdf(List<Transaction> trans) {
    Document document = null;
    try {
      bf = BaseFont.createFont("Helvetica", "ISO-8859-2", false);
      modifiedBold12 = new Font(bf, 12, Font.BOLD);
      modified8 = new Font(bf, 8, Font.NORMAL);
      modified12 = new Font(bf, 12, Font.NORMAL);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    try {
      document = new Document(PageSize.A4, 0, 0, 0, 0);
      PdfWriter writer = PdfWriter.getInstance(document, os);
      writer.setPageEvent(new RaportKorespondencjaPdfFooter());
      document.open();
      if (trans == null || trans.isEmpty() || trans.size() == 0) {
        document.add(new Phrase(
            "\n\n Brak adresów. Sprawdź wybrany zakres dat i spróbuj ponownie.", modifiedBold12));
      } else {
        PdfPTable table = createTable1(trans);
        document.add(table);
      }
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
   * @param transactionList the trans
   * @return the pdf p table
   * @throws DocumentException the document exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static PdfPTable createTable1(List<Transaction> transactionList) throws DocumentException,
      UnsupportedEncodingException {
    PdfPTable table = new PdfPTable(3);
    table.setWidthPercentage(100);
    table.setWidths(new int[] { 1, 1, 1 });
    int sentLetterNumber = 0;
    int sentRegisterredLetterNumber = 0;
    for (Transaction transaction : transactionList) {
      ++sentLetterNumber;
      Address adres = transaction.getAdres();
      PdfPCell cell = new PdfPCell();
      PdfPTable innerTable = new PdfPTable(1);
      innerTable.setWidthPercentage(100.0f);
      // skroc do 80 znakow
      String name = StringUtils.abbreviate(adres.getNazwa() + "\n", 83);
      Phrase namePhrase = new Phrase(name, modifiedBold12);
      Phrase addressPhrase =
          new Phrase(adres.getUlica() + "\n" + adres.getKod() + " " + adres.getMiasto(), modified12);
      PdfPCell nameCell = new PdfPCell(namePhrase);
      nameCell.setFixedHeight(60.0f);
      nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      nameCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
      nameCell.setBorder(Rectangle.NO_BORDER);
      // PRZESUNIĘCIE 2. I 3. KOL LEKKO W PRAWO
      if (sentLetterNumber % 2 == 0) {
        nameCell.setPaddingLeft(5.0f);
      } else if (sentLetterNumber % 3 == 0) {
        nameCell.setPaddingLeft(10.0f);
      }
      innerTable.addCell(nameCell);
      PdfPCell addressCell = new PdfPCell(addressPhrase);
      addressCell.setFixedHeight(40.0f);
      addressCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      addressCell.setVerticalAlignment(Element.ALIGN_TOP);
      addressCell.setBorder(Rectangle.NO_BORDER);
      innerTable.addCell(addressCell);
      String internalNr = " Nr Ref. WSZ:  " + transaction.getId().toString();
      if (transaction.getPolecony()) {
        internalNr += "      Nr wew.:  " + ++sentRegisterredLetterNumber;
      }
      PdfPCell nrWewCell = new PdfPCell(new Phrase(internalNr, modified8));
      nrWewCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      nrWewCell.setBorder(Rectangle.NO_BORDER);
      innerTable.addCell(nrWewCell);
      cell.addElement(innerTable);
      cell.setBorder(Rectangle.NO_BORDER);
      cell.setFixedHeight(120.0f);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);
    }
    // fill missing cells
    while (sentLetterNumber % 3 != 0) {
      PdfPCell cell = new PdfPCell(new Phrase(""));
      cell.setBorder(Rectangle.NO_BORDER);
      table.addCell(cell);
      sentLetterNumber++;
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
    cell = new PdfPCell(new Paragraph(Strings.DATOWNIK, modifiedBold12));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);
    cell = new PdfPCell(new Paragraph(Strings.KWOTA, modifiedBold12));
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
