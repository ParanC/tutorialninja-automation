package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    // existing locators
    private By searchBox = By.name("search");
    private By searchBtn = By.cssSelector("button.btn.btn-default.btn-lg");

    // naya: My Account + Login
    private By myAccountMenu = By.cssSelector("a[title='My Account']");
    private By loginLink = By.linkText("Login");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchProduct(String productName) {
        driver.findElement(searchBox).sendKeys(productName);
        driver.findElement(searchBtn).click();
    }

    // 👇 ye do methods login ke liye
    public void openMyAccountMenu() {
        driver.findElement(myAccountMenu).click();
    }

    public void clickLogin() {
        driver.findElement(loginLink).click();
    }
}
