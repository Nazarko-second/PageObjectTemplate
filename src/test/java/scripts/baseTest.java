package scripts;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class baseTest {

    public WebDriver driver;

    @BeforeMethod
    void setUp() {
        WebDriverManager.chromedriver().driverVersion("88.0.4324.96").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    void tearDown() {
        try {
            driver.quit();
        } catch (Exception e) {
            System.out.println("failed to close driver");
        }
    }
}
