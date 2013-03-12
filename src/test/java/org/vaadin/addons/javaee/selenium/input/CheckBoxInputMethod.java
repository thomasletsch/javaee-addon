package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckBoxInputMethod extends AbstractInputMethod {

    public CheckBoxInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String entityName, String attribute, String text) {
        Boolean check = Boolean.valueOf(text);
        WebElement inputField = driver.findElement(By.xpath("//span[@id='" + getId(entityName, attribute) + "']/input"));
        if (check) {
            if (!isChecked(inputField)) {
                inputField.click();
            }
        } else {
            if (isChecked(inputField)) {
                inputField.click();
            }
        }
    }

    @Override
    public String value(String entityName, String attribute) {
        WebElement inputField = driver.findElement(By.xpath("//span[@id='" + getId(entityName, attribute) + "']/input"));
        return isChecked(inputField).toString();
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        Boolean check = Boolean.valueOf(text);
        WebElement inputField = driver.findElement(By.xpath("//span[@id='" + getId(entityName, attribute) + "']/input"));
        if (check) {
            assertTrue(getId(entityName, attribute), isChecked(inputField));
        } else {
            assertFalse(getId(entityName, attribute), isChecked(inputField));
        }
    }

    private Boolean isChecked(WebElement inputField) {
        return inputField.getAttribute("checked") != null;
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-checkbox";
    }

}
