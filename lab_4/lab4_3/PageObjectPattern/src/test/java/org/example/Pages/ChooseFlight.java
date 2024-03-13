package org.example.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

public class ChooseFlight {
    private WebDriver driver;

    public ChooseFlight(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName = "h3")
    private WebElement tittle;

    public String getTittle() {
        return tittle.getText();
    }

    public PurchasePage clickButton() {
        driver.findElement(By.cssSelector(".table > tbody:nth-child(2) > tr:nth-child(3) > td:nth-child(2) > input:nth-child(1)")).click();
        PurchasePage purchasePage = new PurchasePage(driver);
        return purchasePage;

    }



}
