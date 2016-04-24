package pl.kalisz.szpital;

import junit.framework.TestCase;

import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.HttpCommandProcessor;
import com.thoughtworks.selenium.Selenium;

public class UITest extends TestCase {

  // MIN 100 watch 2000
  protected static final String _WAIT_MILLIS = "200";
  protected static Selenium selenium;
  private SeleniumServer server;
  private HttpCommandProcessor proc;
  static final String LOGIN_GROUP = "login";
  static final String NEW_TRANS_GROUP = "newtrans";
  static final String USERNAME = "ax";
  static final String WYLOGUJ = "Wyloguj";

  @BeforeSuite(alwaysRun = true)
  public synchronized void setupBeforeSuite(ITestContext context) {
    System.out.println("BEFORE suite");
    if (selenium != null) {
      return;
    }
    String seleniumHost = context.getCurrentXmlTest().getParameter("selenium.host");
    String seleniumPort = context.getCurrentXmlTest().getParameter("selenium.port");
    String seleniumBrowser = context.getCurrentXmlTest().getParameter("selenium.browser");
    String seleniumUrl = context.getCurrentXmlTest().getParameter("selenium.url");

    RemoteControlConfiguration rcc = new RemoteControlConfiguration();
    rcc.setSingleWindow(true);
    rcc.setPort(Integer.parseInt(seleniumPort));

    try {
      server = new SeleniumServer(false, rcc);
      server.boot();
    } catch (Exception e) {
      throw new IllegalStateException("Can't start selenium server", e);
    }

    proc =
        new HttpCommandProcessor(seleniumHost, Integer.parseInt(seleniumPort), seleniumBrowser,
            seleniumUrl);
    selenium = new DefaultSelenium(proc);
    selenium.start();
  }

  @AfterSuite(alwaysRun = true)
  public synchronized void setupAfterSuite() {
    System.out.println("after suite");
    try {
      selenium.stop();
    } catch (Exception e1) {
      System.err.println(e1.getMessage());
    }
    try {
      if (server != null) {
        server.stop();
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  protected void waitToLoad(int... times) {
    try {
      int time =
          times.length == 1 ? Integer.parseInt(_WAIT_MILLIS) * times[0] : Integer
              .parseInt(_WAIT_MILLIS);
      selenium.waitForPageToLoad("" + time);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

}
