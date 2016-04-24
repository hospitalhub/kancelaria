package pl.kalisz.szpital.kancelaria.raport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The Class RaportKorespondencjaPdfFooter.
 */
final class RaportKorespondencjaPdfFooter implements PdfPageEvent {

  /** The bf. */
  private static BaseFont bf;
  static {
    try {
      bf = BaseFont.createFont(BaseFont.COURIER, "UTF-8", false);
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** The header font. */
  private static Font headerFont = new Font(bf, 9, Font.NORMAL);

  /** The sdf. */
  private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onStartPage(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document)
   */
  @Override
  public void onStartPage(final PdfWriter writer, final Document document) {
    //
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onSectionEnd(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document, float)
   */
  @Override
  public void onSectionEnd(final PdfWriter writer, final Document document,
      final float paragraphPosition) {
    //
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onSection(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document, float, int, com.itextpdf.text.Paragraph)
   */
  @Override
  public void onSection(final PdfWriter writer, final Document document,
      final float paragraphPosition, final int depth, final Paragraph title) {
    //
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onParagraphEnd(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document, float)
   */
  @Override
  public void onParagraphEnd(final PdfWriter writer, final Document document,
      final float paragraphPosition) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onParagraph(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document, float)
   */
  @Override
  public void onParagraph(final PdfWriter writer, final Document document,
      final float paragraphPosition) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onOpenDocument(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document)
   */
  @Override
  public void onOpenDocument(final PdfWriter writer, final Document document) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onGenericTag(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document, com.itextpdf.text.Rectangle, java.lang.String)
   */
  @Override
  public void onGenericTag(final PdfWriter writer, final Document document, final Rectangle rect,
      final String text) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onEndPage(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document)
   */
  @Override
  public void onEndPage(final PdfWriter writer, final Document document) {
    PdfContentByte cb = writer.getDirectContent();

    // header content
    String headerContent = "Korespondencja WSZ " + sdf.format(new Date());

    ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, new Phrase(headerContent, headerFont),
        document.leftMargin() - 1, document.top() + 30, 0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onCloseDocument(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document)
   */
  @Override
  public void onCloseDocument(final PdfWriter writer, final Document document) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onChapterEnd(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document, float)
   */
  @Override
  public void onChapterEnd(final PdfWriter writer, final Document document,
      final float paragraphPosition) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.itextpdf.text.pdf.PdfPageEvent#onChapter(com.itextpdf.text.pdf.PdfWriter,
   * com.itextpdf.text.Document, float, com.itextpdf.text.Paragraph)
   */
  @Override
  public void onChapter(final PdfWriter writer, final Document document,
      final float paragraphPosition, final Paragraph title) {

  }
}
