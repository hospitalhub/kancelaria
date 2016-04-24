package pl.kalisz.szpital;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@Test(groups = UITest.LOGIN_GROUP)
public class UILoginTest extends UITest {

	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite(ITestContext context) {
		super.setupBeforeSuite(context);
	}

	@AfterSuite(alwaysRun = true)
	public void setupAfterSuite() {
		super.setupAfterSuite();
	}

	@Test(alwaysRun = true, description = "launch")
	public void launchSite() throws InterruptedException {
		selenium.open("/wsz");
		selenium.setTimeout("0");
		Thread.sleep(2000);
		waitToLoad();
		assertEquals(selenium.getTitle(), "WSZ Sekretariat");
	}

	@Test(description = "Enters valid login data")
	public void login() throws InterruptedException {
		waitToLoad();
		selenium.type("id=user", USERNAME);
		selenium.type("id=pass", "x");
		selenium.click("id=submitb");
		waitToLoad(3);
		assertTrue(selenium.isTextPresent("Nowe zdarzenie"));
	}

	@Test(description = "Enters invalid login data")
	public void loginFail() throws InterruptedException {
		waitToLoad();
		selenium.type("id=user", USERNAME);
		selenium.type("id=pass", "b");
		selenium.click("id=submitb");
		waitToLoad();
		assertTrue(selenium.isTextPresent("Błędny użytkownik lub hasło"));
	}

}
