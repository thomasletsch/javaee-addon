package org.vaadin.addons.javaee.selenium.input;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextInputMethod extends AbstractInputMethod {

    public TextInputMethod(WebDriver driver) {
        super(driver);
    }

    @Override
    public void input(String id, String text) {
        WebElement element = saveElementAccess.getElementSave(By.id(id));
        element.clear();
        element.sendKeys(text);
    }

    @Override
    public String value(String id) {
        WebElement element = saveElementAccess.getElementSave(By.id(id));
        return element.getAttribute("value");
    }

    @Override
    public void assertInput(String id, String text) {
        WebElement element = saveElementAccess.getElementSave(By.id(id));
        assertEquals(id, text, element.getAttribute("value"));
    }

    @Override
    protected String getElementClassAttribute() {
        return "v-textfield";
    }

}
