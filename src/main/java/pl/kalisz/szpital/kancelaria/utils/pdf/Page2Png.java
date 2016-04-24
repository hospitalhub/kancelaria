package pl.kalisz.szpital.kancelaria.utils.pdf;

import java.awt.image.BufferedImage;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.ImageIOUtil;

/**
 * The Class Page2Png.
 */
public class Page2Png {

  /** The Constant OVERWRITE. */
  private static final boolean OVERWRITE = false;

  /** The Constant IMAGE_FORMAT. */
  private static final String IMAGE_FORMAT = "png";

  /** The Constant THUMB_RESOLUTION. */
  public static final int THUMB_RESOLUTION = 64;

  /** The Constant PNG_RESOLUTION. */
  public static final int PNG_RESOLUTION = 300;

  /** The Constant IMAGE_TYPE. */
  private static final int IMAGE_TYPE = BufferedImage.TYPE_BYTE_BINARY;

  /**
   * Gets the file.
   * 
   * @param filename the filename
   * @return the file
   */
  private static File getFile(String filename) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(filename);
    buffer.append(".");
    buffer.append(IMAGE_FORMAT);
    return new File(buffer.toString());
  }

  /**
   * Extract.
   * 
   * @param page the page
   * @param filename the filename
   * @param resolution the resolution
   * @return the file
   */
  public static File extract(PDPage page, String filename, int resolution) {
    try {
      BufferedImage image = page.convertToImage(IMAGE_TYPE, resolution);
      File f = getFile(filename);
      if (!f.exists() || (f.exists() && OVERWRITE)) {
        ImageIOUtil.writeImage(image, IMAGE_FORMAT, filename, IMAGE_TYPE, resolution);
      }
      return f;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
