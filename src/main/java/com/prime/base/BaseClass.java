package com.prime.base;

import com.prime.utilities.ExtentReportManager;
import com.prime.utilities.PropertiesFile;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseClass {

	private static final Logger logs = Logger.getLogger(BaseClass.class);
	public static PropertiesFile properties;

	// ThreadLocal WebDriver for parallel tests
	private static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

	@BeforeSuite
	public void loadConfig() {
		PropertyConfigurator.configure("src/test/resources/logs_properties/logs.properties");
		properties = new PropertiesFile();
		ExtentReportManager.setUpReports();
	}


	public void setUp(String browser) {

		String url = properties.getProperties("url");

		if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions c_opts=new ChromeOptions();
				c_opts.addArguments("--disable-popup-blocking");
				c_opts.addArguments("--headless");
				driver.set(new ChromeDriver(c_opts));
		} else if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions opts=new FirefoxOptions();
				opts.addArguments("--disable-popup-blocking");
				opts.addArguments("--headless");
				driver.set(new FirefoxDriver(opts));
		} else if (browser.equalsIgnoreCase("edge")) {
			EdgeOptions e_opts=new EdgeOptions();
				e_opts.addArguments("--disable-popup-blocking");
				 e_opts.addArguments("-headless-");
				driver.set(new EdgeDriver(e_opts));
		} else {
			throw new IllegalArgumentException("Browser type not supported: " + browser);
		}

//		switch (browser.toLowerCase()) {
//			case "chrome":
//				ChromeOptions c_opts=new ChromeOptions();
//				c_opts.addArguments("--disable-popup-blocking");
//				//c_opts.addArguments("--headless");
//				driver.set(new ChromeDriver(c_opts));
//				break;
//
//			case "firefox":
//				FirefoxOptions opts=new FirefoxOptions();
//				opts.addArguments("--disable-popup-blocking");
//				//opts.addArguments("--headless");
//				driver.set(new FirefoxDriver(opts));
//				break;
//
//			case "edge":
//				EdgeOptions e_opts=new EdgeOptions();
//				e_opts.addArguments("--disable-popup-blocking");
//				// e_opts.addArguments("-headless-");
//				driver.set(new EdgeDriver(e_opts));
//				break;
//
//			default:
//				logs.error("Invalid browser type specified");
//				throw new IllegalStateException("Unsupported browser: " + browser);
//		}

		getDriver().get(url);
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().implicitlyWait(
				Duration.ofSeconds(Integer.parseInt(properties.getProperties("implicitWait"))));
	}

	public static RemoteWebDriver  getDriver() {
		return driver.get();
	}


	public void tearDown() {
		if (getDriver() != null) {
			getDriver().quit();
			driver.remove(); // Remove driver instance from ThreadLocal
		}
	}
}
