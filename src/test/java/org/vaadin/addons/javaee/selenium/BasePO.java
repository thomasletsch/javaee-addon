package org.vaadin.addons.javaee.selenium;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.selenium.input.InputMethodFactory;

import com.google.common.base.Function;

public abstract class BasePO {

    private static Logger log = LoggerFactory.getLogger(BasePO.class);

    protected final static boolean DO_NOT_ASSERT_PAGE = false;

    public final static int SHORT_WAIT_SEC = 1;

    public final static int LONG_WAIT_SEC = 10;

    protected WebDriver driver;

    protected SeleniumActions actions;

    protected DataDrivenActions dataActions;

    protected SeleniumAssertions assertions;

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
        dataActions = new DataDrivenActions(actions);
        assertions = new SeleniumAssertions(driver);
        dataAssertions = new DataDrivenAssertions(assertions);
        inputMethodFactory = new InputMethodFactory(driver);
        if (assertPage) {
            assertPage();
        }
    }

    protected abstract String getIdentifyingElement();

    public void assertPage() {
        assertTrue("Failure while asserting page " + getClass().getSimpleName() + ". Element " + getIdentifyingElement() + " not found!",
                driver.findElements(By.id(getIdentifyingElement())).size() == 1);
    }

    public void waitForVaadin() {
        waitForSpinner(driver.findElements(By.cssSelector("v-loading-indicator-wait")));
        waitForSpinner(driver.findElements(By.cssSelector("v-loading-indicator-delay")));
        waitForSpinner(driver.findElements(By.cssSelector("v-loading-indicator")));
    }

    protected void waitForSpinner(List<WebElement> elements) {
        log.debug("Wait for spinner");
        new FluentWait<List<WebElement>>(elements).withTimeout(BasePO.LONG_WAIT_SEC, TimeUnit.SECONDS).until(
                new Function<List<WebElement>, Boolean>() {

                    @Override
                    public Boolean apply(List<WebElement> input) {
                        if (input.isEmpty()) {
                            log.debug("No active Spinner found");
                            return true;
                        }
                        for (WebElement webElement : input) {
                            if (webElement.isDisplayed()) {
                                log.debug("Active Spinner found");
                                return false;
                            }
                        }
                        log.debug("No active Spinner found");
                        return true;
                    }

                });
    }

}