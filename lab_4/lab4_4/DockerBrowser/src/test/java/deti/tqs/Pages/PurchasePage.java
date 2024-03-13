package deti.tqs.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class PurchasePage {

    private WebDriver driver;


    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.NAME, using = "inputName")
    private WebElement inputName;

    @FindBy(how = How.NAME, using = "address")
    private WebElement address;

    @FindBy(how = How.NAME, using = "city")
    private WebElement city;

    @FindBy(how = How.NAME, using = "state")
    private WebElement state;

    @FindBy(how = How.NAME, using = "zipCode")
    private WebElement zipCode;

    @FindBy(how = How.NAME, using = "creditCardNumber")
    private WebElement creditCardNumber;

    @FindBy(how = How.NAME, using = "creditCardMonth")
    private WebElement creditCardMonth;

    @FindBy(how = How.NAME, using = "creditCardYear")
    private WebElement creditCardYear;

    @FindBy(how = How.NAME, using = "nameOnCard")
    private WebElement nameOnCard;

    @FindBy(how = How.CSS, using = "input[type='submit']")
    private WebElement submitButton;

    @FindBy(id="rememberMe")
    private WebElement rememberMe;

    @FindBy(name="cardType")
    private WebElement cardType;

    public void setInputName(String inputName) {
        this.inputName.sendKeys(inputName);
    }

    public void setAddress(String address) {
        this.address.sendKeys(address);
    }

    public void setCity(String city) {
        this.city.sendKeys(city);
    }

    public void setState(String state) {
        this.state.sendKeys(state);
    }

    public void setZipCode(String zipCode) {
        this.zipCode.sendKeys(zipCode);
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber.sendKeys(creditCardNumber);
    }

    public void setCreditCardMonth(String creditCardMonth) {
        this.creditCardMonth.sendKeys(creditCardMonth);
    }

    public void setCreditCardYear(String creditCardYear) {
        this.creditCardYear.sendKeys(creditCardYear);
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard.sendKeys(nameOnCard);
    }

    public void clickRememberMe() {
        rememberMe.click();
    }

    public void selectType(String opcao) {
        Select dropdown = new Select(cardType);
        dropdown.selectByVisibleText(opcao);
    }

    public ConfirmationPage clickButton() {
        submitButton.click();
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        return confirmationPage;

    }
}
