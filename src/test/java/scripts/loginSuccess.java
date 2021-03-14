package scripts;

import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;

public class loginSuccess extends baseTest {

    @Test
    void testLoginSuccess() {
        MainPage mainpage = new MainPage(driver);
        driver.get("https://elitecme.com");
        LoginPage loginPage = mainpage.clickLoginButton();
        loginPage.typeUsername("tr@tr.com")
                .typePassword("qweqweqwe")
                .clickLoginButton();
    }
}
