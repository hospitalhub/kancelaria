package pl.kalisz.szpital.hr.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Class File2StringUtil.
 */
public class File2StringUtil {

  /**
   * Read from resource file.
   *
   * @param path the path
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String readFromResourceFile(String path) throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream stream = classLoader.getResourceAsStream(path);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      builder.append(line).append("\n");
    }
    bufferedReader.close();
    return builder.toString();
  }

  /**
   * Read from file.
   *
   * @param path the path
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String readFromFile(String path) throws IOException {
    InputStream stream = new FileInputStream(new File(path));
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      builder.append(line).append("\n");
    }
    bufferedReader.close();
    return builder.toString();
  }
  
  /**
   * To file.
   *
   * @param f the f
   * @param s the s
   */
  public static final void toFile(File f, String s) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(f);
      fileOutputStream.write(s.getBytes());
      fileOutputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

}
