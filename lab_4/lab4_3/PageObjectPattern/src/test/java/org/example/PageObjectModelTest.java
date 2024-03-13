package org.example;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.openqa.selenium.chrome.ChromeDriver;

import org.example.Pages.HomePage;
import org.example.Pages.ChooseFlight;
import org.example.Pages.PurchasePage;
import org.example.Pages.ConfirmationPage;


public class PageObjectModelTest {



    @Test
    public void testPageObjectModel() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://blazedemo.com");
        HomePage homePage = new HomePage(driver);
        homePage.selecionarFromPort("Boston");
        homePage.selecionarToPort("London");
        ChooseFlight chooseFlight = homePage.clickButton();
        assertThat(chooseFlight.getTittle(), is(equalTo("Flights from Boston to London:")));
        PurchasePage purchasePage = chooseFlight.clickButton();
        purchasePage.setInputName("AJDIASAJDI");
        purchasePage.setAddress("Rua 122");
        purchasePage.setCity("Braga");
        purchasePage.setState("Braga");
        purchasePage.setZipCode("321-222");
        purchasePage.selectType("American Express");
        purchasePage.setCreditCardNumber("2132312312");
        purchasePage.setCreditCardMonth("1");
        purchasePage.setCreditCardYear("2024222");
        purchasePage.setNameOnCard("idk");
        purchasePage.clickRememberMe();
        ConfirmationPage confirmationPage = purchasePage.clickButton();
        assertThat(confirmationPage.getText(), is(equalTo("Thank you for your purchase today!")));
        assertThat(confirmationPage.getTittle(), is(equalTo("BlazeDemo Confirmation")));
    }
}
