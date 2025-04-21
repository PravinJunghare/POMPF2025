package com.qa.opencart.tests;

import java.util.Random;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstant;
import com.qa.opencart.utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest {
	@BeforeClass
	public void regPagesetUp() {
		registrationPage = loginPage.navigateToRegisterPage();
	}

	public String getRandomEmail() {
		// *********By using Random class**********
		Random random = new Random();
		// String email = "automationtest" + random.nextInt(1000) + "@gmail.com";

		// By using System.curretTimeMillSec()//Permanat solution
		String email = "automationtest" + System.currentTimeMillis() + "@gmail.com";
		return email;
	}

	@DataProvider
	public Object[][] getRegTestData() {
		// Object regData[][] = ExcelUtil.getTestData(AppConstant.REGISTER_SHEET_NAME);
		// return regData;
		return ExcelUtil.getTestData(AppConstant.REGISTER_SHEET_NAME);

	}

	@Test(dataProvider = "getRegTestData")

	public void userRegTest(String fName, String lName, String telephoneno, String passwd, String subscribe) {
		// **hardcoded data*********
		// registrationPage.registerUser("test", "user", "user@1231111.com",
		// "9020202020",
		// "test@123", "yes");

		// ****Simple Email****
		// registrationPage.registerUser(fName, lName, emailId, telephone no, password,
		// subscribe);
		// as we run test first time will pass but second time there will be check
		// duplicate email so need to generate random email

		// ***Random Email*****
		// remove email from excel data provider instead call getRandomMethod
		registrationPage.registerUser(fName, lName, getRandomEmail(), telephoneno, passwd, subscribe);
	}

}