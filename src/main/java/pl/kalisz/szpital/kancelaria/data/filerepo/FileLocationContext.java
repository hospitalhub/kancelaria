package pl.kalisz.szpital.kancelaria.data.filerepo;

import java.io.File;
import java.io.Serializable;

/**
 * The Class FileLocationContext.
 */
@SuppressWarnings("serial")
public class FileLocationContext implements Serializable {

  /** The file location strategy. */
  private FileLocationStrategy fileLocationStrategy;

  /**
   * Sets the file location strategy.
   *
   * @param fileLocationStrategy the new file location strategy
   */
  public final void setFileLocationStrategy(final FileLocationStrategy fileLocationStrategy) {
    this.fileLocationStrategy = fileLocationStrategy;
  }

  /**
   * Exists.
   *
   * @param o the o
   * @return true, if successful
   */
  public final boolean exists(final Object o) {
    File f = fileLocationStrategy.getFile(o);
    if (f != null) {
      return f.exists();
    }
    return false;
  }

  /**
   * Gets the file.
   *
   * @param o the o
   * @return the file
   */
  public final File getFile(final Object o) {
    return fileLocationStrategy.getFile(o);
  }

}
