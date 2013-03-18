package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.vaadin.addons.javaee.selenium.WaitConditions;

public class DropDownInputMethod extends AbstractInputMethod {

    public DropDownInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String entityName, String attribute, String text) {
        WebElement element = driver.findElement(By.id(getId(entityName, attribute)));
        WebElement selectDropDown = element.findElement(By.xpath(".//div[@class=\"v-filterselect-button\"]"));
        selectDropDown.click();
        WebElement popupElement = driver.findElement(By.id("VAADIN_COMBOBOX_OPTIONLIST"));
        WebElement entry = popupElement.findElement(By.xpath(".//div[@class=\"v-filterselect-suggestmenu\"]/table/tbody/tr[" + text
                + "]//span"));
        entry.click();
    }

    @Override
    public String value(String entityName, String attribute) {
        WebElement element = driver.findElement(By.id(getId(entityName, attribute)));
        WebElement selectDropDown = element.findElement(By.xpath(".//div[@class=\"v-filterselect-button\"]"));
        selectDropDown.click();
        WebElement popupElement = driver.findElement(By.id("VAADIN_COMBOBOX_OPTIONLIST"));
        List<WebElement> list = popupElement.findElements(By.xpath(".//div[@class=\"v-filterselect-suggestmenu\"]/table/tbody/tr"));
        for (WebElement entry : list) {
            WebElement column = entry.findElement(By.xpath("./td"));
            if (column.getAttribute("class").contains("gwt-MenuItem-selected")) {
                return column.getText();
            }
        }
        return null;
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        WebElement element = driver.findElement(By.id(getId(entityName, attribute)));
        WebElement selectDropDown = element.findElement(By.xpath(".//div[@class=\"v-filterselect-button\"]"));
        selectDropDown.click();
        WaitConditions.waitForVaadin(driver);
        WebElement popupElement = driver.findElement(By.id("VAADIN_COMBOBOX_OPTIONLIST"));
        WebElement entry = popupElement.findElement(By.xpath(".//div[@class=\"v-filterselect-suggestmenu\"]/table/tbody/tr[" + text
                + "]/td"));
        assertTrue("Entry " + text + " of " + getId(entityName, attribute) + " must be selected",
                entry.getAttribute("class").contains("gwt-MenuItem-selected"));
        selectDropDown.click();
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-filterselect";
    }

}
