/*
 * 
 */
package pl.kalisz.szpital.kancelaria.data.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;

import au.com.bytecode.opencsv.CSVReader;

// import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The Class CSVFileApplication.
 */
public class CSVFileApplication extends UI {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -4481309869461512763L;

  /** The temp file. */
  protected File tempFile;

  /** The table. */
  protected Table table;

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
   */
  @SuppressWarnings("deprecation")
  @Override
  protected final void init(final VaadinRequest request) {
    /* Create and configure the upload component */
    Upload upload = new Upload("Upload CSV File", new Upload.Receiver() {
      private static final long serialVersionUID = -5985208497882317081L;

      @Override
      public OutputStream receiveUpload(final String filename, final String mimeType) {
        try {
          tempFile = File.createTempFile("temp", ".csv");
          return new FileOutputStream(tempFile);
        } catch (IOException e) {
          e.printStackTrace();
          return null;
        }
      }
    });
    upload.addListener(new Upload.FinishedListener() {
      private static final long serialVersionUID = -2143029640427180317L;

      @Override
      public void uploadFinished(final Upload.FinishedEvent finishedEvent) {
        try {
          /* Let's build a container from the CSV File */
          FileReader reader = new FileReader(tempFile);
          IndexedContainer indexedContainer = buildContainerFromCSV(reader);
          reader.close();
          tempFile.delete();

          /* Finally, let's update the table with the container */
          table.setCaption(finishedEvent.getFilename());
          table.setContainerDataSource(indexedContainer);
          table.setVisible(true);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    /* Table to show the contents of the file */
    table = new Table();
    table.setVisible(false);

    /* Main layout */
    VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    layout.setSpacing(true);
    layout.addComponent(table);
    layout.addComponent(upload);

    /* Build the main window */
    Window w = new Window("CSV Upload Demo");
    w.setContent(layout);
    setContent(w);
    // setMainWindow(w);
  }

  /**
   * Builds the container from csv.
   * 
   * @param reader the reader
   * @return the indexed container
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected final IndexedContainer buildContainerFromCSV(final Reader reader) throws IOException {
    IndexedContainer container = new IndexedContainer();
    CSVReader csvReader = new CSVReader(reader);
    String[] columnHeaders = null;
    String[] record;
    while ((record = csvReader.readNext()) != null) {
      if (columnHeaders == null) {
        columnHeaders = record;
        addItemProperties(container, columnHeaders);
      } else {
        addItem(container, columnHeaders, record);
      }
    }
    csvReader.close();
    return container;
  }

  /**
   * Adds the item properties.
   * 
   * @param container the container
   * @param columnHeaders the column headers
   */
  private static void addItemProperties(final IndexedContainer container,
      final String[] columnHeaders) {
    for (String propertyName : columnHeaders) {
      container.addContainerProperty(propertyName, String.class, null);
    }
  }

  /**
   * Adds the item.
   * 
   * @param container the container
   * @param propertyIds the property ids
   * @param fields the fields
   */
  @SuppressWarnings("unchecked")
  private static void addItem(final IndexedContainer container, final String[] propertyIds,
      final String[] fields) {
    if (propertyIds.length != fields.length) {
      throw new IllegalArgumentException(
          "Hmmm - Different number of columns to fields in the record");
    }
    Object itemId = container.addItem();
    Item item = container.getItem(itemId);
    for (int i = 0; i < fields.length; i++) {
      String propertyId = propertyIds[i];
      String field = fields[i];
      item.getItemProperty(propertyId).setValue(field);
    }
  }

}
