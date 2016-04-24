package pl.kalisz.szpital.kancelaria.ui.tables;

import org.tepi.filtertable.paged.PagedFilterTable;

import com.vaadin.data.Container;

/**
 * The Class MyPagedFilterTable.
 * 
 * @param <T> the generic type
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class PagedFilterTable30<T extends Container.Indexed & Container.Filterable> extends
    PagedFilterTable {

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.ui.CustomTable#getPageLength()
   */
  @Override
  public int getPageLength() {
    return 30;
  }

}
