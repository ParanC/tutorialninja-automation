package org.example.test;

import org.example.pages.*;
import org.example.utilities.BaseTest;
import org.testng.annotations.Test;

public class CarTest extends BaseTest {

    @Test
    public void verifyProductInCartTest(){
        HomePage homePage= new HomePage(driver);
        homePage.searchProduct("HP");

        SearchResultPage search = new SearchResultPage(driver);
        search.clickOnProduct("HP");


        ProductPage product = new ProductPage(driver);
        product.addToCart();

        CartPage cart =new CartPage(driver);
        cart.verifyProductInCart("HP");



    }
}
