package deti.tqs.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;

public class HomePage {
    private WebDriver driver;

    private static String PAGE_URL="https://blazedemo.com";

    public HomePage(WebDriver driver){
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name="fromPort")
    private WebElement fromPort;

    @FindBy(name="toPort")
    private WebElement toPort;

    public void selecionarFromPort(String opcao) {
        Select dropdown = new Select(fromPort);
        dropdown.selectByVisibleText(opcao);
    }

    public void selecionarToPort(String opcao) {
        Select dropdown = new Select(toPort);
        dropdown.selectByVisibleText(opcao);
    }

    public ChooseFlight clickButton() {
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        ChooseFlight chooseFlight = new ChooseFlight(driver);
        return chooseFlight;

    }



}
