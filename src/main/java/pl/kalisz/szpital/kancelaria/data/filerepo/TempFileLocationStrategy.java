package pl.kalisz.szpital.kancelaria.data.filerepo;

import java.io.File;
import java.util.Date;

import pl.kalisz.szpital.kancelaria.utils.DateUtils;

/**
 * The Class TempFileLocationStrategy.
 */
@SuppressWarnings("serial")
public class TempFileLocationStrategy implements FileLocationStrategy {

  /*
   * (non-Javadoc)
   * 
   * @see pl.kalisz.szpital.kancelaria.data.fileRepo.FileLocationStrategy#getFile(java.lang.Object)
   */
  @Override
  public File getFile(Object o) {
    StringBuffer filename = new StringBuffer();
    filename.append(TEMP_FOLDER);
    synchronized (this) {
      filename.append(DateUtils.DMYHMSS.format(new Date()));
    }
    filename.append(".pdf");
    return new File(filename.toString());
  }

}
