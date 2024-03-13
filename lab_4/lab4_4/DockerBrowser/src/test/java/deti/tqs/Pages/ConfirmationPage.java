package deti.tqs.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPage {

    private WebDriver driver;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName = "h1")
    private WebElement text;

    public String getText() {
        return text.getText();
    }

    public String getTittle() {
        return driver.getTitle();
    }





}
