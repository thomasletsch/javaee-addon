package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextAreaInputMethod extends AbstractInputMethod {

    public TextAreaInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String id, String text) {
        WebElement element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(text);
    }

    @Override
    public String value(String id) {
        WebElement element = driver.findElement(By.id(id));
        return element.getAttribute("value");
    }

    @Override
    public void assertInput(String id, String text) {
        WebElement element = driver.findElement(By.id(id));
        assertEquals(id, text, element.getAttribute("value"));
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-textarea";
    }

}
