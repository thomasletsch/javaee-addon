package org.vaadin.addons.javaee.selenium;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class SeleniumTest {

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
        driver = new FirefoxDriver();
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/env.properties"));
        baseUrl = properties.getProperty("url");
        driver.manage().timeouts().implicitlyWait(WaitConditions.DEFAULT_WAIT_SEC, TimeUnit.SECONDS);
        driver.navigate().to(baseUrl);
    }

    @After
    public void tearDownSelenium() {
        driver.quit();
    }

}