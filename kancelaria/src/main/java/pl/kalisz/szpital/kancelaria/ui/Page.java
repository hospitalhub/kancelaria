package pl.kalisz.szpital.kancelaria.ui;

import java.io.File;
import java.io.Serializable;

import com.vaadin.server.FileResource;

@SuppressWarnings("serial")
public class Page implements Serializable {
  private final String document;
  private final String path;
  private final FileResource image;
  private final Integer page;

  public Page(String document, Integer page) {
    super();
    this.document = document;
    this.path = getPath(document, page);
    this.image = new FileResource(new File(path));
    this.page = page;
  }

  // static
  public String getPath(String document, Integer page) {
    return String.format("%s_%d.png", document, page);
  }

  public Page getNextPage() {
    File f = new File(getPath(document, page + 1));
    if (f.exists()) {
      return new Page(document, page + 1);
    } else {
      return this;
    }
  }

  public String getDocument() {
    return document;
  }

  public FileResource getImage() {
    return image;
  }

  public Integer getPageNumber() {
    return page;
  }

  public Page getPreviousPage() {
    File f = new File(getPath(document, page - 1));
    if (f.exists()) {
      return new Page(document, page - 1);
    } else {
      return this;
    }
  }

}
