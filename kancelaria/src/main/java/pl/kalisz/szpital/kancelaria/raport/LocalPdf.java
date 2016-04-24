package pl.kalisz.szpital.kancelaria.raport;

import com.vaadin.server.StreamResource;

/**
 * This class creates a PDF with the iText library. This class implements the StreamSource interface
 * which defines the getStream method.
 */
@SuppressWarnings("serial")
public class LocalPdf extends StreamResource {

  /**
   * Instantiates a new local pdf.
   * 
   * @param streamSource the stream source
   * @param filename the filename
   */
  public LocalPdf(StreamSource streamSource, String filename) {
    super(streamSource, filename);
    StreamResource resource = new StreamResource(streamSource, filename);

    resource.setMIMEType("application/pdf");
    resource.getStream().setParameter("Content-Disposition", "attachment; filename=" + filename);
  }

}
