package pl.kalisz.szpital.kancelaria.data.filerepo;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Date;

import pl.kalisz.szpital.kancelaria.utils.DateUtils;

/**
 * The Class ScannedFileLocationStrategy.
 */
@SuppressWarnings("serial")
public class ScannedFileLocationStrategy implements FileLocationStrategy {

  /** The Constant PDF. */
  private static final String PDF = ".pdf";

  /**
   * Gets the scanned filename.
   *
   * @param name the name
   * @return the scanned filename
   */
  private String getScannedFilename(final String name) {
    StringBuffer filename = new StringBuffer();
    filename.append(name);
    synchronized (this) {
      filename.append(DateUtils.D_M_Y.format(new Date()));
    }
    filename.append(PDF);
    return filename.toString();
  }

  /**
   * Gets the scanned file regex.
   *
   * @param name the name
   * @return the scanned file regex
   */
  private String getScannedFileRegex(final String name) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(name);
    buffer.append("[0-9_]{0,5}");
    buffer.append(PDF);
    return buffer.toString();
  }


  /**
   * Gets the scanned files.
   *
   * @param name the name
   * @return the scanned files
   */
  public final File[] getScannedFiles(final String name) {

    final String regex = getScannedFileRegex(name);
    File[] files = new File(TEMP_FOLDER + name).listFiles(new FileFilter() {

      @Override
      public boolean accept(final File pathname) {
        return pathname.getName().matches(regex);
      }
    });
    Arrays.sort(files);
    return files;
  }

  /* (non-Javadoc)
   * @see pl.kalisz.szpital.kancelaria.data.fileRepo.FileLocationStrategy#getFile(java.lang.Object)
   */
  @Override
  public final File getFile(final Object o) {
    if (o instanceof String) {
      String name = (String) o;
      StringBuffer buffer = new StringBuffer();
      buffer.append(TEMP_FOLDER);
      buffer.append(name);
      buffer.append("/");
      buffer.append(getScannedFilename(name));
      return new File(buffer.toString());
    }
    return null;
  }
}
