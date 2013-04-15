package org.vaadin.addons.javaee.selenium.po;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.selenium.DataDrivenActions;
import org.vaadin.addons.javaee.selenium.DataDrivenAssertions;
import org.vaadin.addons.javaee.selenium.SeleniumActions;
import org.vaadin.addons.javaee.selenium.SeleniumAssertions;
import org.vaadin.addons.javaee.selenium.SeleniumReads;
import org.vaadin.addons.javaee.selenium.WaitConditions;
import org.vaadin.addons.javaee.selenium.input.InputMethodFactory;

public abstract class BasePO {

    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(BasePO.class);

    /**
     * Marker for the second constructor to not assert page when initializing PO
     */
    protected final static boolean DO_NOT_ASSERT_PAGE = false;

    protected WebDriver driver;

    protected SeleniumActions actions;

    protected SeleniumAssertions assertions;

    protected SeleniumReads reads;

    protected DataDrivenActions dataActions;

    protected DataDrivenAssertions dataAssertions;

    protected InputMethodFactory inputMethodFactory;

    protected BasePO() {
    }

    public BasePO(WebDriver driver) {
        this(driver, true);
    }

    protected BasePO(WebDriver driver, boolean assertPage) {
        this.driver = driver;
        actions = new SeleniumActions(driver);
        assertions = new SeleniumAssertions(driver);
        reads = new SeleniumReads(driver);
        dataActions = new DataDrivenActions(actions);
        dataAssertions = new DataDrivenAssertions(assertions);
        inputMethodFactory = new InputMethodFactory(driver);
        if (assertPage) {
            assertPage();
        }
    }

    /**
     * The id of the element used to identify the page. Used in {@link #assertPage()} and constructors
     */
    protected abstract String getIdentifyingElement();

    /**
     * Tries to find out if the page object belongs to the current page. Sets the timeout for the page to show up to LONG_TIMEOUT.
     */
    public void assertPage() {
        WaitConditions.waitForVaadin(driver);
        driver.manage().timeouts().implicitlyWait(WaitConditions.LONG_WAIT_SEC, TimeUnit.SECONDS);
        assertTrue("Failure while asserting page " + getClass().getSimpleName() + ". Element " + getIdentifyingElement() + " not found!",
                driver.findElements(By.id(getIdentifyingElement())).size() == 1);
        driver.manage().timeouts().implicitlyWait(WaitConditions.DEFAULT_WAIT_SEC, TimeUnit.SECONDS);
    }
}