package org.vaadin.addons.javaee.selenium;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePO {

    protected WebDriver driver;

    protected SeleniumActions actions;

    protected DataDrivenActions dataActions;

    protected SeleniumAssertions assertions;

    protected DataDrivenAssertions dataAssertions;

    protected BasePO() {
    }

    public BasePO(WebDriver driver) {
        this.driver = driver;
        actions = new SeleniumActions(driver);
        dataActions = new DataDrivenActions(actions);
        assertions = new SeleniumAssertions(driver);
        dataAssertions = new DataDrivenAssertions(assertions);
        assertPage();
    }

    protected abstract String getIdentifyingElement();

    public void assertPage() {
        assertTrue("Failure while asserting page " + getClass().getSimpleName() + ". Element " + getIdentifyingElement() + " not found!",
                driver.findElements(By.id(getIdentifyingElement())).size() == 1);
    }

}