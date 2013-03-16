package org.vaadin.addons.javaee.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WaitConditions {

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

}
