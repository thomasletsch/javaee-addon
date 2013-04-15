package org.vaadin.addons.javaee.selenium.po;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.vaadin.addons.javaee.selenium.WaitConditions;

/**
 * PO for the confirm-dialog-addon
 * 
 * @author thomas.letsch.de@gmail.com
 * 
 */
public class ConfirmDialogPO {

    private WebDriver driver;

    public ConfirmDialogPO(WebDriver driver) {
        this.driver = driver;
    }

    public String getWindowTitle() {
        return driver.findElement(By.xpath("//div[contains(@class, 'v-window ')]//div[contains(@class, 'v-window-header')]")).getText();
    }

    public void clickOKButton() {
        List<WebElement> buttons = driver.findElements(By.xpath("//div[contains(@class, 'v-window ')]//div[contains(@class, 'v-button')]"));
        WebElement okButton = buttons.get(1);
        okButton.click();
        WaitConditions.waitForVaadin(driver);
    }

    public void clickCancelButton() {
        List<WebElement> buttons = driver.findElements(By.xpath("//div[contains(@class, 'v-window ')]//div[contains(@class, 'v-button')]"));
        WebElement cancelButton = buttons.get(0);
        cancelButton.click();
        WaitConditions.waitForVaadin(driver);
    }

}
