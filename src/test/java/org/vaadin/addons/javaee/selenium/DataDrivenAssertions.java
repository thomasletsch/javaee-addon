package org.vaadin.addons.javaee.selenium;

import java.util.Properties;

public class DataDrivenAssertions extends DataDriven {

    private SeleniumAssertions assertions;

    public DataDrivenAssertions(SeleniumAssertions assertions) {
        this.assertions = assertions;
    }

    public void assertDefaultValues(String entityName) {
        assertDefaultValues(entityName, null);
    }

    public void assertDefaultValues(String entityName, String extension) {
        Properties properties = loadProperties(entityName, extension);
        for (Object obj : properties.keySet()) {
            String attribute = (String) obj;
            assertions.assertText(entityName, attribute, properties.getProperty(attribute));
        }
    }

    public void assertDefaultValue(String entityName, String extension, String attribute) {
        Properties properties = loadProperties(entityName, extension);
        assertions.assertText(entityName, attribute, properties.getProperty(attribute));
    }

}
