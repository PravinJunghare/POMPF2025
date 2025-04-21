package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {
	// precondition is that user should be login for AccountsPage

	@BeforeClass

	public void producInfoPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}

	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] { { "MacBook", "MacBook Pro", 4 }, { "MacBook", "MacBook Air", 4 }, { "iMac", "iMac", 3 },
				{ "Apple", "Apple Cinema 30\"", 6 },

		};
	}

	@Test(dataProvider = "getProductImagesTestData")
	public void productImagecountTest(String searchkey, String ProdcutName, int imagecount) {
		searchPage = accountsPage.doSearch(searchkey);
		productInfoPage = searchPage.selectProduct(ProdcutName);
		int actualImagecount = productInfoPage.getImageCount();
		Assert.assertEquals(actualImagecount, imagecount);
	}

	@Test
	public void productInfoTest() {
		searchPage = accountsPage.doSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> actualproductInfoMap = productInfoPage.getProductInfo();
		// System.out.println(actualproductInfoMap);

		softAssert.assertEquals(actualproductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actualproductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actualproductInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(actualproductInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertAll();

		// Hard Assertions
		/*
		 * Assert.assertEquals(actualproductInfoMap.get("Brand"), "Apple");
		 * Assert.assertEquals(actualproductInfoMap.get("Product Code"), "Product 18");
		 * Assert.assertEquals(actualproductInfoMap.get("productname"), "MacBook Pro");
		 * Assert.assertEquals(actualproductInfoMap.get("productprice"), "$2,000.
		 */
	}

	@Test()
	public void addToCartSingleProductTest() {
		searchPage = accountsPage.doSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		productInfoPage.enterQuantity(2);
		String actualaddtoCartMsg = productInfoPage.addProductTocart();
		// Success: You have added MacBook Pro to your shopping cart!
		softAssert.assertTrue(actualaddtoCartMsg.contains("Success"));
		softAssert.assertTrue(actualaddtoCartMsg.contains("MacBook Pro"));
		softAssert.assertTrue(actualaddtoCartMsg.indexOf("Success") >= 0);
		// softAssert.assertTrue(actualaddtoCartMsg.indexOf(prodcutname )>= 0);
		softAssert.assertEquals(actualaddtoCartMsg, "Success: You have added MacBook Pro to your shopping cart");
		softAssert.assertAll();
	}

	@DataProvider
	public Object[][] getAddToCartTestData() {
		return new Object[][] { { "MacBook", "MacBook Pro", 1 }, { "MacBook", "MacBook Air", 2 }, { "iMac", "iMac", 3 },

		};
	}

	@Test(dataProvider = "getAddToCartTestData")
	public void addToCartMultipleProductTest(String searchkey, String prodcutname, int quantity) {
		// searchPage = accountsPage.performSearch("MacBook");
		// productInfoPage = searchPage.selectProduct("MacBook Pro");

		searchPage = accountsPage.doSearch(searchkey);
		productInfoPage = searchPage.selectProduct(prodcutname);
		productInfoPage.enterQuantity(quantity);
		String actualaddtoCartMsg = productInfoPage.addProductTocart();
		// Success: You have added MacBook Pro to your shopping cart!
		// softAssert.assertTrue(actualaddtoCartMsg.contains("Success"));
		// softAssert.assertTrue(actualaddtoCartMsg.contains("MacBook Pro"));
		softAssert.assertTrue(actualaddtoCartMsg.indexOf("Success") >= 0);
		softAssert.assertTrue(actualaddtoCartMsg.indexOf(prodcutname) >= 0);
		softAssert.assertEquals(actualaddtoCartMsg,
				"Success: You have added " + prodcutname + " to your shopping cart");
		softAssert.assertAll();
	}

}