package org.vaadin.addons.javaee.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitConditions {

    public final static int SHORT_WAIT_MS = 100;

    public static final int SHORT_SLEEP_MS = 10;

    public final static int SHORT_WAIT_SEC = 1;

    public final static int DEFAULT_WAIT_SEC = 1;

    public final static int LONG_WAIT_SEC = 10;

    private static Logger log = LoggerFactory.getLogger(WaitConditions.class);

    /**
     * An expectation for checking if the given element is selected.
     */
    public static ExpectedCondition<Boolean> elementAttributeContains(final WebElement element, final String attribute,
            final String attributeValue) {
        return new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return element.getAttribute(attribute) != null && element.getAttribute(attribute).contains(attributeValue);
            }

            @Override
            public String toString() {
                return String.format("element (%s) attribute \"%s\" contains \"%s\"", element, attribute, attributeValue);
            }
        };
    }

    /**
     * An expectation for checking if the given element is selected.
     */
    public static ExpectedCondition<Boolean> elementAttributeContains(final String id, final String attribute, final String attributeValue) {
        return new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.id(id));
                return element.getAttribute(attribute) != null && element.getAttribute(attribute).contains(attributeValue);
            }

            @Override
            public String toString() {
                return String.format("element with id (%s): attribute \"%s\" contains \"%s\"", id, attribute, attributeValue);
            }
        };
    }

    /**
     * Waits for all vaadin async calls to be finished. Has a built in timeout of 10 sec.
     */
    public static void waitForVaadin(WebDriver driver) {
        String isVaadinFinished = "      if (window.vaadin == null) {" + "          return true;" + "      }" + ""
                + "      var clients = window.vaadin.clients;" + "      if (clients) {" + "          for (var client in clients) {"
                + "              if (clients[client].isActive()) {" + "              return false;" + "              }" + "          }"
                + "          return true;" + "      } else {" + "          return false;" + "      }";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long timeoutTime = System.currentTimeMillis() + LONG_WAIT_SEC;
        boolean finished = false;
        while ((System.currentTimeMillis() < timeoutTime) && (!finished)) {
            finished = ((Boolean) js.executeScript(isVaadinFinished, new Object[0])).booleanValue();
        }
    }

    public static void waitForShortTime() {
        try {
            Thread.sleep(SHORT_WAIT_MS);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

}
