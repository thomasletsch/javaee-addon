package org.vaadin.addons.javaee.selenium;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

public abstract class SeleniumTest {

    private static final String URL_KEY = "url";

    @Rule
    public TakeScreenshot seleniumDriverRule = new TakeScreenshot();

    private String baseUrl;

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
        seleniumDriverRule.getDriver().navigate().to(baseUrl);
    }

    @After
    public void tearDownSelenium() {

    }

}