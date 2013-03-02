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
    public void input(String entityName, String attribute, String text) {
        String id = entityName + "." + attribute;
        WebElement dateInputField = driver.findElement(By.xpath("//div[@id='" + id + "']/input"));
        dateInputField.clear();
        dateInputField.sendKeys(text);
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-datefield";
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        String id = entityName + "." + attribute;
        WebElement element = driver.findElement(By.xpath("//div[@id='" + id + "']/input"));
        assertEquals(id, text, element.getAttribute("value"));
    }

}
