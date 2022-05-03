package com.datadriver.framework.test.logintest;

import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.datadriven.framework.base.BaseUI;
import com.datadriven.framework.utils.TestDataProvider;

public class ZohoLoginTest extends BaseUI {

	@Test(dataProvider = "getZohoLogin_TestData")
	public void testZohoLoginTest(Hashtable<String, String> dataTable) {

		logger = report.createTest("Zoho Login Test for : " + dataTable.get("Comments"));

		loadProjectConfigProperties();

		invokeBrowser("browserName");

		openURL("websiteURL");

		elementClick("zohoMainPage_SignIn_Link_Class");

		verifyPageTitle(dataTable.get("Accounts Page Title"));

		enterText("zohoAccountsPage_EmailTextBox_Id", dataTable.get("Email Address"));

		elementClick("zohoAccountsPage_NextBtn_Id");

		if (!isElementPresent("zohoAccountsPage_EmailError_CSS")) {
			if (isElementPresent("zohoAccountsPage_PasswordTextBox_Id")) {
				enterText("zohoAccountsPage_PasswordTextBox_Id", dataTable.get("Password"));
				
				elementClick("zohoAccountsPage_SignInBtn_Id");
				
				if (isElementPresent("zohoAccountsPage_PasswordError_CSS")) {
					logger.log(Status.INFO, "Invalid Password");
					verifyPageTitle(dataTable.get("Accounts Page Title"));
				} else {
					logger.log(Status.INFO, "Valid Password");
					waitLoad(5);
					verifyPageTitle(dataTable.get("Home Page Title"));
				}
			}
		}else {
			logger.log(Status.INFO, "Invalid Email Account");
		}

	}

	@DataProvider
	public Object[][] getZohoLogin_TestData() {
		return TestDataProvider.getTestData("ZohoLogin_TestData_.xlsx", "Login Feature", "Login Test");
	}
	
	@AfterTest
	public void endReport() {
		report.flush();
	}
}
