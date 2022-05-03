package com.datadriven.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentReportManager {

	public static ExtentReports report;

	public static ExtentReports getReportInstance() {

		if (report == null) {
			
			report = new ExtentReports();
			
			String reportNameTimeStamp = DateUtils.getTimeStamp() + ".html";

			ExtentSparkReporter allReporter = new ExtentSparkReporter(
					System.getProperty("user.dir") + "/test-output/extent-reports/" + "AllReports_" + reportNameTimeStamp);

			ExtentSparkReporter failsReporter = new ExtentSparkReporter(
					System.getProperty("user.dir") + "/test-output/extent-reports/" + "FailReports_" + reportNameTimeStamp).filter()
							.statusFilter().as(new Status[] {Status.FAIL}).apply();

			allReporter.viewConfigurer().viewOrder().as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST,
					ViewName.CATEGORY, ViewName.AUTHOR, ViewName.DEVICE, ViewName.EXCEPTION, ViewName.LOG });

			failsReporter.viewConfigurer().viewOrder().as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST,
					ViewName.CATEGORY, ViewName.AUTHOR, ViewName.DEVICE, ViewName.EXCEPTION, ViewName.LOG });

			report.attachReporter(allReporter, failsReporter);

			report.setSystemInfo("OS", "Windows 10");
			report.setSystemInfo("Environment", "UAT");
			report.setSystemInfo("Build Number", "15.67.09");
			report.setSystemInfo("Browser", "Chrome");

			allReporter.config().setDocumentTitle("UAT UI All Automation Results");
			allReporter.config().setReportName("UAT UI All Tests Report");
			allReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

			failsReporter.config().setDocumentTitle("UAT UI Failures-Only Automation Results");
			failsReporter.config().setReportName("UAT UI Failed Tests Report");
			failsReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		}

		return report;
	}

}
