package pl.kalisz.szpital.db.kancelaria.enums;

import java.util.ArrayList;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;

import pl.kalisz.szpital.db.kancelaria.Transaction;

/**
 * The Enum TypWiadomosci.
 */
public enum TypWiadomosci {

  /** The PRZYCHOD zą c e_ zew nę trzne. */
  PRZYCHODZĄCE_ZEWNĘTRZNE("Przychodzące zewnętrzne", "icon-zewn", false, false, true),

  /** The PRZYCHOD zą c e_ wew nę trzne. */
  PRZYCHODZĄCE_WEWNĘTRZNE("Przychodzące wewnętrzne", "icon-wewn", false, true, true),

  /** The WYCHOD zą c e_ polecone. */
  WYCHODZĄCE_POLECONE("Wychodzące polecone", "icon-polecony", true, false, false),

  /** The WYCHOD zą ce. */
  WYCHODZĄCE("Wychodzące zwykłe", "icon-wychodzace", false, false, false),

  /** The archiwum. */
  ARCHIWUM("Archiwum", "icon-wychodzace", true);

  /** The polecony. */
  private Boolean polecony;

  /** The wewnetrzny. */
  private Boolean wewnetrzny;

  /** The przychodzacy. */
  private Boolean przychodzacy;

  /** The archiwalny. */
  private Boolean archiwalny;

  /** The nazwa. */
  private String nazwa;

  /** The styl. */
  private String styl;

  /**
   * Checks if is polecony.
   * 
   * @return the boolean
   */
  public Boolean isPolecony() {
    return polecony;
  }

  /**
   * Gets the nazwa.
   * 
   * @return the nazwa
   */
  public String getNazwa() {
    return nazwa;
  }

  /**
   * Gets the styl.
   * 
   * @return the styl
   */
  public String getStyl() {
    return styl;
  }

  /**
   * Checks if is wewnetrzny.
   * 
   * @return the boolean
   */
  public Boolean isWewnetrzny() {
    return wewnetrzny;
  }

  /**
   * Checks if is przychodzacy.
   * 
   * @return the boolean
   */
  public Boolean isPrzychodzacy() {
    return przychodzacy;
  }

  /**
   * Checks if is archiwalny.
   * 
   * @return the boolean
   */
  public Boolean isArchiwalny() {
    return archiwalny;
  }

  /**
   * Instantiates a new typ wiadomosci.
   * 
   * @param tytul the tytul
   * @param styl the styl
   * @param archiwalny the archiwalny
   */
  private TypWiadomosci(final String tytul, final String styl, final boolean archiwalny) {
    this.nazwa = tytul;
    this.styl = styl;
    this.archiwalny = archiwalny;
  }

  /**
   * Instantiates a new typ wiadomosci.
   * 
   * @param tytul the tytul
   * @param styl the styl
   * @param polecony the polecony
   * @param wewnetrzny the wewnetrzny
   * @param przychodzacy the przychodzacy
   */
  TypWiadomosci(final String tytul, final String styl, final boolean polecony,
      final boolean wewnetrzny, final boolean przychodzacy) {
    this.nazwa = tytul;
    this.styl = styl;
    this.polecony = polecony;
    this.wewnetrzny = wewnetrzny;
    this.przychodzacy = przychodzacy;
    this.archiwalny = false;
  }

  /**
   * Gets the by tytul.
   * 
   * @param tytul the tytul
   * @return the by tytul
   */
  public static TypWiadomosci getByTytul(final String tytul) {
    for (TypWiadomosci t : values()) {
      if (t.nazwa.equals(tytul)) {
        return t;
      }
    }
    return null;
  }

  /**
   * Gets the by values.
   * 
   * @param polecony the polecony
   * @param wewnetrzny the wewnetrzny
   * @param przychodzacy the przychodzacy
   * @return the by values
   */
  public static TypWiadomosci getByValues(final Boolean polecony, final Boolean wewnetrzny,
      final Boolean przychodzacy) {
    for (TypWiadomosci t : values()) {
      if (t.polecony == polecony && t.wewnetrzny == wewnetrzny && t.przychodzacy == przychodzacy
          && t.archiwalny == false) {
        return t;
      }
    }
    return null;
  }

  /**
   * Gets the filter.
   * 
   * @return the filter
   */
  public Filter getFilter() {
    ArrayList<Filter> filters = new ArrayList<Filter>();
    filters.add(getFilter(Transaction.PRZYCHODZACY_STR, isPrzychodzacy()));
    filters.add(getFilter(Transaction.WEWN_STR, isWewnetrzny()));
    filters.add(getFilter(Transaction.POLECONY_STR, isPolecony()));
    filters.add(getFilter(Transaction.ARCHIVE_STR, isArchiwalny()));
    while (filters.contains(null)) {
      filters.remove(null);
    }
    return new And(filters.toArray(new Filter[] {}));
  }

  /**
   * Gets the filter.
   * 
   * @param filterString the filter string
   * @param value the value
   * @return the filter
   */
  private Filter getFilter(final String filterString, final Boolean value) {
    if (value != null) {
      return new Compare.Equal(filterString, value);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return nazwa;
  }
}
