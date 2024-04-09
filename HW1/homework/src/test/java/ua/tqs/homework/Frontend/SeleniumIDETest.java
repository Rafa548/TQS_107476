package ua.tqs.homework.Frontend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
class SeleniumIDETest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(2062, 1118));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    void testHomeAndReservationConfirmPage() {
        driver.get("http://localhost:8080/home.html");
        PageFactory.initElements(driver, this);

        WebElement departureCity = driver.findElement(By.id("departureCity"));
        assertThat(departureCity.isDisplayed()).isTrue(); // Assert departure city dropdown is displayed
        new Select(departureCity).selectByVisibleText("Porto");

        WebElement arrivalCity = driver.findElement(By.id("arrivalCity"));
        assertThat(arrivalCity.isDisplayed()).isTrue(); // Assert arrival city dropdown is displayed
        new Select(arrivalCity).selectByVisibleText("Braga");

        driver.findElement(By.cssSelector(".route:nth-child(1) .stop:nth-child(2) > p:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".route:nth-child(1) .stop:nth-child(4) > p:nth-child(2)")).click();
        driver.findElement(By.id("reserveBtn")).click();

        assertThat(driver.getCurrentUrl()).isEqualTo("http://localhost:8080/reservation_confirmation.html?stopId1=2&stopId2=4&routeId=1");

        WebElement clientName = driver.findElement(By.id("clientName"));
        assertThat(clientName.isDisplayed()).isTrue(); // Assert client name input field is displayed
        clientName.click();
        clientName.sendKeys("John Doe");

        WebElement numberOfSeats = driver.findElement(By.id("numberOfSeats"));
        assertThat(numberOfSeats.isDisplayed()).isTrue(); // Assert number of seats dropdown is displayed
        new Select(numberOfSeats).selectByVisibleText("1");

        WebElement seat1 = driver.findElement(By.name("seat1"));
        assertThat(seat1.isDisplayed()).isTrue(); // Assert seat dropdown is displayed
        new Select(seat1).selectByVisibleText("1B");

        WebElement currency = driver.findElement(By.id("currency"));
        assertThat(currency.isDisplayed()).isTrue(); // Assert currency dropdown is displayed
        new Select(currency).selectByVisibleText("CHF");

        driver.findElement(By.cssSelector("input:nth-child(10)")).click();
        driver.findElement(By.id("closeModalBtn")).click();

        assertThat(driver.getCurrentUrl()).isEqualTo("http://localhost:8080/home.html");
    }

}

