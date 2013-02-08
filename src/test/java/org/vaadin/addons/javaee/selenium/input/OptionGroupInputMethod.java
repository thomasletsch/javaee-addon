package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OptionGroupInputMethod extends AbstractInputMethod {

    public OptionGroupInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String entityName, String attribute, String text) {
        WebElement element = driver.findElement(By.xpath("//div[@id='" + entityName + "." + attribute + "']/span[" + text + "]/input"));
        element.click();
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        WebElement element = driver.findElement(By.xpath("//div[@id='" + entityName + "." + attribute + "']/span[" + text + "]/input"));
        assertNotNull("Radio Button at pos " + text + " of " + entityName + "." + attribute + " must be checked",
                element.getAttribute("checked"));
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-select-optiongroup";
    }

}
