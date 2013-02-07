package org.vaadin.addons.javaee.selenium;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BaseSideMenuPOTest {

    @Test
    public void testBuildXPath() {
        BaseSideMenuPO po = new BaseSideMenuPO() {
        };
        String xpath = po.buildXPath(1, 2, 3);
        assertEquals("//div[@id='SideMenu']/div/div/div/div[2]/div[2]/div/div[3]/div/div/div/span", xpath);
    }

}
