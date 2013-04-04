package org.vaadin.addons.javaee.selenium;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.selenium.input.InputMethod;
import org.vaadin.addons.javaee.selenium.input.InputMethodFactory;

/**
 * Collection of different methods to read specific data from the page. Standard elements can be read with the help of the
 * InputMethodFactory.
 * 
 * @author thomas
 * 
 */
public class SeleniumReads {

    private static Logger log = LoggerFactory.getLogger(SeleniumReads.class);

    private WebDriver driver;

    private InputMethodFactory factory;

    public SeleniumReads(WebDriver driver) {
        this.driver = driver;
        factory = new InputMethodFactory(driver);
    }

    public List<WebElement> getTableRows(String tableName) {
        String xpath = "//div[@id='" + tableName + "']//div[contains(@class, 'v-table-body')]//tr";
        return driver.findElements(By.xpath(xpath));
    }

    /**
     * Gets the text of the column.
     * 
     * @param column
     *            the column number starting by 1
     */
    public String getTableCellText(WebElement tableRow, int column) {
        String text = tableRow.findElement(By.xpath("./td[" + column + "]/div")).getText();
        return text;
    }

    /**
     * Gets the text of the column. For input cells.
     * 
     * @param column
     *            the column number starting by 1
     */
    public String getTableInputCellText(String tableId, int row, int column) {
        WaitConditions.waitForVaadin(driver);
        try {
            // I hate this, but in this case waitForVaadin does not seem to work.
            Thread.sleep(100);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        By by = By.xpath(getTableCellXPath(tableId, row, column));
        String id = getAttributeSave(by, "id");
        InputMethod inputMethod = factory.get(id);
        return inputMethod.value(id);
    }

    /**
     * Gets the element of the column.
     * 
     * @param column
     *            the column number starting by 1
     */
    public WebElement getTableCell(WebElement tableRow, int column) {
        WebElement element = tableRow.findElement(By.xpath("./td[" + column + "]/div"));
        return element;
    }

    /**
     * Gets the element of the column. For input cells.
     * 
     * @param column
     *            the column number starting by 1
     */
    public WebElement getTableInputCell(String tableId, int row, int column) {
        String xpath = getTableCellXPath(tableId, row, column);
        WebElement element = driver.findElement(By.xpath(xpath));
        return element;
    }

    private String getTableCellXPath(String tableId, int row, int column) {
        return "//div[@id='" + tableId + "']//div[contains(@class, 'v-table-body')]//tr[" + row + "]/td[" + column + "]/div/*[1]";
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
        WaitConditions.waitForVaadin(driver);
        String text = getTextSave(By.xpath(xpath));
        if (StringUtils.isBlank(text)) {
            xpath = xpath + "/input";
            List<WebElement> elements = driver.findElements(By.xpath(xpath));
            if (elements.size() == 1) {
                String id = elements.get(0).getAttribute("id");
                if (!StringUtils.isBlank(id)) {
                    InputMethod inputMethod = factory.get(id);
                    return inputMethod.value(id);
                }
                return elements.get(0).getAttribute("value");
            }
        }
        return text;
    }

    /**
     * A {@link StaleElementReferenceException} save version of getAttribute
     */
    public String getAttributeSave(final By by, final String attribute) {
        String text = null;
        FluentWait<WebDriver> wait = new WebDriverWait(driver, WaitConditions.SHORT_WAIT_MS, WaitConditions.SHORT_SLEEP_MS)
                .ignoring(StaleElementReferenceException.class);
        text = wait.until(new ExpectedCondition<String>() {

            @Override
            public String apply(WebDriver driver) {
                return driver.findElement(by).getAttribute(attribute);
            }
        });
        return text;
    }

    /**
     * A {@link StaleElementReferenceException} save version of getText
     */
    public String getTextSave(final By by) {
        String text = null;
        FluentWait<WebDriver> wait = new WebDriverWait(driver, WaitConditions.SHORT_WAIT_MS, WaitConditions.SHORT_SLEEP_MS)
                .ignoring(StaleElementReferenceException.class);
        text = wait.until(new ExpectedCondition<String>() {

            @Override
            public String apply(WebDriver driver) {
                return driver.findElement(by).getText();
            }
        });
        return text;
    }
}
