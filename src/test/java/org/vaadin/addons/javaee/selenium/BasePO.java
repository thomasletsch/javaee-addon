package org.vaadin.addons.javaee.selenium;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.selenium.input.InputMethodFactory;

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
        String isVaadinFinished = "      if (window.vaadin == null) {" + "          return true;" + "      }" + ""
                + "      var clients = window.vaadin.clients;" + "      if (clients) {" + "          for (var client in clients) {"
                + "              if (clients[client].isActive()) {" + "              return false;" + "              }" + "          }"
                + "          return true;" + "      } else {" + "          return false;" + "      }";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long timeoutTime = System.currentTimeMillis() + 20000L;
        boolean finished = false;
        while ((System.currentTimeMillis() < timeoutTime) && (!finished)) {
            finished = ((Boolean) js.executeScript(isVaadinFinished, new Object[0])).booleanValue();
        }
    }
}