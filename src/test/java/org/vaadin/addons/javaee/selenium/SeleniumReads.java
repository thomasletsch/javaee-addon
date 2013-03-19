package org.vaadin.addons.javaee.selenium;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Collection of different methods to read specific data from the page. Standard elements can be read with the help of the
 * InputMethodFactory.
 * 
 * @author thomas
 * 
 */
public class SeleniumReads {

    private WebDriver driver;

    public SeleniumReads(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getTableRows(String tableName) {
        return driver.findElements(By.xpath("//div[@id='" + tableName + "']//div[contains(@class, 'v-table-body')]//tr"));
    }

    /**
     * Gets the text of the column.
     * 
     * @param column
     *            the column number starting by 1
     */
    public String getTableColumn(WebElement tableRow, int column) {
        String text = tableRow.findElement(By.xpath("./td[" + column + "]/div")).getText();
        return text;
    }

    /**
     * Gets the text of the column.
     * 
     * @param column
     *            the column number starting by 1
     */
    public String getInputTableColumn(WebElement tableRow, int column) {
        String text = tableRow.findElement(By.xpath("./td[" + column + "]/div/input")).getAttribute("value");
        return text;
    }

    /**
     * Gets the value of the table cell identified by row and column. Checks for normal text first and if not found checks for input field
     * with value.
     * 
     * @param tableId
     *            id of the table
     * @param row
     *            row starting with 1
     * @param column
     *            column starting with 1
     */
    public String getTableCellText(String tableId, int row, int column) {
        String xpath = "//div[@id='" + tableId + "']//div[contains(@class, 'v-table-body')]//tr[" + row + "]/td[" + column + "]/div";
        String text = driver.findElement(By.xpath(xpath)).getText();
        if (StringUtils.isBlank(text)) {
            xpath = xpath + "/input";
            List<WebElement> elements = driver.findElements(By.xpath(xpath));
            if (elements.size() == 1) {
                return elements.get(0).getAttribute("value");
            }
        }
        return text;
    }

}
