package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // adjust these if your site has specific containers / product link selectors
    private By resultsContainer = By.cssSelector("div.search-results, ul.products, .product-list");
    private By allAnchors = By.cssSelector("a");

    public SearchResultPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    // ADD TO CART for a specific product in Search Result Page
    public void clickAddToCart(String productName) {
        // dynamic xpath: find product by name, then its Add to Cart button
        By addToCartBtn = By.xpath(
                "//a[text()='" + productName + "']/ancestor::div[@class='product-thumb']//button[contains(@onclick,'cart.add')]"
        );

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
        btn.click();
    }

    /**
     * Click product by name with several fallback strategies:
     * 1) exact linkText
     * 2) partialLinkText
     * 3) case-insensitive contains among visible anchors
     */
    public void clickOnProduct(String productName){
        String trimmed = productName == null ? "" : productName.trim();

        // wait for results to appear (best-effort)
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(resultsContainer),
                    ExpectedConditions.visibilityOfElementLocated(allAnchors)
            ));
        } catch (TimeoutException ignored){
            // continue anyway, maybe page doesn't use typical containers
        }

        // 1) exact linkText (quick try)
        try {
            WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(trimmed)));
            e.click();
            return;
        } catch (Exception ignored){}

        // 2) partial link text
        try {
            WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(trimmed)));
            e.click();
            return;
        } catch (Exception ignored){}

        // 3) scan visible anchors (case-insensitive)
        List<WebElement> anchors = driver.findElements(allAnchors);
        String lower = trimmed.toLowerCase();
        for (WebElement a : anchors) {
            try {
                if (!a.isDisplayed()) continue;
                String txt = a.getText();
                if (txt != null && txt.toLowerCase().contains(lower)) {
                    wait.until(ExpectedConditions.elementToBeClickable(a)).click();
                    return;
                }
                String title = a.getAttribute("title");
                if (title != null && title.toLowerCase().contains(lower)) {
                    wait.until(ExpectedConditions.elementToBeClickable(a)).click();
                    return;
                }
            } catch (Exception ex) {
                // stale or not clickable -> try next
            }
        }

        // not found -> give helpful runtime message
        throw new RuntimeException("Product not found in search results: '" + productName + "'. Current URL: " + driver.getCurrentUrl());
    }

    // optional: safer existence check with wait
    public boolean isProductDisplayed(String productName){
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(resultsContainer),
                    ExpectedConditions.visibilityOfElementLocated(allAnchors)
            ));
        } catch (Exception ignored){}

        String lower = productName.trim().toLowerCase();
        for (WebElement a : driver.findElements(allAnchors)) {
            try {
                if (!a.isDisplayed()) continue;
                String txt = a.getText();
                if (txt != null && txt.toLowerCase().contains(lower)) return true;
                String title = a.getAttribute("title");
                if (title != null && title.toLowerCase().contains(lower)) return true;
            } catch (Exception ignored){}
        }
        return false;
    }
}



















//package org.example.pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//
//public class SearchResultPage {
//    private WebDriver driver;
//
//    public SearchResultPage(WebDriver driver){
//        this.driver=driver;
//    }
//
////    Click any product y visible link text
//    public void clickOnProduct(String producName){
//        driver.findElement(By.linkText(producName)).click();
//
//    }
//
////    optional:check product exists in search results
//    public boolean isProductDisplayed(String productName){
//        return driver.findElements(By.linkText(productName)).size() > 0;
//
//    }
//}
