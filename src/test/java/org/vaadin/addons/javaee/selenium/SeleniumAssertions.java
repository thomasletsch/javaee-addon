package org.vaadin.addons.javaee.selenium;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.vaadin.addons.javaee.selenium.input.InputMethod;
import org.vaadin.addons.javaee.selenium.input.InputMethodFactory;

public class SeleniumAssertions {

    private WebDriver driver;

    private InputMethodFactory inputMethodFactory;

    public SeleniumAssertions(WebDriver driver) {
        this.driver = driver;
        inputMethodFactory = new InputMethodFactory(driver);
    }

    public void assertText(String entityName, String attribute, String text) {
        InputMethod inputMethod = inputMethodFactory.get(entityName, attribute);
        inputMethod.assertInput(entityName, attribute, text);
    }

    public void assertText(String id, String text) {
        InputMethod inputMethod = inputMethodFactory.get(id);
        inputMethod.assertInput(id, text);
    }

    public void assertNoError() {
        List<WebElement> elements = driver.findElements(By.className("v-Notification"));
        assertTrue("Error Element found!", elements.isEmpty());
    }

}
