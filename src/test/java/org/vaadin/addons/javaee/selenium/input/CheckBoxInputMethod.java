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
    public void input(String id, String text) {
        Boolean check = Boolean.valueOf(text);
        WebElement inputField = driver.findElement(By.xpath("//span[@id='" + id + "']/input"));
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
    public String value(String id) {
        WebElement inputField = driver.findElement(By.xpath("//span[@id='" + id + "']/input"));
        return isChecked(inputField).toString();
    }

    @Override
    public void assertInput(String id, String text) {
        Boolean check = Boolean.valueOf(text);
        WebElement inputField = driver.findElement(By.xpath("//span[@id='" + id + "']/input"));
        if (check) {
            assertTrue(id, isChecked(inputField));
        } else {
            assertFalse(id, isChecked(inputField));
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
