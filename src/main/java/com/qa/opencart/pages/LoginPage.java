package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstant;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Private By locators
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgottenPwdlink = By.id("input-email");
	private By registerlink = By.linkText("Register");

	// 2. Page Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	// 3.Page Actions/Methods
	@Step("Getting loginpage title")
	public String getLoginPageTitle() {
		// String title = driver.getTitle();
		String title = eleUtil.waitForTitleContainsAndFetch(AppConstant.DEFAULT_SHORT_TIMEOUT,
				AppConstant.LOGIN_PAGE_TITLE_VALUE);
		return title;
	}

	public String getLoginUrl() {
		// String url = driver.getCurrentUrl();
		String url = eleUtil.waitForURLContainsAndFetch(AppConstant.DEFAULT_MEDIUM_TIMEOUT,
				AppConstant.LOGIN_PAGE_URL_FRACTION_VALUE);
		return url;
	}

	public boolean isForgotpwdLinkExits() {

		// return driver.findElement(forgottenPwdlink).isDisplayed();
		return eleUtil.doElementIsDisplayed(forgottenPwdlink);

	}

	public boolean isregistertLinkExits() {
		// return driver.findElement(registerlink).isDisplayed();
		return eleUtil.doElementIsDisplayed(registerlink);

	}

	public AccountsPage doLogin(String un, String pwd) {
		// driver.findElement(username).sendKeys(un);
		// driver.findElement(password).sendKeys(pwd);
		// driver.findElement(loginBtn).click();
		System.out.println("App Credntial are " + un + "+pwd");

		eleUtil.waitForElementVisible(username, AppConstant.DEFAULT_MEDIUM_TIMEOUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);

	}

	public RegistrationPage navigateToRegisterPage() {
		{
			eleUtil.doClick(registerlink);
			return new RegistrationPage(driver);
		}
	}

}