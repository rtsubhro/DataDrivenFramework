package com.datadriven.framework.utils;

/**
 * 
 * This class is not going to be used in the Framework.
 *
 */

public class ReadTestData {

	public static void main(String[] args) {

		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\TestData_Testmanagement.xlsx");

		String sheetName = "Feature 1";

		String testName = "Test One";

		// Find Row Number that contains the Test Name
		int rowNum_Of_TestName = 0;

		while (!readData.getCellData(sheetName, 0, rowNum_Of_TestName).equalsIgnoreCase(testName)) {
			rowNum_Of_TestName++;
		}

		System.out.println("Row Number of Test Name : " + rowNum_Of_TestName);

		int rowNum_Of_Columns_Of_TestData = rowNum_Of_TestName + 1;

		int start_RowNum_Of_Rows_Of_TestData = rowNum_Of_TestName + 2;

		// Find Number of Rows of Test Data
		int numOfRowsOfTestData = 0;
		while (!readData.getCellData(sheetName, 0, start_RowNum_Of_Rows_Of_TestData + numOfRowsOfTestData).equals("")) {
			numOfRowsOfTestData++;
		}

		System.out.println("Number of Rows of Test Data : " + numOfRowsOfTestData);

		// Find Number of Columns of Test Data
		int numOfColumnsOfTestData = 0;
		while (!readData.getCellData(sheetName, numOfColumnsOfTestData, rowNum_Of_Columns_Of_TestData).equals("")) {
			numOfColumnsOfTestData++;
		}
		System.out.println("Number of Columns of Test Data : " + numOfColumnsOfTestData);

		/*Object[][]testData = new Object[numOfRowsOfTestData][numOfColumnsOfTestData];
		int rowNum = 0;
		for (int row = start_RowNum_Of_Rows_Of_TestData; row < start_RowNum_Of_Rows_Of_TestData
				+ numOfRowsOfTestData; row++) {
			for (int column = 0; column < numOfColumnsOfTestData; column++) {
				//System.out.println(readData.getCellData(sheetName, column, row));
				testData[rowNum][column] = readData.getCellData(sheetName, column, row);
			}
			
			rowNum++;
		}
		
		System.out.println(testData);*/
		
		// Retrieve the Test Data
		for (int row = start_RowNum_Of_Rows_Of_TestData; row < start_RowNum_Of_Rows_Of_TestData
				+ numOfRowsOfTestData; row++) {
			for (int column = 0; column < numOfColumnsOfTestData; column++) {
				System.out.println(readData.getCellData(sheetName, column, row));
			}
		}

	}

}
