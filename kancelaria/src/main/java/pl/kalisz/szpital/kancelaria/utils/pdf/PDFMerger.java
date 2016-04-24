package pl.kalisz.szpital.kancelaria.utils.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

import pl.kalisz.szpital.kancelaria.data.filerepo.ScannedFileLocationStrategy;

/**
 * The Class PDFMerger.
 */
public class PDFMerger {

  /** The Constant fileLocationStrategy. */
  private static final ScannedFileLocationStrategy fileLocationStrategy =
      new ScannedFileLocationStrategy();

  /**
   * Merge.
   *
   * @param files the files
   * @param output the output
   */
  private static final void merge(File[] files, File output) {
    PDFMergerUtility ut = new PDFMergerUtility();
    for (File f : files) {
      ut.addSource(f);
    }
    ut.setDestinationFileName(output.getAbsolutePath());
    try {
      ut.mergeDocuments();
    } catch (COSVisitorException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    deleteFiles(files);
  }

  /**
   * Delete files.
   *
   * @param files the files
   */
  private static void deleteFiles(File[] files) {
    for (File f : files) {
      f.delete();
    }
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    File f0 = new File("00011423.pdf");
    File f1 = new File("00011425.pdf");
    File f2 = new File("00011427.pdf");
    merge(new File[] {f0, f1, f2}, new File("qwe"));
  }

  /**
   * Merger.
   *
   * @param name the name
   * @return the int
   * @throws FileNotFoundException the file not found exception
   */
  public static final int merger(String name) throws FileNotFoundException {
    File[] files = fileLocationStrategy.getScannedFiles(name);
    File output = fileLocationStrategy.getFile(name);
    if (files.length > 1) {
      merge(files, output);
      return files.length;
    } else if (files.length == 1) {
      files[0].renameTo(output);
      return 1;
    } else {
      throw new FileNotFoundException("Brak pliku");
    }
  }
}
