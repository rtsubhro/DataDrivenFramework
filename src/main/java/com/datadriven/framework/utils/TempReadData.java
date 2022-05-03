package com.datadriven.framework.utils;

/**
 * 
 * This class is not going to be used in the Framework.
 *
 */

public class TempReadData {

	public static void main(String[] args) {

		ReadExcelDataFile readDataFile = new ReadExcelDataFile(
				System.getProperty("user.dir") + "/src/test/resources/testData/TestData_Testmanagement.xlsx");

		int numOfRows = readDataFile.getRowCount("SampleData");
		System.out.println("Number of Rows is: " + numOfRows);

		System.out.println(readDataFile.getCellData("SampleData", 1, 2));

		System.out.println(readDataFile.getCellData("SampleData", "Name", 4));

		System.out.println("Number of Columns is: " + readDataFile.getColumnCount("SampleData"));

	}

}
