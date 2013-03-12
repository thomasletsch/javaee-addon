package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextInputMethod extends AbstractInputMethod {

    public TextInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String entityName, String attribute, String text) {
        WebElement element = driver.findElement(By.id(getId(entityName, attribute)));
        element.clear();
        element.sendKeys(text);
    }

    @Override
    public String value(String entityName, String attribute) {
        WebElement element = driver.findElement(By.id(getId(entityName, attribute)));
        return element.getAttribute("value");
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        WebElement element = driver.findElement(By.id(getId(entityName, attribute)));
        assertEquals(getId(entityName, attribute), text, element.getAttribute("value"));
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-textfield";
    }

}
