package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OptionGroupInputMethod extends AbstractInputMethod {

    public OptionGroupInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String entityName, String attribute, String text) {
        WebElement element = driver.findElement(By.xpath("//div[@id='" + getId(entityName, attribute) + "']/span[" + text + "]/input"));
        element.click();
    }

    @Override
    public String value(String entityName, String attribute) {
        List<WebElement> options = driver.findElements(By.xpath("//div[@id='" + getId(entityName, attribute) + "']/span"));
        for (WebElement webElement : options) {
            WebElement element = webElement.findElement(By.xpath("./input"));
            if (element.getAttribute("checked") != null) {
                return webElement.findElement(By.xpath("./label")).getText();
            }
        }
        return null;
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        if (StringUtils.isBlank(text)) {
            return;
        }
        WebElement element = driver.findElement(By.xpath("//div[@id='" + getId(entityName, attribute) + "']/span[" + text + "]/input"));
        assertNotNull("Radio Button at pos " + text + " of " + getId(entityName, attribute) + " must be checked",
                element.getAttribute("checked"));
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-select-optiongroup";
    }

}
