package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.vaadin.addons.javaee.selenium.WaitConditions;

/**
 * text can be either the row number in the drop down menu (starting by 1) or part of the text
 * 
 * @author thomas.letsch.de@gmail.com
 * 
 */
public class DropDownInputMethod extends AbstractInputMethod {

    public DropDownInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String id, String text) {
        openDropDownMenu(id);
        WebElement entry = getDropDownElement(text);
        entry.click();
    }

    @Override
    public String value(String id) {
        openDropDownMenu(id);
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
    public void assertInput(String id, String text) {
        WebElement selectDropDown = openDropDownMenu(id);
        WaitConditions.waitForVaadin(driver);
        WebElement entry = getDropDownElement(text);
        assertTrue("Entry " + text + " of " + id + " must be selected", entry.getAttribute("class").contains("gwt-MenuItem-selected"));
        selectDropDown.click();
    }

    private WebElement openDropDownMenu(String id) {
        WebElement element = driver.findElement(By.id(id));
        WebElement selectDropDown = element.findElement(By.xpath(".//div[@class=\"v-filterselect-button\"]"));
        selectDropDown.click();
        return selectDropDown;
    }

    private WebElement getDropDownElement(String text) {
        WebElement entry = null;
        if (StringUtils.isNumeric(text)) {
            entry = driver.findElement(By.xpath("//div[@id='VAADIN_COMBOBOX_OPTIONLIST']"
                    + "//div[@class=\"v-filterselect-suggestmenu\"]/table/tbody/tr[" + text + "]//span"));
        } else {
            entry = driver.findElement(By.xpath("//div[@id='VAADIN_COMBOBOX_OPTIONLIST']"
                    + "//div[@class='v-filterselect-suggestmenu']/table/tbody//span[contains(., '" + text + "')]"));
        }
        return entry;
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-filterselect";
    }

}
