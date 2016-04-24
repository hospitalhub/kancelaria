package pl.kalisz.szpital.kancelaria.utils.other;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PDFsGenerator {
  public static void main(String[] args) throws IOException, InterruptedException {

    File file = new File("list.txt");
    FileReader fr = new FileReader(file);
    int i = 1;
    StringBuffer sb = new StringBuffer();
    while ((i = fr.read()) > 0) {
      sb.append((char) i);
    }
    String s = sb.toString();
    int ii = 0;
    int ig = 0;
    for (String l : s.split("\n")) {
      String plik = "/volume2/Kancelaria/" + l.replace("\"", "");
      File f2 = new File(plik);
      if (f2.exists()) {
        ii++;
        System.out.println(plik);
      } else {
        ig++;
        new File(f2.getAbsolutePath().substring(0, f2.getAbsolutePath().lastIndexOf("/"))).mkdirs();
        f2.createNewFile();
      }
    }
    fr.close();
    System.out.println("itnierej:" + ii + " utworzono:" + ig);
  }
}
