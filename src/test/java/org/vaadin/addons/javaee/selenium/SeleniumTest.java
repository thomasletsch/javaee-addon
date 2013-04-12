package org.vaadin.addons.javaee.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SeleniumTest {

    private static final String SAUCE_API_KEY_KEY = "SAUCE_ONDEMAND_ACCESS_KEY";

    private static final String SAUCE_USER_NAME_KEY = "SAUCE_ONDEMAND_USERNAME";

    private static final String URL_KEY = "url";

    private static final String SELENIUM_GRID_KEY = "seleniumGrid";

    private static Logger log = LoggerFactory.getLogger(SeleniumTest.class);

    @Rule
    public TakeScreenshot takeScreenshot = new TakeScreenshot();

    protected WebDriver driver;

    private String baseUrl;

    private boolean remote = false;

    private String seleniumGrid;

    public SeleniumTest() {
        super();
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    @Before
    public void setUpSelenium() throws Exception {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/env.properties"));
        baseUrl = properties.getProperty(URL_KEY);
        if (!StringUtils.isBlank(properties.getProperty(SELENIUM_GRID_KEY))) {
            remote = true;
            seleniumGrid = properties.getProperty(SELENIUM_GRID_KEY);
        }
        setUpDriver();
        takeScreenshot.setDriver(driver);
        driver.navigate().to(baseUrl);
    }

    /**
     * To be overwritten. This implementation works with Firefox local and on SauceLabs
     */
    protected void setUpDriver() {
        if (remote) {
            setUpRemoteDriver();
        } else {
            setUpLocalDriver();
        }
        driver.manage().timeouts().implicitlyWait(WaitConditions.DEFAULT_WAIT_SEC, TimeUnit.SECONDS);
    }

    private void setUpRemoteDriver() {
        String username = System.getenv(SAUCE_USER_NAME_KEY);
        String apiKey = System.getenv(SAUCE_API_KEY_KEY);
        if (username != null) {
            setUpSauceLabsDriver(username, apiKey);
        } else {
            DesiredCapabilities capabillities = DesiredCapabilities.firefox();
            try {
                driver = new RemoteWebDriver(new URL(seleniumGrid), capabillities);
            } catch (MalformedURLException e) {
                log.error("Could not create a webdriver for seleniumGrid " + seleniumGrid, e);
            }
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

    }

}