package com.datadriver.framework.test.logintest;

import org.testng.annotations.Test;

import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.Status;
import com.datadriven.framework.base.BaseUI;
import com.datadriven.framework.utils.TestDataProvider;

public class LoginTest extends BaseUI {

	@Test(dataProvider="getTestDataTestOne")
	public void testOne(Hashtable<String , String> dataTable) {
		/*
		 * invokeBrowser(); openWebSite(); enterUserName(); enterPassword();
		 */

		logger = report.createTest("LoginTest : Test One : Enter UserName and Password in Rediff for : " + dataTable.get("Name"));

		// logger.log(Status.INFO, "Loading Project Config Properties");
		loadProjectConfigProperties();
		// logger.log(Status.INFO, "Initializing the Browser");
		invokeBrowser("browserName");
		// logger.log(Status.INFO, "Opening the Website");
		openURL("websiteURL");
		// elementClick("signInLink_Xpath");
		// takeScreenShotOnFailure();
		// logger.log(Status.INFO, "Clicking Sign In Link on Home Page");
		elementClick("signInLink_CSS");
		// logger.log(Status.INFO, "Entering User Name on Sign In Page");
		enterText("usernameTextBox_Id", dataTable.get("UserName"));
		// logger.log(Status.INFO, "Entering Password on Sign In Page");
		enterText("passwordTextBox_CSS", dataTable.get("Password"));
		// takeScreenShotOnFailure();
		// logger.log(Status.INFO, "Clicking Sign In Button on Sign In Page");
		elementClick("signInBtn_Name");
		// logger.log(Status.INFO, "Shutting down the Browser");

		// takeScreenShotOnFailure();
		// logger.log(Status.INFO, "Took the Screenshot");
		// quitBrowser();
		// logger.log(Status.PASS, "LoginTest : Test One executed successfully");

	}
	
	@DataProvider
	public Object[][] getTestDataTestOne(){
		return TestDataProvider.getTestData("TestData_Testmanagement.xlsx", "Login Test", "Test One");
	}

	@Test(dataProvider="getTestDataTestTwo")
	public void testTwo(Hashtable<String, String> dataTable) {
		logger = report.createTest("LoginTest: Test Two : Login to Money Portfolio in Rediff for : " + dataTable.get("Name"));
		
		loadProjectConfigProperties();
		invokeBrowser("browserName");
		openURL("websiteURL");
		
		elementClick("moneyPageLink_Xpath");
		
		elementClick("moneySignInLink_Xpath");
		
		enterText("moneyEmailTextBox_Id", dataTable.get("UserName"));
		
		enterText("moneyPasswordTextBox_Xpath", dataTable.get("Password"));
		
		elementClick("moneySignInBtn_Id");
		
		isElementPresent("createPortfolioBtn_Xpath");
		
	}
	
	@DataProvider
	public Object[][] getTestDataTestTwo(){
		return TestDataProvider.getTestData("TestData_Testmanagement.xlsx", "Login Test", "Test Two");
	}

	@AfterTest
	public void endReport() {
		report.flush();
	}

	// @Test
	public void testThree() {

	}
}
