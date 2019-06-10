package com.as.selenium.util;

import com.as.selenium.api.SupportedWebDrivers;
import com.as.selenium.engine.TestEngine;
import com.gargoylesoftware.htmlunit.WebClientOptions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by Tejas
 */
public class WebDriverFactory {


    public static WebDriver createDriver(SupportedWebDrivers driver) throws IOException {
        switch (driver) {
            case HTMLUNIT: return createSilentDriver();
            case INTERNET_EXPLORER: return createIEDriver();
            case CHROME: return createChromeDriver();
            default: return createSilentDriver();
        }
    }

    private static WebDriver createSilentDriver() {
        SilentDriver driver = new SilentDriver();
        
        //set logging level
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
        
        driver.setJavascriptEnabled(true);
        driver.getOptions().setThrowExceptionOnScriptError(false);

        return driver;
    }

    private static WebDriver createIEDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability("ignoreZoomSetting", true);
        capabilities.setCapability("nativeEvents", false);
        capabilities.setCapability("unexpectedAlertBehaviour", "accept");
        capabilities.setCapability("ignoreProtectedModeSettings", true);
        capabilities.setCapability("disable-popup-blocking", true);
        capabilities.setCapability("enablePersistentHover", true);

        File file = new File(TestEngine.IE_DRIVER_PATH);
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

        return new InternetExplorerDriver(capabilities);
    }

    private static WebDriver createChromeDriver() throws IOException {
		File file = new File("").getCanonicalFile();
		System.setProperty("webdriver.chrome.driver", file.getParent() +"/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        
        
        return new ChromeDriver(options);
    }

    private static class SilentDriver  extends HtmlUnitDriver {

		public WebClientOptions getOptions() {
            return this.getWebClient().getOptions();
        }
    }
}
