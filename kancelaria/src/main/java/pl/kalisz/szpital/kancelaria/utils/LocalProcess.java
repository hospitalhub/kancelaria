package pl.kalisz.szpital.kancelaria.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * The Class LocalProcess.
 */
public class LocalProcess {

   /** The Constant C. */
   private static final String C = "-c";
  
  /** The Constant BIN_SH. */
  private static final String BIN_SH = "/bin/sh";
  
  /** The Constant UTF_8. */
  private static final String UTF_8 = "UTF-8";

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {

  }

  /**
   * Gets the result.
   *
   * @param command the command
   * @param dir the dir
   * @return the result
   */
  public static String getResult(String command, File dir) {
    try {
      String[] cmdarray = {BIN_SH, C, command};
      Process p = Runtime.getRuntime().exec(cmdarray, null, dir);
      InputStream is = p.getInputStream();
      p.waitFor();
      StringBuffer sb = new StringBuffer();
      byte[] buffer = new byte[1024 * 32];
      while (is.read(buffer) > 0) {
        sb.append(new String(buffer, UTF_8));
      }
      return sb.toString();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
