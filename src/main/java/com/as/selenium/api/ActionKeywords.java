package com.as.selenium.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.as.selenium.api.SupportedWebDrivers;
import com.as.selenium.engine.TestEngine;
import com.as.selenium.util.ExcelUtils;
import com.as.selenium.util.JQuery;
import com.as.selenium.util.Reporting;
import com.as.selenium.util.WebDriverFactory;

/**
 * Created by Tejas
 */

public class ActionKeywords {

	Logger logger = Logger.getLogger("ActionKeywords");

	// public static RemoteWebDriver rdriver;
	public static WebDriver driver;
	private Properties pageElements;
	String AppName;
	long startTime;
	long endTime;
	long totalTime;
	private String parentWindow;
	private static String pageLoadTime;
	private static String homePageLoadTime;

	// load page elements locators (declared on page model file).
	public void setPageElements(Properties pageElements) {
		this.pageElements = pageElements;
	}

	public static String getPageLoadTime() {
		return pageLoadTime;
	}

	public static void setPageLoadTime(String pageLoadTime) {
		ActionKeywords.pageLoadTime = pageLoadTime;
	}

	public static String getHomePageLoadTime() {
		return homePageLoadTime;
	}

	public static void setHomePageLoadTime(String homePageLoadTime) {
		ActionKeywords.homePageLoadTime = homePageLoadTime;
	}

	public boolean openBrowser(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Creating webdriver " + parameter);
			logger.info("Creating webdriver " + parameter);
			if (parameter.equals("Chrome")) {
				driver = WebDriverFactory.createDriver(SupportedWebDrivers.CHROME);
				return true;
			} else if (parameter.equals("HtmlUnit")) {
				driver = WebDriverFactory.createDriver(SupportedWebDrivers.HTMLUNIT);
				return true;
			}
			throw new Exception("Invalid driver");
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to create webdriver " + parameter + "\n" + e.getMessage());
			logger.info("Failed to create webdriver " + parameter + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean navigate(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Navigating to: " + parameter);
			logger.info("Navigating to: " + parameter);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			startTime = System.currentTimeMillis();
			driver.get(parameter);
			// new
			// WebDriverWait(driver,30).until(ExpectedConditions.presenceOfElementLocated(By.id("Calculate")));
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			//
			setHomePageLoadTime(String.valueOf(totalTime));
			System.out.println("Total Page Load Time: " + totalTime + "milliseconds");
			Reporting.addInfo("Total Page Load Time to load search  URL: " + totalTime + "milliseconds");
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to navigate to " + parameter + "\n" + e.getMessage());
			logger.info("Failed to navigate to " + parameter + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean navigate2(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Navigating to: " + parameter);
			logger.info("Navigating to: " + parameter);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			startTime = System.currentTimeMillis();
			driver.get(parameter);
			// new
			// WebDriverWait(driver,30).until(ExpectedConditions.presenceOfElementLocated(By.id("Calculate")));
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			//
			// setHomePageLoadTime(String.valueOf(totalTime));
			System.out.println("Total Page Load Time: " + totalTime + "milliseconds");
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to navigate to " + parameter + "\n" + e.getMessage());
			logger.info("Failed to navigate to " + parameter + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean window(String element, String parameter, String description) throws Exception {
		try {
			setParentWindow(driver.getWindowHandle());
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to set window\n" + e.getMessage());
			logger.info("Failed to set window\n" + e.getMessage());

			return false;
		}
	}

	public boolean clickButton(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo(description);
			logger.info("starting");
			startTime = System.currentTimeMillis();
			driver.findElement(By.xpath(element)).click();
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			System.out.println("Total Page Load Time after click: " + totalTime + "milliseconds");
			Reporting.addRespInfo(description + "= " + totalTime + " milliseconds");
			logger.info(description + " = " + totalTime + " milliseconds");
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\n" + e.getMessage());
			logger.info("Failed to click on " + parameter + "\n" + e.getMessage());
			return false;
		}
	}

	public boolean click(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo(description);
			driver.findElement(By.xpath(element)).click();
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\n" + e.getMessage());
			logger.info("Failed to click on " + parameter + "\n" + e.getMessage());
			return false;
		}
	}

	public boolean pageLogin(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Click on " + element);
			logger.info("Click on " + element);
			startTime = System.currentTimeMillis();
			driver.findElement(By.xpath(element)).click();
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			setPageLoadTime(String.valueOf(totalTime));
			System.out.println("Total Page Load Time after click: " + totalTime + "milliseconds");
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\n" + e.getMessage());
			logger.info("Failed to click on " + element + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean clickValue(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Click on " + element);
			logger.info("Click on " + element);
			startTime = System.currentTimeMillis();
			driver.findElement(By.xpath(element)).click();
			Select s = new Select(driver.findElement(By.xpath(element)));
			s.selectByValue(parameter);
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\n" + e.getMessage());
			logger.info("Failed to click on " + element + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean checkResponseTime(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Checking " + parameter);
			logger.info("Checking " + parameter);
			String[] param = parameter.split("\\.");
			Long time = Long.parseLong(param[0]);
			if (totalTime > time) {

			}
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to click on " + element + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean clickCheckBox(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Click on checkbox " + element);
			logger.info("Click on checkbox " + element);

			driver.findElement(By.xpath(element)).click();
			// long endTime = System.currentTimeMillis();
			// long totalTime = endTime - startTime;
			// System.out.println("Total Page Load Time after click: " + totalTime +
			// "milliseconds");
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\n" + e.getMessage());
			logger.info("Failed to click on checkBox " + element + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean clickTextLink(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Click on link " + parameter);
			logger.info("Click on link " + parameter);
			driver.findElement(By.linkText(parameter)).click();
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\n" + e.getMessage());
			logger.info("Failed to click on text link " + parameter + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean clickText(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Click on text " + element);
			logger.info("Click on text " + element);
			driver.findElement(By.xpath(element)).click();
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\'" + "\n" + e.getMessage());
			logger.info("Failed to click on text \'" + element + "\'" + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean mouseOverMenu(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Click on text " + element);
			logger.info("Click on text " + element);
			WebElement menu = driver.findElement(By.linkText(parameter));
			Actions builder = new Actions(driver);
			builder.moveToElement(menu).build().perform();
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element))).click();
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to click on text \'" + element + "\'" + "\n" + e.getMessage());
			logger.info("Failed to click on text \'" + element + "\'" + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean inputText(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Input text \'" + parameter + "\' into " + element);
			logger.info("Input text \'" + parameter + "\' into " + element);
			driver.findElement(By.xpath(element)).sendKeys(parameter);
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\' on " + element + "\n" + e.getMessage());
			logger.info("Failed to input text \'" + parameter + "\' on " + element + "\n" + e.getMessage());
			return false;
		}
	}

	public boolean inputNumber(String element, String parameter, String description) throws Exception {
		try {

			Reporting.addInfo("Input number \'" + parameter + "\' into " + element);
			logger.info("Input num  \'" + parameter + "\' into " + element);
			parameter = parameter.replaceAll("[^\\d.]", "");
			driver.findElement(By.xpath(element)).sendKeys(String.valueOf(parameter));
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\' on " + element + "\n" + e.getMessage());
			logger.info("Failed to input text \'" + parameter + "\' on " + element + "\n" + e.getMessage());

			return false;
		}
	}

	public boolean inputEncryptedText(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo(description + " : " + parameter);
			logger.info(description + " : " + parameter);
			driver.findElement(By.xpath(element)).sendKeys(parameter);
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + parameter + "\n" + e.getMessage());
			logger.info("Failed at " + description + parameter + "\n" + e.getMessage());
			return false;
		}
	}

	public boolean waitFor(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Wait for " + parameter);
			logger.info("Wait for " + parameter);
			String[] time = parameter.split("\\.");
			Thread.sleep(Long.parseLong(time[0]));
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\n" + e.getMessage());
			logger.info("Failed to wait for " + parameter + "\n" + e.getMessage());

			return false;
		}
	}

	// this action will wait until there's no asynchronous request running on the
	// background;
	public boolean waitForAjax(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Waiting for Ajax");
			JQuery.injectjQueryIfNeeded(driver);

			new WebDriverWait(driver, 15).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					JavascriptExecutor js = (JavascriptExecutor) d;
					return (Boolean) js.executeScript("return jQuery.active == 0");
				}
			});
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Error waiting for ajax \n" + e.getMessage());

			return false;
		}
	}

	// this action will measure the time it takes for the "element" to load. It will
	// fail if it takes longer than the "parameter";
	public boolean checkElementLoadingTime(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Checking '" + element + "' loading time is less than " + parameter);
			logger.info("Checking '" + element + "' loading time is less than " + parameter);
			// get start time
			long startTime = System.currentTimeMillis();

			// wait for the element to appear (20 seconds timeout)
			new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(element)));

			// get the end time
			long endTime = System.currentTimeMillis();

			// measure elapsed time
			long elapsedTime = endTime - startTime;

			// evaluate
			if (elapsedTime < Double.parseDouble(parameter)) {
				Reporting.addInfo("Check OK - loading time: " + elapsedTime + " milliseconds.");
				return true;
			} else {
				Reporting.testStepFailed("Failed to load element on time, it took " + elapsedTime + " milliseconds.");
				return false;
			}
		} catch (Exception e) {
			Reporting.testStepFailed("Error checking '" + element + "' loading time.  \n" + e.getMessage());
			logger.info("Error checking '" + element + "' loading time.  \n" + e.getMessage());

			return false;
		}
	}

	public boolean checkTextPresent(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Check for text: " + parameter);
			logger.info("Check for text: " + parameter);
			if (driver.getPageSource().contains(parameter)) {
				return true;
			} else {
				throw new Exception("Text not found");
			}
		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\n" + e.getMessage());
			logger.info("Failed to locate text " + parameter + "\n" + e.getMessage());

			// NotificationService.sendEmail("Failed to locate text " +parameter + "suring
			// healthcheck of " +ExcelUtils.getCellData(1, 0, "Test Cases"));
			logger.info("Failed to locate text " + parameter + "\n" + e);
			// logupdate("Failed to locate text " + parameter + "\n" + e, "error");

			return false;
		}
	}

	// added by Tejas
	public boolean checkTextAvailability(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Check for text: " + parameter);
			logger.info("Check for text: " + parameter);
			String tst = driver.findElement(By.xpath(element)).getText();
			System.out.println(tst);
			if (tst.equals(parameter)) {

				return true;
			} else {

				throw new Exception("Text not found");
			}
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to locate text " + parameter + "\n" + e.getMessage());
			logger.info("Failed to locate text " + parameter + "\n" + e.getMessage());

			// NotificationService.sendEmail("Failed to locate text " +parameter + "suring
			// healthcheck of " +ExcelUtils.getCellData(1, 0, "Test Cases"));
			logger.info("Failed to locate text " + parameter + "\n" + e);
			// logupdate("Failed to locate text " + parameter + "\n" + e, "error");

			return false;
		}
	}

	public boolean getText(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo(description);
			logger.info(description);
			String tst = driver.findElement(By.xpath(element)).getText();
			System.out.println(tst);
			Reporting.addInfo(description + "= " + tst);
			return true;

		} catch (Exception e) {
			Reporting.testStepFailed("Failed during " + description + "\n" + e.getMessage());
			logger.info("Failed to locate text " + parameter + "\n" + e.getMessage());
			return false;
		}
	}

	public boolean selectFromList(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Select from list: " + element + " -> \'" + parameter + "\'");
			logger.info("Select from list: " + element + " -> \'" + parameter + "\'");
			Select list = new Select(driver.findElement(By.xpath(element)));
			list.deselectAll();
			list.selectByVisibleText(parameter);
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to locate " + parameter + " on list " + element + "\n" + e.getMessage());
			logger.info("Failed to locate " + parameter + " on list " + element + "\n" + e.getMessage());
			return false;
		}
	}

	public boolean selectFromListV(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Select from list: " + element + " -> \'" + parameter + "\'");
			logger.info("Select from list: " + element + " -> \'" + parameter + "\'");
			Select list = new Select(driver.findElement(By.xpath(element)));
			list.selectByValue(parameter);
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to locate " + parameter + " on list " + element + "\n" + e.getMessage());
			logger.info("Failed to locate " + parameter + " on list " + element + "\n" + e.getMessage());
			return false;
		}
	}

	// takes a screenshot of the current browser window. The parameter should be the
	// path + filename (ex: "c:\path\screenshot.png")
	public boolean takeScreenshot(String element, String parameter, String description) throws Exception {
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(parameter));
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to take screenshot  \n" + e.getMessage());
			logger.info("Failed to take screenshot  \n" + e.getMessage());
			return false;
		}
	}

	public boolean switchWindow(String element, String parameter, String description) throws Exception {
		try {
			// Store the current window handle
			// Perform the click operation that opens new window
			// Switch to new window opened

			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}

			// Perform the actions on new window
			// Close the new window, if that window no more required

			return true;

		} catch (Exception e) {
			Reporting.testStepFailed("Failed to switch widow  \n" + e.getMessage());
			logger.info("Failed to switch widow  \n" + e.getMessage());
			return false;
		}
	}

	public boolean switchiFrame(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Switching to iFrame: " + parameter);
			logger.info("Switching to iFrame: " + parameter);
			driver.switchTo().frame(pageElements.getProperty(element));
			System.out.println("Passed");
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to switch iframe \n" + e.getMessage());
			logger.info("Failed to switch widow  \n" + e.getMessage());
			return false;

		}
	}

	public boolean switchiFrameId(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Switching to iFrame: " + parameter);
			logger.info("Switching to iFrame: " + parameter);
			WebElement frame = driver.findElement(By.cssSelector(pageElements.getProperty(element)));
			driver.switchTo().frame(frame);
			System.out.println("Passed");
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to switch iframe \n" + e.getMessage());
			logger.info("Failed to switch widow  \n" + e.getMessage());
			return false;
		}

	}

	public boolean switchiFrameX(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Switching to iFrame: " + parameter);
			logger.info("Switching to iFrame: " + parameter);
			WebElement frame = driver.findElement(By.xpath(element));
			driver.switchTo().frame(frame);
			System.out.println("Passed");
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to switch iframe \n" + e.getMessage());
			logger.info("Failed to switch widow  \n" + e.getMessage());
			return false;
		}

	}

	public boolean switchToDefFrame(String element, String parameter, String description) throws IOException {
		Reporting.addInfo("Switching to default Frame: " + parameter);
		logger.info("Switching to default Frame: " + parameter);
		driver.switchTo().defaultContent();
		return true;
	}

	public boolean acceptAlert(String element, String parameter, String description) throws Exception {
		try {

			driver.switchTo().alert().accept();
			return true;

		} catch (Exception e) {
			Reporting.testStepFailed("Failed to accept on Alert  \n" + e.getMessage());
			logger.info("Failed to switch widow  \n" + e.getMessage());

			return false;
		}
	}

	public boolean cancelAlert(String element, String parameter, String description) throws Exception {
		try {

			driver.switchTo().alert().dismiss();
			return true;

		} catch (Exception e) {
			Reporting.testStepFailed("Failed to accept on Alert  \n" + e.getMessage());
			logger.info("Failed to switch widow  \n" + e.getMessage());

			return false;
		}
	}

	public boolean clickJavascriptLink(String element, String parameter, String description) {
		try {
			Reporting.addInfo("Click on javascript link " + parameter);
			logger.info("Click on javascript link " + parameter);

			WebElement we = driver.findElement(By.xpath(element));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", we);
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to click on javascript link " + parameter + "\n" + e.getMessage());
			logger.info("Failed to click on javascript link " + parameter + "\n" + e.getMessage());
			return false;
		}
	}

	public boolean switchBackWindow(String element, String parameter, String description) throws Exception {
		try {
			driver.close();
			driver.switchTo().window(getParentWindow());

			return true;

		} catch (Exception e) {
			Reporting.testStepFailed("Failed to switch back widow  \n" + e.getMessage());
			return false;
		}
	}

	public boolean switchPopWindow(String element, String parameter, String description) throws Exception {
		try {

			driver.switchTo().window(getParentWindow());

			return true;

		} catch (Exception e) {
			Reporting.testStepFailed("Failed to switch back widow  \n" + e.getMessage());

			return false;
		}
	}

	public boolean switchWindowIE(String element, String parameter, String description) throws Exception {
		try {
			String subWindowHandler = null;
			Set<String> handles = driver.getWindowHandles(); // get all window handles
			Iterator<String> iterator = handles.iterator();
			while (iterator.hasNext()) {
				subWindowHandler = iterator.next();
			}
			driver.switchTo().window(subWindowHandler);
			return true;

		} catch (Exception e) {
			Reporting.testStepFailed("Failed to switch back widow  \n" + e.getMessage());

			return false;
		}
	}

	public boolean navigateBack(String element, String parameter, String description) throws Exception {
		try {

			driver.navigate().back();

			return true;

		} catch (Exception e) {
			Reporting.testStepFailed("Failed to switch widow  \n" + e.getMessage());

			return false;
		}
	}

	public boolean sendKeys(String element, String parameter, String description) throws Exception {
		try {
			System.out.println("sending Keys");
			Actions builder = new Actions(driver);
			builder.sendKeys(parameter);
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to switch widow  \n" + e.getMessage());

			return false;
		}
	}

	public boolean closeBrowser(String element, String parameter, String description) throws Exception {
		try {
			Reporting.addInfo("Close browser");

			// NotificationService.sendEmail("Failed to locate text " +parameter + "suring
			// healthcheck of " +ExcelUtils.getCellData(1, 0, "Test Cases"));
			driver.quit();
			return true;
		} catch (Exception e) {
			Reporting.testStepFailed("Failed to close browser \n " + e.getMessage());

			return false;
		}
	}

	public void quit() {
		if (driver != null)
			driver.quit();
	}

	public static String getprop(String name) throws IOException {
		// Create FileInputStream Object
		String cwd = System.getProperty("user.dir");
		System.out.println("Current working directory : " + cwd);
		FileInputStream fileInput = new FileInputStream(cwd + "/resources/AppId.properties");
		// Create Properties object
		Properties prop = new Properties();
		// load properties file
		prop.load(fileInput);

		return prop.getProperty(name);
	}

	public String getParentWindow() {
		return parentWindow;
	}

	public void setParentWindow(String parentWindow) {
		this.parentWindow = parentWindow;
	}

}
