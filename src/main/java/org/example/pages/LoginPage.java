package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By emailField = By.id("input-email");
    private By passwordField = By.id("input-password");
    private By loginButton = By.xpath("//input[@value='Login']");

    public LoginPage(WebDriver driver){
         this.driver=driver;
    }
     public void enterEmail(String email){
        driver.findElement(emailField).sendKeys(email);
     }
     public void enterPassword(String pass){
        driver.findElement(passwordField).sendKeys(pass);
     }
     public void clickLogin(){
        driver.findElement(loginButton).click();
     }



}
