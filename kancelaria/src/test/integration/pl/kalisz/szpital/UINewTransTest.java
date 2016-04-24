package pl.kalisz.szpital;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@Test(dependsOnGroups = UITest.LOGIN_GROUP, groups = UITest.NEW_TRANS_GROUP)
public class UINewTransTest extends UITest {

	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite(ITestContext context) {
		super.setupBeforeSuite(context);
	}

	@AfterSuite(alwaysRun = true)
	public void setupAfterSuite() {
		super.setupAfterSuite();
	}

	@Test(description = "Navigates to the New Post screen")
	public void navigateNewPost() {
		selenium.click("//span[contains(text(),'Nowe zdarzenie')]");
		assertTrue(selenium.isTextPresent("Skopiuj"));
		selenium.click("//span[contains(text(),'Nowe zdarzenie')]/following::span[contains(text(),'Nowe zdarzenie')]");
	}

	@Test(description = "Writes")
	public void writeData() {
		waitToLoad();
		selenium.type("//input[@class='v-filterselect-input']", "DN");
		waitToLoad();
		selenium.click("//span[contains(text(),'Zapisz')]");
		selenium.click("//div[@class='v-window-closebox']");
	}

	@Test(description = "Publishes the post")
	public void save() {
		selenium.click("submitdiv");
		selenium.click("publish");
		waitToLoad();
		assertTrue(selenium.isTextPresent("Post published."));
	}

	@Test(description = "Verifies the post")
	public void verifyBlogPost() {
		selenium.click("//a[contains(text(),'Posts') and contains(@class,'wp-first-item')]");
		waitToLoad();
		assertTrue(selenium.isElementPresent("//a[text()='New Blog Post']"));
	}

}
