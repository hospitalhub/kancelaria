package pl.kalisz.szpital.kancelaria.data.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * The Class Sygnatura.
 */
public class Sygnatura {

  /** The Constant SLASH. */
  private static final String SLASH = "/";

  /** The Constant W_ZW. */
  private static final String W_ZW = "WZw";

  /** The Constant W_PO. */
  private static final String W_PO = "WPo";

  /** The Constant P_ZE. */
  private static final String P_ZE = "PZe";

  /** The Constant P_WE. */
  private static final String P_WE = "PWe";
  // TODO(AM) sygnatura old => ze stringa w kolumnie sygnatura dla pism do 20
  // lutego
  // sygnatura na podstawie id dla pism po 20 lutego
  /** The przychodzacy. */
  private final Boolean przychodzacy;

  /** The wewnetrzny. */
  private final Boolean wewnetrzny;

  /** The polecony. */
  private final Boolean polecony;

  /** The data. */
  private final Date data;

  /** The numer. */
  private final Integer numer;

  /** The Constant ROK_MIES. */
  private static final SimpleDateFormat ROK_MIES = new SimpleDateFormat(Strings.YYYY_MM);

  /**
   * Instantiates a new sygnatura.
   * 
   * @param builder the builder
   */
  public Sygnatura(final SygnaturaBuilder builder) {
    this.przychodzacy = builder.getPrzychodzacy();
    this.wewnetrzny = builder.getWewnetrzny();
    this.polecony = builder.getPolecony();
    this.data = builder.getData();
    this.numer = builder.getNumer();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public final String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(przychodzacy ? (wewnetrzny ? P_WE : P_ZE) : (polecony ? W_PO : W_ZW));
    buffer.append(SLASH);
    buffer.append(ROK_MIES.format(data));
    buffer.append(SLASH);
    buffer.append(numer);
    return buffer.toString();
  }

  /**
   * The Class SygnaturaBuilder.
   */
  public static class SygnaturaBuilder {

    /** The przychodzacy. */
    private final Boolean przychodzacy;

    /** The numer. */
    private final Integer numer;

    /** The wewnetrzny. */
    private Boolean wewnetrzny = false;

    /** The polecony. */
    private Boolean polecony = false;

    /** The data. */
    private Date data = new Date(System.currentTimeMillis());

    /**
     * Instantiates a new sygnatura builder.
     * 
     * @param numer the numer
     * @param przychodzacy the przychodzacy
     */
    public SygnaturaBuilder(final Integer numer, final Boolean przychodzacy) {
      super();
      this.numer = numer;
      this.przychodzacy = przychodzacy;
    }

    /**
     * Instantiates a new sygnatura builder.
     * 
     * @param transaction the transaction
     */
    public SygnaturaBuilder(final Transaction transaction) {
      super();
      this.numer = transaction.getId();
      this.przychodzacy = transaction.getPrzychodzacy();
      this.wewnetrzny = transaction.getWewn();
      this.polecony = transaction.getPolecony();
      this.data = transaction.getData();
    }

    /**
     * Data.
     * 
     * @param date the date
     * @return the sygnatura builder
     */
    public final SygnaturaBuilder data(final Date date) {
      this.data = date;
      return this;
    }

    /**
     * Wewnetrzny.
     * 
     * @param wewnetrzny the wewnetrzny
     * @return the sygnatura builder
     */
    public final SygnaturaBuilder wewnetrzny(final boolean wewnetrzny) {
      this.wewnetrzny = wewnetrzny;
      return this;
    }

    /**
     * Polecony.
     * 
     * @param polecony the polecony
     * @return the sygnatura builder
     */
    public final SygnaturaBuilder polecony(final boolean polecony) {
      this.polecony = polecony;
      return this;
    }

    /**
     * Gets the przychodzacy.
     * 
     * @return the przychodzacy
     */
    public final Boolean getPrzychodzacy() {
      return przychodzacy;
    }

    /**
     * Gets the wewnetrzny.
     * 
     * @return the wewnetrzny
     */
    public final Boolean getWewnetrzny() {
      return wewnetrzny;
    }

    /**
     * Gets the polecony.
     * 
     * @return the polecony
     */
    public final Boolean getPolecony() {
      return polecony;
    }

    /**
     * Gets the data.
     * 
     * @return the data
     */
    public final Date getData() {
      return data;
    }

    /**
     * Gets the numer.
     * 
     * @return the numer
     */
    public final Integer getNumer() {
      return numer;
    }

    /**
     * Builds the.
     * 
     * @return the sygnatura
     */
    public final Sygnatura build() {
      return new Sygnatura(this);
    }

  }

}
