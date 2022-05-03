package com.datadriven.framework.utils;

import java.util.Hashtable;

/*import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;*/

public class TestDataProvider {

	/*
	 * @Test(dataProvider = "getTestData") public void
	 * testSampleOne(Hashtable<String, String> table) {
	 * System.out.println(table.get("Col2")); }
	 */

	// @DataProvider
	public static Object[][] getTestData(String dataFileName, String sheetName, String testName) {
		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\" + dataFileName);

		/*
		 * String sheetName = "Feature 1";
		 * 
		 * String testName = "Test Two";
		 */

		// Find Row Number that contains the Test Name
		int rowNum_Of_TestName = 0;

		while (!readData.getCellData(sheetName, 0, rowNum_Of_TestName).equalsIgnoreCase(testName)) {
			rowNum_Of_TestName++;
		}

		// System.out.println("Row Number of Test Name : " + rowNum_Of_TestName);

		int rowNum_Of_Columns_Of_TestData = rowNum_Of_TestName + 1;

		int start_RowNum_Of_Rows_Of_TestData = rowNum_Of_TestName + 2;

		// Find Number of Rows of Test Data
		int numOfRowsOfTestData = 0;
		while (!readData.getCellData(sheetName, 0, start_RowNum_Of_Rows_Of_TestData + numOfRowsOfTestData).equals("")) {
			numOfRowsOfTestData++;
		}

		// System.out.println("Number of Rows of Test Data : " + numOfRowsOfTestData);

		// Find Number of Columns of Test Data
		int numOfColumnsOfTestData = 0;
		while (!readData.getCellData(sheetName, numOfColumnsOfTestData, rowNum_Of_Columns_Of_TestData).equals("")) {
			numOfColumnsOfTestData++;
		}
		// System.out.println("Number of Columns of Test Data : " +
		// numOfColumnsOfTestData);

		// Retrieve the Test Data
		// return an object array, where each row has only one column, that is a
		// HashTable
		// and the Hashtable has key value pairs, where key is the column name, and
		// value is the cell value in the excel sheet
		// Object[][] testData = new
		// Object[numOfRowsOfTestData][numOfColumnsOfTestData];
		Object[][] testData = new Object[numOfRowsOfTestData][1];

		Hashtable<String, String> dataTable = null;

		int rowNum = 0;
		for (int row = start_RowNum_Of_Rows_Of_TestData; row < start_RowNum_Of_Rows_Of_TestData
				+ numOfRowsOfTestData; row++) {
			dataTable = new Hashtable<String, String>();
			for (int column = 0; column < numOfColumnsOfTestData; column++) {
				// System.out.println(readData.getCellData(sheetName, column, row));
				// testData[rowNum][column] = readData.getCellData(sheetName, column, row);
				String key = readData.getCellData(sheetName, column, rowNum_Of_Columns_Of_TestData); // key is the
																										// column Name
				String value = readData.getCellData(sheetName, column, row); // value is the cell data

				dataTable.put(key, value);
			}
			testData[rowNum][0] = dataTable;

			rowNum++;
		}

		// System.out.println(testData);

		return testData;

	}

}
