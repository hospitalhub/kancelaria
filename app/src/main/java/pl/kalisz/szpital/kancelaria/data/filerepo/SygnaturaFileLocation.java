package pl.kalisz.szpital.kancelaria.data.filerepo;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.DateUtils;
import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * The Class SygnaturaFileLocation.
 */
@SuppressWarnings("serial")
public class SygnaturaFileLocation implements FileLocationStrategy {

  /** The Constant logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(SygnaturaFileLocation.class);

  /** The Constant PW_FOLDER. */
  private static final String PW_FOLDER = "Przychodzące wewnętrzne";

  /** The Constant PZ_FOLDER. */
  private static final String PZ_FOLDER = "Przychodzące zewnętrzne";

  /** The Constant WY_FOLDER. */
  private static final String WY_FOLDER = "Wychodzące";

  /** The Constant EE_FOLDER. */
  private static final String EE_FOLDER = "Przychodzace zewnetrzne_Dział finansowy";

  /** The Constant CZTEROCYFR. */
  private static final DecimalFormat CZTEROCYFR = new DecimalFormat(Strings.ZERO_X4);

  /**
   * Gets the dir.
   * 
   * @param finansowy the finansowy
   * @param wewn the wewn
   * @param przych the przych
   * @param d the d
   * @param temp the temp
   * @param create the create
   * @return the dir
   */
  private static File getDir(final boolean finansowy, final boolean wewn, final boolean przych,
      final Date d, final boolean temp, final boolean create) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(FileLocationStrategy.FOLDER);
    if (temp) {
      buffer.append(TEMP_FOLDER);
    } else {
      if (przych) {
        if (wewn) {
          buffer.append(PW_FOLDER);
        } else {
          if (finansowy) {
            buffer.append(EE_FOLDER);
          } else {
            buffer.append(PZ_FOLDER);
          }
        }
      } else {
        buffer.append(WY_FOLDER);
      }
    }
    if (d != null) {
      buffer.append("/");
      buffer.append(DateUtils.D_M_Y.format(d));
    }
    File dir = new File(buffer.toString());
    if (!dir.exists() && create) {
      dir.mkdirs();
    }
    return dir;
  }

  /**
   * File exists.
   * 
   * @param zdarzenie the zdarzenie
   * @return true, if successful
   */
  public final boolean fileExists(final Transaction zdarzenie) {
    try {
      File f = getFile(zdarzenie);
      if (f.exists()) {
        return true;
      }
    } catch (Exception e) {
      LOGGER.trace(e.getMessage());
    }
    return false;
  }

  // TODO(AM) zapisac w rules!!!
  /**
   * getFile.
   */
  public final File getFile(final Object o) {
    if (o instanceof Transaction) {
      Transaction zdarzenie = (Transaction) o;
      String sygnatura = zdarzenie.getSygnatura();
      if (sygnatura == null) {
        return new NumerIdFileLocation().getFile(((Transaction) o).getId());
      }
      boolean przychodzace = zdarzenie.getPrzychodzacy();
      boolean wewn = zdarzenie.getWewn();
      Date d = zdarzenie.getData();
      String typPisma = zdarzenie.getTypPisma().getNazwa();
      boolean finansowy =
          typPisma.contains("FAKTURA") || typPisma.contains("RACHUNEK")
              || typPisma.contains("SALDO");
      String numerSygn = "";
      try {
        numerSygn = sygnatura.substring(sygnatura.lastIndexOf("/") + 1);
      } catch (Exception e) {
        throw new IllegalArgumentException("Błędna Sygnatura dla:" + zdarzenie.getId());
      }
      Integer numerI = Integer.parseInt(numerSygn);
      String numer = numerI.toString();
      if (wewn || !przychodzace) {
        try {
          numer = CZTEROCYFR.format(numerI);
        } catch (Exception e) {
          LOGGER.warn("Numer dok: *" + numer + "*" + e.getMessage());
        }
      }
      return getFile(finansowy, wewn, przychodzace, d, true, numer);
    }
    throw new IllegalArgumentException();
  }

  // TODO(AM) zrobic nowe sygnatury i parsowac na ich podstawie wszystko
  /**
   * Gets the file.
   * 
   * @param finansowy the finansowy
   * @param wewn the wewn
   * @param przych the przych
   * @param d the d
   * @param create the create
   * @param numer the numer
   * @return the file
   */
  @Deprecated
  private static File getFile(final boolean finansowy, final boolean wewn, final boolean przych,
      final Date d, final boolean create, final String numer) {
    File file =
        new File(getDir(finansowy, wewn, przych, d, false, create).getAbsolutePath() + "/" + numer
            + ".pdf");
    return file;
  }



}
