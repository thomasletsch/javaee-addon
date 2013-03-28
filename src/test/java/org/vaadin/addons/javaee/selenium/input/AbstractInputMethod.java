package org.vaadin.addons.javaee.selenium.input;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.vaadin.addons.javaee.selenium.WaitConditions;

public abstract class AbstractInputMethod implements InputMethod {

    protected WebDriver driver;

    public AbstractInputMethod(WebDriver driver) {
        this.driver = driver;
    }

    protected abstract String getElementClassAttribute();

    @Override
    public void input(String entityName, String attribute, String text) {
        String id = getId(entityName, attribute);
        input(id, text);
    }

    @Override
    public String value(String entityName, String attribute) {
        String id = getId(entityName, attribute);
        return value(id);
    }

    @Override
    public void assertInput(String entityName, String attribute, String text) {
        String id = getId(entityName, attribute);
        assertInput(id, text);
    }

    @Override
    public boolean accepts(String entityName, String attribute) {
        String id = getId(entityName, attribute);
        return accepts(id);
    }

    @Override
    public boolean accepts(String id) {
        WaitConditions.waitForVaadin(driver);
        WebElement element = driver.findElement(By.id(id));
        return element.getAttribute("class").contains(getElementClassAttribute());
    }

    protected String getId(String entityName, String attribute) {
        if (StringUtils.isBlank(entityName) && StringUtils.isBlank(attribute)) {
            throw new IllegalArgumentException("entityName and attribute are NULL");
        } else if (StringUtils.isBlank(entityName)) {
            return attribute;
        } else if (StringUtils.isBlank(attribute)) {
            return entityName;
        } else {
            return entityName + "." + attribute;
        }
    }

}
