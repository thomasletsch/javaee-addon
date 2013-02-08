package org.vaadin.addons.javaee.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.vaadin.addons.javaee.selenium.input.InputMethod;
import org.vaadin.addons.javaee.selenium.input.InputMethodFactory;

public class SeleniumActions {

    private WebDriver driver;

    private InputMethodFactory inputMethodFactory;

    public SeleniumActions(WebDriver driver) {
        this.driver = driver;
        inputMethodFactory = new InputMethodFactory(driver);
    }

    public void input(String entityName, String attribute, String text) {
        InputMethod inputMethod = inputMethodFactory.get(entityName, attribute);
        inputMethod.input(entityName, attribute, text);
    }

    public void clearText(String entityName, String attribute) {
        String id = entityName + "." + attribute;
        WebElement element = driver.findElement(By.id(id));
        element.clear();
    }

    public void clickButton(String button_name) {
        driver.findElement(By.id(button_name)).click();
    }

}
