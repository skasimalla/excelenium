package com.as.selenium.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.as.selenium.engine.TestEngine;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Tejas 
 */
public class ExcelUtils {

    private static XSSFSheet excelSheet;
    private static XSSFWorkbook excelBook;
    private static XSSFCell excelCell;

    public static void setExcelFile(String path) throws IOException {
        FileInputStream excelFile = new FileInputStream(path);
        excelBook = new XSSFWorkbook(excelFile);
    }

    public static String getCellData(int row, int col, String sheetName) {
        excelSheet = excelBook.getSheet(sheetName);
        try {
            excelCell = excelSheet.getRow(row).getCell(col);
            String cellData = excelCell.getStringCellValue();
            return cellData;
        } catch (IllegalStateException noStringValue) {
            double cellData = excelCell.getNumericCellValue();
            return "" + cellData;
        } catch (Exception e) {
            return "";
        }
    }

    //get row number of test case
    public static int getTestCaseStartingRow(String testCaseName, int colNum, String sheetName) {
        int i;
        int rowCount = getRowCount(sheetName);
        for (i = 0; i < rowCount; i++) {
            if(getCellData(i, colNum, sheetName).equalsIgnoreCase(testCaseName)) {
                break;
            }
        }
        return i;
    }

    public static int getTestStepsRowCount(String sheetName, String testCaseID, int testCaseStart ) {
        for (int i = testCaseStart; i < getRowCount(sheetName); i++) {

            if(!testCaseID.equals(getCellData(i, TestEngine.EXCEL_TEST_CASE_ID_COL, sheetName))) {
                return i;
            }
        }
        excelSheet = excelBook.getSheet(sheetName);
        int number= excelSheet.getLastRowNum()+1;
        return number;
    }

    public static int getRowCount(String sheetName) {
        excelSheet = excelBook.getSheet(sheetName);
        return excelSheet.getLastRowNum()+1;
    }

}
