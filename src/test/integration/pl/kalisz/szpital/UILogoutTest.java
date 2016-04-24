package pl.kalisz.szpital;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class UILogoutTest extends UITest {
	

	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite(ITestContext context) {
		super.setupBeforeSuite(context);
	}
	
	@AfterSuite(alwaysRun = true)
	public void setupAfterSuite() {
		super.setupAfterSuite();
	}

	@Test(description = "Logs out", dependsOnGroups= UITest.NEW_TRANS_GROUP)
	public void logout() {
		selenium.click("//span[text()='" + USERNAME + "']");
		waitToLoad(20);
		selenium.click("//span[text()='" + WYLOGUJ + "']");
		waitToLoad();
		assertEquals(selenium.getTitle(), "bye");
		waitToLoad();
	}
}
