package org.vaadin.addons.javaee.selenium.po;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BaseSideMenuPO extends BasePO {

    public static final String SECOND_LEVEL_TEMPLATE = "//div[@id='SideMenu']/div/div[{first}]/div[2]/div[{second}]/div[1]";

    public static final String THIRD_LEVEL_TEMPLATE = "//div[@id='SideMenu']/div/div[{first}]/div[2]/div[{second}]/div[2]/div[{third}]/div[1]";

    public static final String ELEMENT_POST_FIX = "/div/span";

    protected BaseSideMenuPO() {
    }

    public BaseSideMenuPO(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(Integer firstLevelPos, Integer secondLevelPos) {
        navigateTo(firstLevelPos, secondLevelPos, null);
    }

    public void navigateTo(Integer firstLevelPos, Integer secondLevelPos, Integer thirdLevelPos) {
        String xpath = buildXPath(firstLevelPos, secondLevelPos, thirdLevelPos) + ELEMENT_POST_FIX;
        WebElement element = driver.findElement(By.xpath(xpath));
        element.click();
    }

    public void assertMenuSelected(Integer firstLevelPos, Integer secondLevelPos, Integer thirdLevelPos) {
        String xpath = buildXPath(firstLevelPos, secondLevelPos, thirdLevelPos);
        WebElement element = driver.findElement(By.xpath(xpath));
        assertTrue("Entry " + firstLevelPos + "-" + secondLevelPos + "-" + thirdLevelPos + " must be selected",
                element.getAttribute("class").contains("v-tree-node-selected"));

    }

    String buildXPath(Integer firstLevelPos, Integer secondLevelPos, Integer thirdLevelPos) {
        String xpath = SECOND_LEVEL_TEMPLATE;
        if (thirdLevelPos != null) {
            xpath = THIRD_LEVEL_TEMPLATE.replace("{third}", thirdLevelPos.toString());
        }
        xpath = xpath.replace("{first}", firstLevelPos.toString());
        xpath = xpath.replace("{second}", secondLevelPos.toString());
        return xpath;
    }

    @Override
    protected String getIdentifyingElement() {
        return "SideMenu";
    }

}
