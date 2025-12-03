package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ProductPage{
    WebDriver driver;
    @FindBy(id="button-cart")
    WebElement addToCartBtn;

    @FindBy(css=".alert-success")
    WebElement successALERT;

    public ProductPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);

    }

    public void addToCart(){
        addToCartBtn.click();

    }
    public boolean isAddedToCart(){
        return successALERT.isDisplayed();
    }



}