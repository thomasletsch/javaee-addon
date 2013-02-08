package org.vaadin.addons.javaee.selenium;

import org.openqa.selenium.WebDriver;
import org.vaadin.addons.javaee.selenium.input.InputMethod;
import org.vaadin.addons.javaee.selenium.input.InputMethodFactory;

public class SeleniumAssertions {

    @SuppressWarnings("unused")
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

}
