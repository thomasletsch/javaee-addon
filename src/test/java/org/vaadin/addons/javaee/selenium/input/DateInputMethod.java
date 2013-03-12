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
        WebElement dateInputField = driver.findElement(By.xpath("//div[@id='" + getId(entityName, attribute) + "']/input"));
        dateInputField.clear();
        dateInputField.sendKeys(text);
    }

    @Override
    public String value(String entityName, String attribute) {
        WebElement element = driver.findElement(By.xpath("//div[@id='" + getId(entityName, attribute) + "']/input"));
        return element.getAttribute("value");
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        WebElement element = driver.findElement(By.xpath("//div[@id='" + getId(entityName, attribute) + "']/input"));
        assertEquals(getId(entityName, attribute), text, element.getAttribute("value"));
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-datefield";
    }

}
