package org.vaadin.addons.javaee.selenium;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class DataDrivenActionsTest {

    private DataDrivenActions underTest = new DataDrivenActions(null);

    @Test
    public void testGetDefaultValue() {
        assertNotNull("firstName", underTest.getDefaultValue("Customer", "firstName"));
    }

}
