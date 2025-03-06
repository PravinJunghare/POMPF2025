package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	private WebDriver driver;

	// 1. Private By locators
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgottenPwdlink = By.id("input-email");
	private By registerlink = By.linkText("Register");

	// 2. Page Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;

	}

	// 3.Page Actions/Methods

	public String getLoginPageTitle() {
		String title = driver.getTitle();
		return title;
	}

	public String getLoginUrl() {
		String url = driver.getCurrentUrl();
		return url;
	}

	public boolean isForgotpwdLinkExits() {
		return driver.findElement(forgottenPwdlink).isDisplayed();

	}

	public boolean isregistetLinkExits() {
		return driver.findElement(registerlink).isDisplayed();

	}

	public String doLogin(String un, String pwd) {
		driver.findElement(username).sendKeys(un);
		driver.findElement(password).sendKeys(pwd);
		driver.findElement(loginBtn).click();
		String accPageTitle= driver.getTitle();
		System.out.println("Acc Page title ...." +accPageTitle);
		return accPageTitle;

	}

}