package org.example.test;

import org.example.pages.HomePage;
import org.example.pages.ProductPage;
import org.example.pages.SearchResultPage;
import org.example.utilities.BaseTest;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest{

    @Test
    public void searchProductTest(){
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("iphone");

        SearchResultPage searchResult = new SearchResultPage(driver);
        searchResult.clickOnProduct("iphone");
        searchResult.isProductDisplayed("iphone");

    }



}
