package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage{


    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    private String logInButton = "li.menu-item-login";

    public LoginPage clickLoginButton() {
        driver.findElement(By.cssSelector(logInButton)).click();
//        Thread.sleep(5000);
        return new LoginPage(driver);
    }
}
