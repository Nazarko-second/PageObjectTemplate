package scripts;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.fetch.Fetch;
import org.openqa.selenium.devtools.v91.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v91.network.model.ErrorReason;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class DevToolsTest {

    @Test
    void testRequestIntercepting() {

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();

        driver.get("https://www.elitecme.com/nursing/alabama");

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
        devTools.addListener(Fetch.requestPaused(), request -> {
            String url = request.getRequest().getUrl();
            if (url.contains("items.json")) {
                System.out.println("FOUND!!! " + request.getRequest().getUrl());
            }
            if (url.contains("paymentOptionId")) {
                Pattern p = Pattern.compile("(paymentOptionId=?\\d*)");
                Matcher m = p.matcher(url);
                if (m.find())
                    System.out.println(url.substring(m.start()));
                    System.out.println(m.group(1));
            }
            devTools.send(Fetch.continueRequest(
                    request.getRequestId(),
                    Optional.of(url),
                    Optional.of(request.getRequest().getMethod()),
                    request.getRequest().getPostData(),
                    request.getResponseHeaders()));
        });


/*            Optional<List<RequestPattern>> patterns = Optional.of(asList(new RequestPattern(Optional.of("*items.json*"), Optional.empty(), Optional.empty())));
            devTools.send(Fetch.enable(patterns, Optional.empty()));
            devTools.addListener(Fetch.requestPaused(), r -> devTools.send(Fetch.failRequest(r.getRequestId(), ErrorReason.FAILED)));*/


            WebElement addMembership = driver.findElement(By.xpath("(//button[contains(text(), 'Add to Cart')])[3]"));
            addMembership.click();

        driver.quit();

    }
}
