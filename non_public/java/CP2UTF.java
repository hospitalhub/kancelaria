package pl.kalisz.szpital.kancelaria.utils.other;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnmappableCharacterException;
import java.util.Arrays;

public class CP2UTF {
  private final static String[] enc = {"IBM852", "IBM855", "IBM857", "IBM862", "IBM866",
      "ISO-8859-1", "ISO-8859-2", "ISO-8859-4", "ISO-8859-5", "ISO-8859-7", "ISO-8859-9",
      "ISO-8859-13", "ISO-8859-15", "KOI8-R", "KOI8-U", "US-ASCII", "UTF-8", "UTF-16", "UTF-16BE",
      "UTF-16LE", "UTF-32", "UTF-32BE", "UTF-32LE", "x-UTF-32BE-BOM", "x-UTF-32LE-BOM",
      "windows-1250", "windows-1251", "windows-1252", "windows-1253", "windows-1254",
      "windows-1257", "x-IBM737", "x-IBM874", "x-UTF-16LE-BOM", "Cp858", "Cp437", "Cp775", "Cp850",
      "Cp852", "Cp855", "Cp857", "Cp862", "Cp866", "ISO8859_1", "ISO8859_2", "ISO8859_4",
      "ISO8859_5", "ISO8859_7", "ISO8859_9", "ISO8859_13", "ISO8859_15", "KOI8_R", "KOI8_U",
      "ASCII", "UTF8", "UTF-16", "UnicodeBigUnmarked", "UnicodeLittleUnmarked", "UTF_32",
      "UTF_32BE", "UTF_32LE", "UTF_32BE_BOM", "UTF_32LE_BOM", "Cp1250", "Cp1251", "Cp1252",
      "Cp1253", "Cp1254", "Cp1257", "UnicodeBig", "Cp737", "Cp874", "UnicodeLittle", "UTF8"};


  public static String getString(String txt) {
    String s = null;
    Charset charset = Charset.forName("UTF-8");
    CharsetDecoder decoder = charset.newDecoder();
    CharsetEncoder encoder = charset.newEncoder();
    ByteBuffer bbuf = null;
    try {
      for (String encoding : enc) {
        try {
          charset = Charset.forName(encoding);
          encoder = charset.newEncoder();
          bbuf = encoder.encode(CharBuffer.wrap(txt));
          System.out.println(encoding + " : " + new String(bbuf.array()));
        } catch (UnmappableCharacterException e) {
          System.err.println(e.getMessage() + encoding);
        } catch (IllegalCharsetNameException e) {
          System.err.println(e.getMessage() + encoding);
        }
      }
      CharBuffer cbuf = decoder.decode(bbuf);
      System.out.println(Arrays.toString(cbuf.array()));
      s = cbuf.toString();
    } catch (CharacterCodingException e) {
      e.printStackTrace();
    }
    return s;
  }

  public static void main(String[] args) {
    System.out.println(Dict.fix("głwa owo"));
  }

}
