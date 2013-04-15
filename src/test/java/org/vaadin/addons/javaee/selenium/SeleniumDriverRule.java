package org.vaadin.addons.javaee.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumDriverRule extends ExternalResource {

    private static Logger log = LoggerFactory.getLogger(SeleniumDriverRule.class);

    private static final String SAUCE_API_KEY_KEY = "SAUCE_ONDEMAND_ACCESS_KEY";

    private static final String SAUCE_USER_NAME_KEY = "SAUCE_ONDEMAND_USERNAME";

    private static final String SELENIUM_GRID_KEY = "seleniumGrid";

    private WebDriver driver;

    private boolean remote = false;

    private String seleniumGrid;

    @Override
    protected void before() throws Throwable {
        log.info("Creating driver...");
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/env.properties"));
        if (!StringUtils.isBlank(properties.getProperty(SELENIUM_GRID_KEY))) {
            remote = true;
            seleniumGrid = properties.getProperty(SELENIUM_GRID_KEY);
        }
        setUpDriver();
    }

    @Override
    protected void after() {
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
        driver.manage().window().setSize(new Dimension(1200, 1024));
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

    public WebDriver getDriver() {
        return driver;
    }
}
