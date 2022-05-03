package com.datadriven.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.datadriven.framework.utils.DateUtils;
import com.datadriven.framework.utils.ExtentReportManager;

public class BaseUI {

	public WebDriver driver;

	public Properties props;

	public ExtentReports report = ExtentReportManager.getReportInstance();

	public ExtentTest logger;

	SoftAssert softAssert = new SoftAssert();

	String chromeDriverExec = System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe";

	String geckoDriverExec = System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\geckodriver.exe";

	String edgeDriverExec = System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\msedgedriver.exe";

	String projectConfigPropertiesPath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\objectrepository\\projectConfig.properties";

	public void waitForPageLoad() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		int i = 0;

		while (i != 180) {

			String pageState = (String) jse.executeScript("return document.readyState;");

			if ("complete".equals(pageState)) {
				break;
			} else {
				waitLoad(1);
				i++;
			}
		}

		waitLoad(2);

		i = 0;

		while (i != 180) {
			boolean jsState = (boolean) jse.executeScript("return window.jQuery != undefined && jQuery.active == 0;");

			if (jsState) {
				break;
			} else {
				waitLoad(1);
				i++;
			}
		}
	}

	public void waitLoad(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadProjectConfigProperties() {
		if (props == null) {
			props = new Properties();
		}

		try {
			props.load(new FileInputStream(projectConfigPropertiesPath));
		} catch (IOException e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}

	}

	public void invokeBrowser(String browserNameKey) {

		String browserName = props.getProperty(browserNameKey);
		try {
			switch (browserName.toLowerCase()) {

			case "chrome":
				System.setProperty("webdriver.chrome.driver", chromeDriverExec);
				driver = new ChromeDriver();
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", geckoDriverExec);
				driver = new FirefoxDriver();
				break;
			default:
				System.setProperty("webdriver.edge.driver", edgeDriverExec);
				driver = new EdgeDriver();
				break;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
	}

	public void openURL(String websiteURLKey) {
		try {
			driver.get(props.getProperty(websiteURLKey));
			reportPass(websiteURLKey + " URL identified and opened successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	public void tearDown() {
		driver.close();
	}

	public void quitBrowser() {
		driver.quit();
	}

	public void enterText(String elementKey, String data) {
		try {
			getWebElement(elementKey).sendKeys(data);
			reportPass(data + " entered successfully in element with locator : " + elementKey);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void elementClick(String elementKey) {
		try {
			getWebElement(elementKey).click();
			reportPass("Element with locator : " + elementKey + " clicked successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public WebElement getWebElement(String elementKey) {
		WebElement webElement = null;

		try {
			if (elementKey.endsWith("_Xpath")) {
				webElement = driver.findElement(By.xpath(props.getProperty(elementKey)));
				logger.log(Status.INFO, "Locator Identified : " + elementKey);
			} else if (elementKey.endsWith("_Id")) {
				webElement = driver.findElement(By.id(props.getProperty(elementKey)));
				logger.log(Status.INFO, "Locator Identified : " + elementKey);
			} else if (elementKey.endsWith("_CSS")) {
				webElement = driver.findElement(By.cssSelector(props.getProperty(elementKey)));
				logger.log(Status.INFO, "Locator Identified : " + elementKey);
			} else if (elementKey.endsWith("_LinkText")) {
				webElement = driver.findElement(By.linkText(props.getProperty(elementKey)));
				logger.log(Status.INFO, "Locator Identified : " + elementKey);
			} else if (elementKey.endsWith("_PartialLinkText")) {
				webElement = driver.findElement(By.partialLinkText(props.getProperty(elementKey)));
				logger.log(Status.INFO, "Locator Identified : " + elementKey);
			} else if (elementKey.endsWith("_Name")) {
				webElement = driver.findElement(By.name(props.getProperty(elementKey)));
				logger.log(Status.INFO, "Locator Identified : " + elementKey);
			} else if (elementKey.endsWith("_Class")) {
				webElement = driver.findElement(By.className(props.getProperty(elementKey)));
				logger.log(Status.INFO, "Locator Identified : " + elementKey);
			} else {
				reportFail("Failing the Test Case, Invalid Locator : " + elementKey);
				Assert.fail("Failing the Test Case, Invalid Locator : " + elementKey);
			}
		} catch (Exception exp) {
			// Fail the test case and report the error
			//reportFail(exp.getMessage());

			//exp.printStackTrace();

			//Assert.fail("Test Case Failed : " + exp.getMessage());
		}
		return webElement;
	}

	/***************** Assertion Functions ********************/
	public void assertTrue(boolean flag) {
		softAssert.assertTrue(flag);
	}

	public void assertFalse(boolean flag) {
		softAssert.assertFalse(flag);
	}

	public void assertEquals(String actual, String expected) {
		softAssert.assertEquals(actual, expected);
	}

	/***************** Verify Element Functions ****************/
	public boolean isElementPresent(String locatorKey) {
		try {
			if (getWebElement(locatorKey) != null && getWebElement(locatorKey).isDisplayed()) {
				reportPass("Web Element with locator key : " + locatorKey + " is Displayed");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	public boolean isElementSelected(String locatorKey) {
		try {
			if (getWebElement(locatorKey) != null && getWebElement(locatorKey).isSelected()) {
				reportPass("Web Element with locator key : " + locatorKey + " is Selected");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	public boolean isElementEnabled(String locatorKey) {
		try {
			if (getWebElement(locatorKey) != null && getWebElement(locatorKey).isEnabled()) {
				reportPass("Web Element with locator key : " + locatorKey + " is Enabled");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	public void verifyPageTitle(String expectedTitle) {
		try {
			String actualTitle = driver.getTitle();
			logger.log(Status.INFO, "Actual Page Title is : " + actualTitle);
			logger.log(Status.INFO, "Expected Page Title is : " + expectedTitle);
			Assert.assertEquals(actualTitle, expectedTitle);

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/*********************
	 * Reporting Functions
	 *************************************/

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	@AfterMethod
	public void afterTest() {
		softAssert.assertAll();
		quitBrowser();
	}

	/************* Capture Screenshot **************/
	public void takeScreenShotOnFailure() {

		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;

		File srcFile = takeScreenshot.getScreenshotAs(OutputType.FILE);

		String destFilePath = System.getProperty("user.dir") + "/test-output/extent-reports/screenshots/"
				+ DateUtils.getTimeStamp() + ".png";
		File destFile = new File(destFilePath);

		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.addScreenCaptureFromPath(destFilePath);

	}

}
