package org.vaadin.addons.javaee.selenium.input;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class AbstractInputMethod implements InputMethod {

    protected WebDriver driver;

    public AbstractInputMethod(WebDriver driver) {
        this.driver = driver;
    }

    protected abstract String getElementClassAttribute();

    @Override
    public boolean accepts(String entityName, String attribute) {
        WebElement element = driver.findElement(By.id(entityName + "." + attribute));
        return element.getAttribute("class").contains(getElementClassAttribute());
    }

}
