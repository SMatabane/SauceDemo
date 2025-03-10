package com.prime.base;


import com.prime.utilities.ExtentReportManager;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.apache.log4j.Logger;
import com.prime.utilities.PropertiesFile;

import java.time.Duration;



public class BaseClass {
	
	//create driver
	public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
	public static PropertiesFile properties;
	private static final Logger logs = Logger.getLogger(BaseClass.class);
	
	
	/**
	 * Initialize logs and properties file
	 */
	@BeforeSuite
	public void loadConfig() {
		 // Configure logs
        PropertyConfigurator.configure("src/test/resources/logs_properties/logs.properties");
		properties=new PropertiesFile();
		ExtentReportManager.setUpReports();
	}

	/**
	 * This method sets up the browser
	 */
	public void setUp() {

		String browser=properties.getProperties("browser");
		//boolean isheadless=Boolean.parseBoolean(properties.getProperties("headless"));
		String url=properties.getProperties("url");

		switch(browser.toLowerCase()){
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();
				//if (isheadless) chromeOptions.addArguments("--headless");
				chromeOptions.addArguments("--disable-popup-blocking");
				driver.set(new ChromeDriver(chromeOptions));
				break;
			case "firefox":
				FirefoxOptions firefox = new FirefoxOptions();
				//if (isheadless) firefox.addArguments("--headless");
				firefox.addArguments("--disable-popup-blocking");
				driver.set(new FirefoxDriver(firefox));
				break;

			case "edge":
				EdgeOptions edgeOptions = new EdgeOptions();
				//if (isheadless) edgeOptions.addArguments("--headless");
				edgeOptions.addArguments("--disable-popup-blocking");
				driver.set(new EdgeDriver(edgeOptions));
				break;

			default:
				logs.error("Invalid browser type please try again");

		}

		getDriver().get(url);
		//Maximize the screen
		getDriver().manage().window().maximize();
		//Delete all the cookies
		getDriver().manage().deleteAllCookies();
		//Implicit TimeOuts
		getDriver().manage().timeouts().implicitlyWait
				(Duration.ofSeconds(Integer.parseInt(properties.getProperties("implicitWait"))));

	}





	
	/**
	 *  Get Driver from threadLocalmap
	 * @return driver
	 */
	public static WebDriver getDriver() {
		return driver.get();
	}

	@AfterSuite
	public void afterSuite(){
		ExtentReportManager.flushReport();

	}
	

}
