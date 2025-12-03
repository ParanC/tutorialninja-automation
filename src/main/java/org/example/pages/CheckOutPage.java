package org.example.pages;


// add these imports at top of CheckoutPage.java
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CheckOutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
        this.wait =new WebDriverWait(driver,Duration.ofSeconds(10));//simple wait
    }

    // Step 1: Checkout Options
    private By registerAccountRadio = By.xpath("//input[@value='register']");
    private By guestCheckoutRadio = By.xpath("//input[@value='guest']");
    private By continueStep1 = By.id("button-account");

    // Step 2: Billing Details
    private By firstName = By.id("input-payment-firstname");
    private By lastName = By.id("input-payment-lastname");
    private By email = By.id("input-payment-email");
    private By telephone = By.id("input-payment-telephone");

    private By address1 = By.id("input-payment-address-1");
    private By city = By.id("input-payment-city");
    private By postCode = By.id("input-payment-postcode");
    private By country = By.id("input-payment-country");
    private By region = By.id("input-payment-zone");

    private By continueStep2 = By.id("button-guest");

    // Step 3 Delivery Details
    private By existingAddressRadio = By.xpath("//input[@value='existing']");
    private By continueStep3 = By.id("button-shipping-address");

    // Step 4 Delivery Method
    private By continueStep4 = By.id("button-shipping-method");

    // Step 5 Payment Method
    private By agreeCheckbox = By.name("agree");
    private By continueStep5 = By.id("button-payment-method");

    // Step 6 Confirm Order
    private By confirmOrderBtn = By.id("button-confirm");

    //------------- SIMPLE ACTIONS -----------------

    public void selectRegisterAccount() {
        driver.findElement(registerAccountRadio).click();
    }

    public void selectGuestCheckout() {
        // Wait for the "New Customer" area (Step 1) to be visible first
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(text(),'Checkout') or contains(text(),'Checkout')]")
            ));
        } catch (Exception e) {
            // ignore if header not found, we'll try next waits
        }

        // Try robust locators - in order
        By[] candidateLocators = new By[] {
                By.xpath("//input[@name='account' and (@value='guest' or contains(@value,'guest'))]"),
                By.xpath("//label[contains(.,'Guest Checkout')]/input"),
                By.xpath("//label[contains(.,'Guest')]/input"),
                By.cssSelector("input[value='guest']"),
                By.xpath("//input[@value='guest']") // original - last attempt
        };

        for (By loc : candidateLocators) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(loc)).click();
                System.out.println("Clicked guest using locator: " + loc);
                return;
            } catch (Exception ignored) {
                // try next locator
            }
        }

        // If still not found, helpful message and throw
        throw new RuntimeException("Could not find Guest Checkout radio. Inspect the page - maybe the page uses different markup. " +
                "You can print page source in test to debug: System.out.println(driver.getPageSource());");
    }


    public void clickContinueStep1() {
        By continueBtn = By.id("button-account"); // typical
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
        } catch (Exception e) {
            // fallback: try finding button by text
            By alt = By.xpath("//button[contains(text(),'Continue')]");
            wait.until(ExpectedConditions.elementToBeClickable(alt)).click();
        }
    }

    public void fillBillingDetails(String fName, String lName, String mail, String phone,
                                   String addr, String cityName, String pin,
                                   String countryName, String regionName) {

        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(email).sendKeys(mail);
        driver.findElement(telephone).sendKeys(phone);

        driver.findElement(address1).sendKeys(addr);
        driver.findElement(city).sendKeys(cityName);
        driver.findElement(postCode).sendKeys(pin);

        // Select country
        Select c = new Select(driver.findElement(country));
        c.selectByVisibleText(countryName);
        // Simple Wait for states to load
        try {
            Thread.sleep(1500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Select state
        Select r = new Select(driver.findElement(region));
        r.selectByVisibleText(regionName);
    }

    public void clickContinueStep2() {
        driver.findElement(continueStep2).click();
    }

    public void clickExistingAddress() {
        driver.findElement(existingAddressRadio).click();
    }

    public void clickContinueStep3() {
        driver.findElement(continueStep3).click();
    }


    public void clickContinueStep4() {
        try {
            // pehle wait karo ki button visible + clickable ho
            wait.until(ExpectedConditions
                            .elementToBeClickable(By.id("button-shipping-method")))
                    .click();
        } catch (Exception e) {
            // fallback: agar id change ho gayi ho to generic continue dhoondo
            By alt = By.xpath("//div[contains(.,'Delivery Method')]//input[@value='Continue' or contains(@id,'shipping')]");
            driver.findElement(alt).click();
        }
    }



    public void agreeTerms() {
        driver.findElement(agreeCheckbox).click();
    }

    public void clickContinueStep5() {
        driver.findElement(continueStep5).click();
    }

    public void clickConfirmOrder() {
        driver.findElement(confirmOrderBtn).click();
    }
}











//
//package org.example.pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//import java.util.List;
//
//public class CheckOutPage {
//    private WebDriver driver;
//    private WebDriverWait wait;
//    private JavascriptExecutor js;
//
//    private By inputFirstName = By.id("input-payment-firstname");
//    private By inputLastName = By.id("input-payment-lastname");
//    private By companyName = By.id("input-payment-company");
//    private By address_1 = By.id("input-payment-address-1");
//    private By address_2 = By.id("input-payment-address-2");
//    private By cityName = By.id("input-payment-city");
//    private By postCode = By.id("input-payment-postcode");
//    private By countryName = By.xpath("//select[@id='input-payment-country']");
//    private By region_state = By.xpath("//select[@id='input-payment-zone']");
//    private By billingDetailscontinueBtn = By.xpath("//input[@value='Continue' and contains(@class,'btn btn-primary')]");
//
//    // step-3 delivery details
//    private By existingAddressSelect = By.name("address_id");
//    private By radioUseNewAddress = By.xpath("//label[contains(.,'I want to use a new address')]/preceding-sibling::input[@type='radio']");
//
//    private By continueBtnDeliveryDetails = By.xpath("//input[@value='Continue' and contains(@onclick,'shipping') or contains(@id,'button-shipping-address')]");
//
//    //fallback generic continue inside Delivery panel:
//    private By genericContinueInDelivery = By.xpath("//div[contains(.,'Delivery Details')]//input[@value='Continue' or contains(@class,'btn')][last()]");
//
//    // step 4 delivery method
//    private By deliveryMethodPanelHeader = By.xpath("//a[contains(.,'Step 4: Delivery Method') or contains(.,'Delivery Method')]");
//    private By shippingMethodRadios = By.cssSelector("input[name='shipping_method']");
//    private By shippingCommentsTextarea = By.name("comment");
//    private By continueBtnShippingMethod = By.id("button-shipping-method");
//    private By genericContinueInShipping = By.xpath("//div[contains(.,'Delivery Method')]//input[@value='Continue' or contains(@class,'btn')][last()]");
//
//    // Step 5 - Payment Method
//    private By paymentMethodPanelHeader = By.xpath("//a[contains(.,'Step 5: Payment Method') or contains(.,'Payment Method')]");
//    private By paymentMethodRadios = By.cssSelector("input[name='payment_method']");
//    private By agreeCheckbox = By.name("agree");
//    private By continueBtnPaymentMethod = By.id("button-payment-method");
//    private By genericContinueInPayment = By.xpath("//div[contains(.,'Payment Method')]//input[@value='Continue' or contains(@class,'btn')][last()]");
//
//    // Step 6 - Confirm
//    private By confirmOrderButton = By.id("button-confirm");
//    private By orderSuccessHeading = By.xpath("//h1[contains(.,'Your order has been placed') or contains(.,'Thank you for your order')]");
//
//    public CheckOutPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//        this.js = (JavascriptExecutor) driver;
//        PageFactory.initElements(driver, this);
//    }
//
//    // SINGLE helper: check presence & visible
//    private boolean isElementPresent(By locator) {
//        try {
//            List<WebElement> els = driver.findElements(locator);
//            return !els.isEmpty() && els.get(0).isDisplayed();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // SINGLE helper: click if present (By locator)
//    private boolean clickIfPresent(By locator) {
//        try {
//            if (!isElementPresent(locator)) return false;
//            WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
//            scrollToElement(el);
//            el.click();
//            // brief pause to let UI update
//            try { Thread.sleep(300); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
//            return true;
//        } catch (TimeoutException te) {
//            return false;
//        } catch (Exception e) {
//            // last resort try direct find & click
//            try {
//                WebElement fallback = driver.findElement(locator);
//                scrollToElement(fallback);
//                fallback.click();
//                return true;
//            } catch (Exception ignored) {}
//        }
//        return false;
//    }
//
//    // helper: scroll & click WebElement
//    private void scrollToAndClick(WebElement el) {
//        try {
//            scrollToElement(el);
//            wait.until(ExpectedConditions.elementToBeClickable(el));
//            el.click();
//        } catch (Exception e) {
//            try { el.click(); } catch (Exception ignored) {}
//        }
//    }
//
//    private void scrollToElement(WebElement el) {
//        try {
//            js.executeScript("arguments[0].scrollIntoView({behavior:'auto', block:'center'});", el);
//        } catch (Exception ignored) {}
//    }
//
//    // filling billing details
//    public void fillingBillingDetails(String firstName, String lastName, String company, String adress1, String adress2,
//                                      String city, String post, String country, String region) {
//        try {
//            if (firstName != null && isElementPresent(inputFirstName)) {
//                WebElement e = driver.findElement(inputFirstName);
//                e.clear(); e.sendKeys(firstName);
//            }
//            if (lastName != null && isElementPresent(inputLastName)) {
//                WebElement e = driver.findElement(inputLastName);
//                e.clear(); e.sendKeys(lastName);
//            }
//            if (company != null && isElementPresent(companyName)) {
//                WebElement e = driver.findElement(companyName);
//                e.clear(); e.sendKeys(company);
//            }
//            if (adress1 != null && isElementPresent(address_1)) {
//                WebElement e = driver.findElement(address_1);
//                e.clear(); e.sendKeys(adress1);
//            }
//            if (adress2 != null && isElementPresent(address_2)) {
//                WebElement e = driver.findElement(address_2);
//                e.clear(); e.sendKeys(adress2);
//            }
//            if (city != null && isElementPresent(cityName)) {
//                WebElement e = driver.findElement(cityName);
//                e.clear(); e.sendKeys(city);
//            }
//            if (post != null && isElementPresent(postCode)) {
//                WebElement e = driver.findElement(postCode);
//                e.clear(); e.sendKeys(post);
//            }
//
//            // country select
//            if (country != null && isElementPresent(countryName)) {
//                WebElement countryElement = driver.findElement(countryName);
//                Select countrySelect = new Select(countryElement);
//                boolean matched = countrySelect.getOptions().stream().anyMatch(o -> o.getText().trim().equals(country.trim()));
//                if (matched) countrySelect.selectByVisibleText(country);
//                else if (countrySelect.getOptions().size() > 1) countrySelect.selectByIndex(1);
//            }
//
//            // region select
//            if (region != null && isElementPresent(region_state)) {
//                WebElement regionElement = driver.findElement(region_state);
//                Select regionSelect = new Select(regionElement);
//                boolean matched = regionSelect.getOptions().stream().anyMatch(o -> o.getText().trim().equals(region.trim()));
//                if (matched) regionSelect.selectByVisibleText(region);
//                else if (regionSelect.getOptions().size() > 1) regionSelect.selectByIndex(1);
//            }
//        } catch (Exception ignored) {}
//    }
//
//    public void clickContinueButton() {
//        clickIfPresent(billingDetailscontinueBtn);
//    }
//
//    // use existing address and continue
//    public void useExistingAddressAndContinue(String visibleAddressText) {
//        try {
//            if (isElementPresent(existingAddressSelect)) {
//                WebElement sel = driver.findElement(existingAddressSelect);
//                Select s = new Select(sel);
//                if (visibleAddressText != null && !visibleAddressText.isEmpty()) {
//                    boolean matched = s.getOptions().stream().anyMatch(o -> o.getText().trim().equals(visibleAddressText.trim()));
//                    if (matched) s.selectByVisibleText(visibleAddressText);
//                    else if (s.getOptions().size() > 1) s.selectByIndex(1);
//                } else if (s.getOptions().size() > 1) {
//                    s.selectByIndex(1);
//                }
//            } else {
//                try {
//                    WebElement label = driver.findElement(By.xpath("//label[contains(.,'I want to use an existing address')]"));
//                    scrollToAndClick(label);
//                } catch (Exception ignored) {}
//            }
//        } catch (Exception ignored) {}
//
//        if (!clickIfPresent(continueBtnDeliveryDetails)) {
//            clickIfPresent(genericContinueInDelivery);
//        }
//    }
//
//    /* Delivery method, payment, confirm methods kept similar to your previous logic — examples below */
//
//    public void selectShippingMethod(String partialLabelText, String commentIfAny) {
//        // expand header if necessary
//        expandPanelIfCollapsed(deliveryMethodPanelHeader);
//
//        try {
//            List<WebElement> radios = driver.findElements(shippingMethodRadios);
//            if (!radios.isEmpty()) {
//                boolean picked = false;
//                for (WebElement r : radios) {
//                    String labelText = "";
//                    try {
//                        WebElement label = r.findElement(By.xpath("./ancestor::label[1]"));
//                        labelText = label.getText();
//                    } catch (Exception ignored) {}
//                    String value = r.getAttribute("value") == null ? "" : r.getAttribute("value");
//                    if ((partialLabelText != null && !partialLabelText.isEmpty() &&
//                            (labelText.contains(partialLabelText) || value.contains(partialLabelText)))
//                            || (partialLabelText == null || partialLabelText.isEmpty())) {
//                        scrollToAndClick(r);
//                        picked = true;
//                        break;
//                    }
//                }
//                if (!picked) scrollToAndClick(radios.get(0));
//            }
//        } catch (Exception ignored) {}
//
//        if (commentIfAny != null && isElementPresent(shippingCommentsTextarea)) {
//            WebElement ta = driver.findElement(shippingCommentsTextarea);
//            ta.clear();
//            ta.sendKeys(commentIfAny);
//        }
//
//        if (!clickIfPresent(continueBtnShippingMethod)) clickIfPresent(genericContinueInShipping);
//    }
//
//    public void selectPaymentMethodAndAgree(String partialLabelText) {
//        expandPanelIfCollapsed(paymentMethodPanelHeader);
//
//        try {
//            List<WebElement> radios = driver.findElements(paymentMethodRadios);
//            if (!radios.isEmpty()) {
//                boolean picked = false;
//                for (WebElement r : radios) {
//                    String labelText = "";
//                    try {
//                        WebElement label = r.findElement(By.xpath("./ancestor::label[1]"));
//                        labelText = label.getText();
//                    } catch (Exception ignored) {}
//                    String value = r.getAttribute("value") == null ? "" : r.getAttribute("value");
//                    if ((partialLabelText != null && !partialLabelText.isEmpty() &&
//                            (labelText.contains(partialLabelText) || value.contains(partialLabelText)))
//                            || (partialLabelText == null || partialLabelText.isEmpty())) {
//                        scrollToAndClick(r);
//                        picked = true;
//                        break;
//                    }
//                }
//                if (!picked) scrollToAndClick(radios.get(0));
//            }
//        } catch (Exception ignored) {}
//
//        try {
//            if (isElementPresent(agreeCheckbox)) {
//                WebElement agree = driver.findElement(agreeCheckbox);
//                if (!agree.isSelected()) scrollToAndClick(agree);
//            } else {
//                List<WebElement> labels = driver.findElements(By.xpath("//label[contains(.,'Terms & Conditions') or contains(.,'I have read and agree')]"));
//                if (!labels.isEmpty()) scrollToAndClick(labels.get(0));
//            }
//        } catch (Exception ignored) {}
//
//        if (!clickIfPresent(continueBtnPaymentMethod)) clickIfPresent(genericContinueInPayment);
//    }
//
//    public void clickConfirmOrder() {
//        try { expandPanelIfCollapsed(By.xpath("//a[contains(.,'Step 6: Confirm Order') or contains(.,'Confirm Order')]")); } catch (Exception ignored) {}
//        if (!clickIfPresent(confirmOrderButton)) {
//            try {
//                List<WebElement> fallback = driver.findElements(By.xpath("//button[contains(.,'Confirm Order') or contains(.,'Confirm')]"));
//                if (!fallback.isEmpty()) scrollToAndClick(fallback.get(0));
//            } catch (Exception ignored) {}
//        }
//    }
//
//    public boolean isOrderPlaced() {
//        try {
//            wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessHeading));
//            return driver.findElements(orderSuccessHeading).size() > 0;
//        } catch (TimeoutException te) {
//            return false;
//        }
//    }
//
//    private void expandPanelIfCollapsed(By headerLocator) {
//        try {
//            if (isElementPresent(headerLocator)) {
//                WebElement header = driver.findElement(headerLocator);
//                js.executeScript("arguments[0].scrollIntoView(true);", header);
//                try {
//                    WebElement panelDiv = header.findElement(By.xpath("./ancestor::div[1]"));
//                    WebElement body = panelDiv.findElement(By.xpath(".//div[contains(@class,'panel-body') or contains(@class,'collapse') or contains(@class,'card-body')]"));
//                    if (body != null && !body.isDisplayed()) {
//                        header.click();
//                        try { Thread.sleep(300); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
//                    }
//                } catch (Exception ex) {
//                    header.click();
//                    try { Thread.sleep(300); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
//                }
//            }
//        } catch (Exception ignored) {}
//    }
//
//}
