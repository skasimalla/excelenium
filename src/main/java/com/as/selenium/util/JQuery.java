package com.as.selenium.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Created by Tejas
 */
public class JQuery {

	
	public static void injectjQueryIfNeeded(WebDriver driver) {
		if (!jQueryLoaded(driver)) {
			injectjQuery(driver);
		}
	}
	
	private static Boolean jQueryLoaded(WebDriver driver) {
		Boolean loaded;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			loaded = (Boolean) js.executeScript ("return jQuery() != null");
		} catch (WebDriverException e) {
			loaded = false;
			System.out.println("Error checking for jQuery \n" + e.getMessage());
		}
		return loaded;
	}
	
	private static void injectjQuery(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(" var headID = document.getElementByTagName(\"head\") [0];" +
									"var newScript = document.createElement('script');" +
									"newScript.type = 'text/javascript';" +
									"newScript.src = 'https://code.jquery.com/jquery-3.1.1.min.js';" +
									"headID.appendChild(newScript);");
	}

}
