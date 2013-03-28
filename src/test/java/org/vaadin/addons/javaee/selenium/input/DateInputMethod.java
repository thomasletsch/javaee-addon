package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DateInputMethod extends AbstractInputMethod {

    public DateInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String id, String text) {
        WebElement dateInputField = driver.findElement(By.xpath("//div[@id='" + id + "']/input"));
        dateInputField.clear();
        dateInputField.sendKeys(text);
    }

    @Override
    public String value(String id) {
        WebElement element = driver.findElement(By.xpath("//div[@id='" + id + "']/input"));
        return element.getAttribute("value");
    }

    @Override
    public void assertInput(String id, String text) {
        WebElement element = driver.findElement(By.xpath("//div[@id='" + id + "']/input"));
        assertEquals(id, text, element.getAttribute("value"));
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-datefield";
    }

}
