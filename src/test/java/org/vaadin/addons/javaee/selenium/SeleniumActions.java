package org.vaadin.addons.javaee.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumActions {

    private WebDriver driver;

    public SeleniumActions(WebDriver driver) {
        this.driver = driver;
    }

    public void select(String entityName, String attribute, String row) {
        WebElement element = driver.findElement(By.id(entityName + "." + attribute));
        WebElement selectDropDown = element.findElement(By.xpath("//div[@class=\"v-filterselect-button\"]"));
        selectDropDown.click();
        WebElement popupElement = driver.findElement(By.id("VAADIN_COMBOBOX_OPTIONLIST"));
        WebElement entry = popupElement.findElement(By.xpath(".//div[@class=\"v-filterselect-suggestmenu\"]/table/tbody/tr[" + row
                + "]//span"));
        entry.click();
    }

    public void typeText(String entityName, String attribute, String text) {
        typeText(entityName, null, attribute, text);
    }

    public void typeText(String entityName, String subEntityName, String attribute, String text) {
        String id = entityName + "." + ((subEntityName != null) ? subEntityName + "." : "") + attribute;
        WebElement element = driver.findElement(By.id(id));
        element.sendKeys(text);
    }

    public void typeDate(String entityName, String attribute, String text) {
        String id = entityName + "." + attribute;
        WebElement dateInputField = driver.findElement(By.xpath("//div[@id='" + id + "']/input"));
        dateInputField.sendKeys(text);
    }

    public void clickButton(String button_name) {
        driver.findElement(By.id(button_name)).click();
    }

}
