package pl.kalisz.szpital.kancelaria.data.filerepo;

import java.io.File;
import java.io.Serializable;

/**
 * The Interface FileLocationStrategy.
 */
public interface FileLocationStrategy extends Serializable {

  /** The Constant FOLDER. */
  String FOLDER = "/volume2/Kancelaria/";
  
  /** The Constant TEMP_FOLDER. */
  String TEMP_FOLDER = FOLDER + "temp/";

  /**
   * Gets the file.
   *
   * @param o the o
   * @return the file
   */
  File getFile(Object o);

}
