package pl.kalisz.szpital.kancelaria.data.pojo;

import java.io.Serializable;
import java.util.Locale;

/**
 * The Class ApplicationSettings.
 */
@SuppressWarnings("serial")
public class ApplicationSettings implements Serializable {

  /** The language. */
  private Locale language;

  /** The theme. */
  private String theme;

  /** The help enabled. */
  private boolean helpEnabled;

  /** The user name. */
  private String userName;

  /**
   * Gets the language.
   * 
   * @return the language
   */
  public Locale getLanguage() {
    return language;
  }

  /**
   * Sets the language.
   * 
   * @param language the new language
   */
  public void setLanguage(Locale language) {
    this.language = language;
  }

  /**
   * Gets the theme.
   * 
   * @return the theme
   */
  public String getTheme() {
    return theme;
  }

  /**
   * Sets the theme.
   * 
   * @param theme the new theme
   */
  public void setTheme(String theme) {
    this.theme = theme;
  }

  /**
   * Checks if is help enabled.
   * 
   * @return true, if is help enabled
   */
  public boolean isHelpEnabled() {
    return helpEnabled;
  }

  /**
   * Sets the help enabled.
   * 
   * @param helpEnabled the new help enabled
   */
  public void setHelpEnabled(boolean helpEnabled) {
    this.helpEnabled = helpEnabled;
  }

  /**
   * Gets the user name.
   * 
   * @return the user name
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Sets the user name.
   * 
   * @param userName the new user name
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

}
