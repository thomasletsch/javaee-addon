package org.vaadin.addons.javaee.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePagePO extends BasePO {

    public BasePagePO(WebDriver driver) {
        super(driver);
    }

    public BasePagePO(WebDriver driver, boolean assertPage) {
        super(driver, assertPage);
    }

    protected List<WebElement> getTableRows(String tableName) {
        return driver.findElements(By.xpath("//div[@id='" + tableName + "']//div[contains(@class, 'v-table-body')]//tr"));
    }

    /**
     * Gets the text of the column.
     * 
     * @param column
     *            the column number starting by 1
     */
    protected String getTableColumn(WebElement tableRow, int column) {
        String text = tableRow.findElement(By.xpath("./td[" + column + "]/div")).getText();
        return text;
    }

    /**
     * Gets the text of the column.
     * 
     * @param column
     *            the column number starting by 1
     */
    protected String getInputTableColumn(WebElement tableRow, int column) {
        String text = tableRow.findElement(By.xpath("./td[" + column + "]/div/input")).getAttribute("value");
        return text;
    }

}
