package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver){
        this.driver=driver;
    }
//    verifies if a product with the given visible link text is present in the cart.
//    Returns true if present, false otherwise.
    public boolean verifyProductInCart(String productName){
        return driver.findElements(By.linkText(productName)).size() > 0;
    }
    /**
     * Clicks the 'Checkout' link/button from the cart page to proceed to checkout.
     * Uses link text "Checkout" which is present on the TutorialsNinja demo site.
     */

    public void clickCheckout() {
        driver.findElement(By.linkText("Checkout")).click();
    }
}