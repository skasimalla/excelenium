package com.as.selenium.engine;

import com.as.selenium.api.ActionKeywords;
import com.as.selenium.util.ExcelUtils;
import com.as.selenium.util.Reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class TestEngine {

	// engine constants
	public static final String IE_DRIVER_PATH;
	public static String TEST_DATA_FOLDER;
	public static final String EXCEL_SHEET_STEPS;
	public static final String EXCEL_SHEET_CASES;
	public static final int EXCEL_KEYWORD_COL;
	public static final int EXCEL_DESC_COL;
	public static final int EXCEL_ELEMENT_COL;
	public static final int EXCEL_PARAMETER_COL;
	public static final int EXCEL_TEST_CASE_ID_COL;
	public static final int EXCEL_ENABLED_COL;
	public static InputStream input = null;

	// -----------------------------------------------
	private ActionKeywords actionKeywords;
	private Method[] methods;

	static {
		// load settings from default properties file
		Properties settings = new Properties();

		// set location of test suites folder into constants
		try {
			File file = new File("").getCanonicalFile();
			TEST_DATA_FOLDER = file.getParent() + "/resources/";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IE_DRIVER_PATH = settings.getProperty("IE_DRIVER_PATH");

		EXCEL_SHEET_STEPS = "Test Steps";
		EXCEL_SHEET_CASES = "Test Cases";
		EXCEL_KEYWORD_COL = 4;
		EXCEL_ELEMENT_COL = 3;
		EXCEL_PARAMETER_COL = 5;
		EXCEL_TEST_CASE_ID_COL = 0;
		EXCEL_ENABLED_COL = 2;
		EXCEL_DESC_COL = 2;
	}

	public TestEngine() {
		// load action methods from ActionKeywords class
		actionKeywords = new ActionKeywords();
		methods = actionKeywords.getClass().getMethods();
	}

	// create objects with pageModels and keywordActions
	public int runTestSuite(String excelFileName) throws Exception {
		String actionKeyword = "";
		String pageElement = "";
		String parameter = "";
		String description = "";
		String testCaseId = "";
		String testEnabled = "";
		int testFirstStep;
		int testLastStep;

		// load excel test sutie file
		try {
			ExcelUtils.setExcelFile(excelFileName);

			Path path = Paths.get(excelFileName);
			Reporting.addInfo("Loaded Test Suite file " + path.getFileName().toString());
		} catch (IOException e) {
			Reporting.reportSystemError("Can't find test suite file " + excelFileName);
			return 1;
		}


		// parse test suite excel file
		int totalTestCases = ExcelUtils.getRowCount(EXCEL_SHEET_CASES);
		for (int testCase = 1; testCase < totalTestCases; testCase++) {
			testCaseId = ExcelUtils.getCellData(testCase, EXCEL_TEST_CASE_ID_COL, EXCEL_SHEET_CASES);
			testEnabled = ExcelUtils.getCellData(testCase, EXCEL_ENABLED_COL, EXCEL_SHEET_CASES);

			// execute only enabled test cases (specified on sheet 2 in excel file)
			if (testEnabled.equals("Yes")) {
				testFirstStep = ExcelUtils.getTestCaseStartingRow(testCaseId, EXCEL_TEST_CASE_ID_COL,
						EXCEL_SHEET_STEPS);
				testLastStep = ExcelUtils.getTestStepsRowCount(EXCEL_SHEET_STEPS, testCaseId, testFirstStep);

				// load keywords and elements
				for (int step = testFirstStep; step < testLastStep; step++) {
					actionKeyword = ExcelUtils.getCellData(step, EXCEL_KEYWORD_COL, EXCEL_SHEET_STEPS);
					pageElement = ExcelUtils.getCellData(step, EXCEL_ELEMENT_COL, EXCEL_SHEET_STEPS);
					parameter = ExcelUtils.getCellData(step, EXCEL_PARAMETER_COL, EXCEL_SHEET_STEPS);
					description = ExcelUtils.getCellData(step, EXCEL_DESC_COL, EXCEL_SHEET_STEPS);
					try {
						// execute action
						if (!executeAction(actionKeyword, pageElement, parameter, description)) {
							actionKeywords.quit();
							return 1;
						}
					} catch (Exception e) {
						Reporting.reportSystemError(e.getMessage());
						actionKeywords.quit();
						return 1;
					}
				}
				Reporting.reportTestSuccess(testCaseId);
			}
		}
		return 0;
	}

	private boolean executeAction(String action, String element, String parameter, String description)
			throws Exception {
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(action)) {
				Boolean success = (Boolean) methods[i].invoke(actionKeywords, element, parameter, description);
				if (!success) {
					return false;
				}
				return true;
			}
		}
		throw new Exception("Invalid Action Keyword: " + action);
	}
}
