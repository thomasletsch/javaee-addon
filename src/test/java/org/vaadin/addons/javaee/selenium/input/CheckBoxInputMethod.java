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
        String id = entityName + "." + attribute;
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

    private boolean isChecked(WebElement inputField) {
        return inputField.getAttribute("checked") != null;
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        Boolean check = Boolean.valueOf(text);
        String id = entityName + "." + attribute;
        WebElement inputField = driver.findElement(By.xpath("//span[@id='" + id + "']/input"));
        if (check) {
            assertTrue(id, isChecked(inputField));
        } else {
            assertFalse(id, isChecked(inputField));
        }
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-checkbox";
    }

}
