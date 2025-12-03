package org.example.test;


import org.example.pages.HomePage;
import org.example.pages.SearchResultPage;
import org.example.pages.ProductPage;
import org.example.pages.CartPage;
import org.example.pages.CheckOutPage;
import org.example.utilities.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class CheckOutTest extends BaseTest {

    @Test
    public void checkoutProcessTest() throws InterruptedException{
        HomePage homepage= new HomePage(driver);
        homepage.searchProduct("Hp");

        SearchResultPage search = new SearchResultPage(driver);
        search.clickOnProduct("HP");

        ProductPage product = new ProductPage(driver);
        product.addToCart();

        // go to cart and then to checkout
        CartPage cart= new CartPage(driver);
        cart.clickCheckout();

        // after cart.clickCheckout();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("checkout"));


        CheckOutPage checkout=new CheckOutPage(driver);
        checkout.selectGuestCheckout();
        checkout.clickContinueStep1();
        // small wait so page section renders
        Thread.sleep(1000);
        // Step 2: fill billing details
        checkout.fillBillingDetails(
                "paran",        // firstName
                "Choudury",     // lastName
                "paran@test.com", // email (better to use valid-looking)
                "1234567890",   // telephone
                "zzz street",   // address1
                "kanpur",       // city
                "07",           // postCode
                "India",        // country (visible text in dropdown)
                "Delhi"         // region/state (visible text)
        );
        checkout.clickContinueStep2();

        Thread.sleep(1000);

        // Step 3: use existing address (if available) and continue
//        checkout.clickExistingAddress();
//        checkout.clickContinueStep3();

        Thread.sleep(1000);

        // Step 4: delivery method -> continue
        checkout.clickContinueStep4();

        Thread.sleep(1000);

        // Step 5: accept terms and continue
        checkout.agreeTerms();
        checkout.clickContinueStep5();

        Thread.sleep(1000);

        // Step 6: confirm
        checkout.clickConfirmOrder();

    }



}
