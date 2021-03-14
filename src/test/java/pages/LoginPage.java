package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class LoginPage extends BasePage{

//    private final WebDriver driver;
    private final static int TIMEOUT = 10;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    private String usernameInput = "input#ctl00_MasterContent_userNameTextBox";
    private String passwordInput = "input#ctl00_MasterContent_passWordTextBox";
    private String signInButton = "input#ctl00_MasterContent_signInButton";


    public LoginPage typeUsername(String username) {
//        new WebDriverWait(driver, 5).until(webDriver -> ExpectedConditions.visibilityOfElementLocated(By.cssSelector(usernameInput)).apply(webDriver));
        WebElement element = findElement(usernameInput);
        element.sendKeys(username);
        return this;
    }

    public LoginPage typePassword(String password) {
        WebElement element = findElement(passwordInput);
        element.sendKeys(password);
        return this;
    }

    public void clickLoginButton() {
        WebElement element = findElement(signInButton);
        element.click();
    }

    public WebElement findElement(String selector) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.of(TIMEOUT, SECONDS))
                .ignoring(NoSuchElementException.class);
        return driver.findElement(By.cssSelector(selector));
    }
}
