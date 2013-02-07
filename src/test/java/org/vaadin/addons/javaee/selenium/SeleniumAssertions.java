package org.vaadin.addons.javaee.selenium;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumAssertions {

    private WebDriver driver;

    public SeleniumAssertions(WebDriver driver) {
        this.driver = driver;
    }

    public void assertText(String entityName, String attribute, String text) {
        assertText(entityName, null, attribute, text);
    }

    public void assertText(String entityName, String subEntityName, String attribute, String text) {
        String id = entityName + "." + ((subEntityName != null) ? subEntityName + "." : "") + attribute;
        WebElement element = driver.findElement(By.id(id));
        assertEquals(id, text, element.getAttribute("value"));
    }

    public void assertDropDown(String entityName, String attribute, String row) {
        String id = entityName + "." + attribute;
        WebElement element = driver.findElement(By.id(id));
        WebElement selectDropDown = element.findElement(By.xpath(".//div[@class=\"v-filterselect-button\"]"));
        selectDropDown.click();
        WebElement popupElement = driver.findElement(By.id("VAADIN_COMBOBOX_OPTIONLIST"));
        WebElement entry = popupElement.findElement(By
                .xpath(".//div[@class=\"v-filterselect-suggestmenu\"]/table/tbody/tr[" + row + "]/td"));
        assertTrue("Entry " + row + " of " + id + " must be selected", entry.getAttribute("class").contains("gwt-MenuItem-selected"));
        selectDropDown.click();
    }

    public void assertRadioButton(String entityName, String attribute, String pos) {
        WebElement element = driver.findElement(By.xpath("//div[@id='" + entityName + "." + attribute + "']/span[" + pos + "]/input"));
        assertNotNull("Radio Button at pos " + pos + " of " + entityName + "." + attribute + " must be checked",
                element.getAttribute("checked"));
    }

    public void assertDate(String entityName, String attribute, String text) {
        String id = entityName + "." + attribute;
        WebElement element = driver.findElement(By.xpath("//div[@id='" + id + "']/input"));
        assertEquals(id, text, element.getAttribute("value"));
    }

}
