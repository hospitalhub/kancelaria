package pl.kalisz.szpital.kancelaria.data.filerepo;

import java.io.File;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * The Class FilepathLocationStrategy.
 */
@SuppressWarnings("serial")
public class FilepathLocationStrategy implements FileLocationStrategy {

  /* (non-Javadoc)
   * @see pl.kalisz.szpital.kancelaria.data.fileRepo.FileLocationStrategy#getFile(java.lang.Object)
   */
  @Override
  public final File getFile(final Object o) {
    if (o instanceof Transaction) {
      String path = ((Transaction) o).getPlikSciezka();
      if (path != null && !path.equals(Strings.EMPTY_STRING)) {
        return new File(path);
      } else {
        Integer id = ((Transaction) o).getId();
        if (id != null) {
          return new NumerIdFileLocation().getFile(id);
        }
      }
    }
    return null;
  }
  
}
