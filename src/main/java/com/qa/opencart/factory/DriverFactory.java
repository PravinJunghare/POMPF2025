package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;

import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driverfactory {

	public WebDriver driver;
	public Properties prop;
	public OptionManager optionsManger;
	public static String highlight;

	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
	public static String isHighlight;

	/**
	 * this method is initalizing the browser on the basis of browser name
	 * 
	 * @param browserName
	 * @return this returns the driver
	 */
	// public WebDriver initDriver(String browserName)
	public WebDriver initDriver(Properties prop) {
		optionsManger = new OptionManager(prop);

		highlight = prop.getProperty("highlight");
		String browserName = prop.getProperty("browser").trim();
		System.out.println("browsername is :" + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {
			// driver = new ChromeDriver(optionsManger.getChromeOptions());
			tldriver.set(new ChromeDriver(optionsManger.getChromeOptions()));
			// Set the thread local for chrome class
		}

		else if (browserName.trim().equalsIgnoreCase("firefox")) {
			// driver = new FirefoxDriver(optionsManger.getFirefoxOptions());
			tldriver.set(new FirefoxDriver(optionsManger.getFirefoxOptions()));
		} else if (browserName.trim().equalsIgnoreCase("edge")) {
			// driver = new EdgeDriver(optionsManger.getEdgeOptions());
			tldriver.set(new EdgeDriver(optionsManger.getEdgeOptions()));
		} else {
			System.out.println("Enter correct browser :" + browserName);
		}

		// here all methods are calling driver so should be replaced by getDriver()

		// driver.manage().deleteAllCookies();
		// driver.manage().window().maximize();
		// driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		// driver.get(prop.getProperty("url"));
		// return driver;

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		// driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		getDriver().get(prop.getProperty("url").trim());
		return getDriver();

	}

	/*
	 * get the local thread copy of the driver
	 */
	// here every driver will get respective thread copy
	public synchronized static WebDriver getDriver() {
		return tldriver.get();

	}

	/**
	 * This method is used to read properties from .properties file
	 * 
	 * @return
	 */
	// environment specific config.file

	/**
	 * this method is reading the properties from the .properties file
	 * 
	 * @return
	 */
	public Properties initProp() {

		// mvn clean install -Denv="qa"
		// mvn clean install
		prop = new Properties();
		FileInputStream ip = null;
		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env: " + envName);

		try {
			if (envName == null) {
				System.out.println("no env is passed....Running tests on QA env...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("....Wrong env is passed....No need to run the test cases....");
					// throw new FrameworkException("WRONG ENV IS PASSED...");
					// break;
				}

			}
		} catch (FileNotFoundException e) {

		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/*
	 * // old code public Properties initProp() { prop = new Properties(); try {
	 * FileInputStream ip = new
	 * FileInputStream("./src/main/resources/config/config.properties");
	 * prop.load(ip); } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * 
	 * return prop; }
	 * 
	 * /** take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
			// org.openqa.selenium.io.FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}