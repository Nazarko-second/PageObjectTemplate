package scripts;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.fetch.Fetch;
import org.openqa.selenium.devtools.v91.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v91.network.model.ErrorReason;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Arrays.asList;

public class DevToolsTest {

        static String PARAMETER = "paymentOptionId2=1";
//    static String PARAMETER = "paymentOptionId=\\d+";
    static final String CONTAINS = "contains";
    static final String MATCHES = "matches";
//    String action = CONTAINS;
    String action = MATCHES;

    @Test
    void testRequestIntercepting() throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://colibri:devsonly@stage.elitecme.com/nursing/alabama");
        DevTools devTools = setUpDevTools(driver);


        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
        devTools.addListener(Fetch.requestPaused(), request -> {
            System.out.println("new request intercepted");
                    String url = request.getRequest().getUrl();
                    if (url.contains("items.json")) {
                        System.out.println("ENDPOINT FOUND!!! " + request.getRequest().getUrl());

                    String param = processPassedParameter(PARAMETER);

                    if (url.contains(param)) {
                        switch (action) {
                            case MATCHES:
                                Pattern p = Pattern.compile("(" + PARAMETER + ").*");
                                Matcher m = p.matcher(url);
                                if (m.find()) {
                                    System.out.println("URL parameter " + m.group(1) + " MATCHES pattern: " + PARAMETER);
                                } else {
                                    System.out.println("URL parameter DOES NOT MATCH pattern: " + PARAMETER);
                                }
                                break;
                            default:
                                if (url.contains(PARAMETER)) {
                                    System.out.println("URL CONTAINS parameter: " + PARAMETER);
                                } else {
                                    System.out.println("URL DOES NOT CONTAIN parameter: " + PARAMETER);
                                }
                        }
                    } else {
                        System.out.println("END: URL DOES NOT CONTAIN parameter: " + PARAMETER);
                    }
                }

            devTools.send(Fetch.continueRequest(
                    request.getRequestId(),
                    Optional.of(url),
                    Optional.of(request.getRequest().getMethod()),
                    request.getRequest().getPostData(),
                    request.getResponseHeaders()));
            System.out.println("request released");
        });


/*            Optional<List<RequestPattern>> patterns = Optional.of(asList(new RequestPattern(Optional.of("*items.json*"), Optional.empty(), Optional.empty())));
            devTools.send(Fetch.enable(patterns, Optional.empty()));
            devTools.addListener(Fetch.requestPaused(), r -> devTools.send(Fetch.failRequest(r.getRequestId(), ErrorReason.FAILED)));*/


        WebElement addMembership = driver.findElement(By.xpath("(//button[contains(text(), 'Add to Cart')])[4]"));
        Thread.sleep(3000);

        addMembership.click();

        Thread.sleep(2000);


        System.out.println("======= FINDING NEXT ELEMENT ======");

        Wait<WebDriver> wait = new FluentWait<>((WebDriver)driver)
                .withTimeout(Duration.of(30, SECONDS))
                .ignoring(NoSuchElementException.class);
//                .ignoring(ElementNotInteractableException.class);

        System.out.println("Start find element");
        WebElement checkMembership = driver.findElement(By.xpath("(//a[contains(text(), 'Checkout')])[6]"));

        System.out.println("Element found");
//        Thread.sleep(2000);

        Wait<WebDriver> wait2 = new FluentWait<>((WebDriver)driver)
                .withTimeout(Duration.of(30, SECONDS))
                .ignoring(ElementNotInteractableException.class);
        checkMembership.click();

        Thread.sleep(2000);
        driver.quit();

    }

    String processPassedParameter(String parameter) {
        String par;
        if (parameter.contains("=")) {
            par = parameter.split("=")[0];
        } else {
            par = parameter;
        }
        return par;
    }

    DevTools setUpDevTools(ChromeDriver dr) {
        DevTools devTools = dr.getDevTools();
        devTools.createSession();
        return devTools;
    }


}
