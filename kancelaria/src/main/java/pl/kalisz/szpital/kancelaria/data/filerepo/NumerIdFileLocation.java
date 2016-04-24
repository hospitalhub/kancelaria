package pl.kalisz.szpital.kancelaria.data.filerepo;

import java.io.File;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * The Class NumerIdFileLocation.
 */
@SuppressWarnings("serial")
public class NumerIdFileLocation implements FileLocationStrategy {

  /** The Constant LOGGER. */
  private static final Logger LOGGER = Logger.getLogger(NumerIdFileLocation.class);

  /** The Constant OSMIOCYFR. */
  private static final DecimalFormat OSMIOCYFR = new DecimalFormat(Strings.ZERO_X8);

  /**
   * File exists.
   * 
   * @param zdarzenie the zdarzenie
   * @return true, if successful
   */
  public final boolean fileExists(final Transaction zdarzenie) {
    try {
      File f = getFile(zdarzenie);
      if (f.exists()) {
        return true;
      }
    } catch (Exception e) {
      LOGGER.trace(e.getMessage());
    }
    return false;
  }

  /**
   * getFile.
   */
  public final File getFile(final Object o) {
    if (o instanceof Integer) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(FOLDER);
      buffer.append(OSMIOCYFR.format(o));
      buffer.append(".pdf");
      return new File(buffer.toString());
    }
    throw new IllegalArgumentException();
  }



}
