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
    public void input(String id, String text) {
        WebElement element = driver.findElement(By.xpath("//div[@id='" + id + "']/span[" + text + "]/input"));
        element.click();
    }

    @Override
    public String value(String id) {
        List<WebElement> options = driver.findElements(By.xpath("//div[@id='" + id + "']/span"));
        for (WebElement webElement : options) {
            WebElement element = webElement.findElement(By.xpath("./input"));
            if (element.getAttribute("checked") != null) {
                return webElement.findElement(By.xpath("./label")).getText();
            }
        }
        return null;
    }

    @Override
    public void assertInput(String id, String text) {
        if (StringUtils.isBlank(text)) {
            return;
        }
        WebElement element = driver.findElement(By.xpath("//div[@id='" + id + "']/span[" + text + "]/input"));
        assertNotNull("Radio Button at pos " + text + " of " + id + " must be checked", element.getAttribute("checked"));
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-select-optiongroup";
    }

}
