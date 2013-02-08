package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DropDownInputMethod extends AbstractInputMethod {

    public DropDownInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String entityName, String attribute, String text) {
        WebElement element = driver.findElement(By.id(entityName + "." + attribute));
        WebElement selectDropDown = element.findElement(By.xpath("//div[@class=\"v-filterselect-button\"]"));
        selectDropDown.click();
        WebElement popupElement = driver.findElement(By.id("VAADIN_COMBOBOX_OPTIONLIST"));
        WebElement entry = popupElement.findElement(By.xpath(".//div[@class=\"v-filterselect-suggestmenu\"]/table/tbody/tr[" + text
                + "]//span"));
        entry.click();
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        String id = entityName + "." + attribute;
        WebElement element = driver.findElement(By.id(id));
        WebElement selectDropDown = element.findElement(By.xpath(".//div[@class=\"v-filterselect-button\"]"));
        selectDropDown.click();
        WebElement popupElement = driver.findElement(By.id("VAADIN_COMBOBOX_OPTIONLIST"));
        WebElement entry = popupElement.findElement(By.xpath(".//div[@class=\"v-filterselect-suggestmenu\"]/table/tbody/tr[" + text
                + "]/td"));
        assertTrue("Entry " + text + " of " + id + " must be selected", entry.getAttribute("class").contains("gwt-MenuItem-selected"));
        selectDropDown.click();
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-filterselect";
    }
}
