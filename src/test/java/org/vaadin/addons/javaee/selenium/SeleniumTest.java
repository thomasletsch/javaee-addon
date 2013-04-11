package org.vaadin.addons.javaee.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SeleniumTest {

    private static final String SAUCE_API_KEY_KEY = "SAUCE_ONDEMAND_ACCESS_KEY";

    private static final String SAUCE_USER_NAME_KEY = "SAUCE_ONDEMAND_USERNAME";

    private static Logger log = LoggerFactory.getLogger(SeleniumTest.class);

    protected WebDriver driver;

    private String baseUrl;

    public SeleniumTest() {
        super();
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    @Before
    public void setUpSelenium() throws Exception {
        setUpDriver();
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/env.properties"));
        baseUrl = properties.getProperty("url");
        driver.manage().timeouts().implicitlyWait(WaitConditions.DEFAULT_WAIT_SEC, TimeUnit.SECONDS);
        driver.navigate().to(baseUrl);
    }

    /**
     * To be overwritten. This implementation works with Firefox local and on SauceLabs
     */
    protected void setUpDriver() {
        String username = System.getenv(SAUCE_USER_NAME_KEY);
        String apiKey = System.getenv(SAUCE_API_KEY_KEY);
        if (username != null) {
            setUpSauceLabsDriver(username, apiKey);
        } else {
            setUpLocalDriver();
        }
    }

    /**
     * To be overwritten. This implementation creates the Firefox local caps
     */
    protected void setUpLocalDriver() {
        driver = new FirefoxDriver();
    }

    /**
     * To be overwritten. This implementation creates a firefox SauceLabs cap
     */
    protected void setUpSauceLabsDriver(String username, String apiKey) {
        DesiredCapabilities capabillities = DesiredCapabilities.firefox();
        capabillities.setCapability("platform", "Windows 7");
        capabillities.setCapability("version", "20");
        try {
            driver = new RemoteWebDriver(new URL("http://" + username + ":" + apiKey + "@ondemand.saucelabs.com:80/wd/hub"), capabillities);
        } catch (MalformedURLException e) {
            log.error("Could not create remote webdriver on saucelabs", e);
        }
    }

    @After
    public void tearDownSelenium() {
        driver.quit();
    }

}