package org.example.test;

import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.utilities.BaseTest;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {
    @Test
    public void validLoginTest(){

        //pehle home page se 'login' pe jao
        HomePage home = new HomePage(driver);
        home.openMyAccountMenu();
        home.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("testuser@example.com");
        loginPage.enterPassword("password123");
        loginPage.clickLogin();
        //Assertion can be added here
    }
}
