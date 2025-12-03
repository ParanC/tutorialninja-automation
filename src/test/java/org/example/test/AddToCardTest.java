package org.example.test;

import org.example.pages.HomePage;
import org.example.pages.SearchResultPage;
import org.example.pages.ProductPage;
import org.example.utilities.BaseTest;
import org.testng.annotations.Test;

public class AddToCardTest extends BaseTest {

    @Test
    public void addToCart(){
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("MacBook");

        SearchResultPage searchResult = new SearchResultPage(driver);
        searchResult.clickOnProduct("MacBook");

        ProductPage product = new ProductPage(driver);
        product.addToCart();


    }
}
