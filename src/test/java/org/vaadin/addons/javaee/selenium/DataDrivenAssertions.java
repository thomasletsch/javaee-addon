package org.vaadin.addons.javaee.selenium;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataDrivenAssertions {

    private static Logger log = LoggerFactory.getLogger(DataDrivenAssertions.class);

    private SeleniumAssertions assertions;

    public DataDrivenAssertions(SeleniumAssertions assertions) {
        this.assertions = assertions;
    }

    public void assertSelect(String entityName, String attribute) {
        assertions.assertSelect(entityName, attribute, getDefaultValue(entityName, attribute));
    }

    public void assertText(String entityName, String attribute) {
        assertions.assertText(entityName, attribute, getDefaultValue(entityName, attribute));
    }

    public void assertText(String entityName, String subEntityName, String attribute) {
        assertions.assertText(entityName, subEntityName, attribute, getDefaultValue(entityName, subEntityName, attribute));
    }

    public void assertDate(String entityName, String attribute) {
        assertions.assertDate(entityName, attribute, getDefaultValue(entityName, attribute));
    }

    public String getDefaultValue(String entityName, String attribute) {
        return getDefaultValue(entityName, null, attribute);
    }

    String getDefaultValue(String entityName, String subEntityName, String attribute) {
        Properties properties = new Properties();
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(entityName + ".properties");
            properties.load(resourceAsStream);
        } catch (IOException e) {
            log.error("Could not load " + entityName + ".properties", e);
            throw new RuntimeException(e);
        }
        String value = (String) properties.get(((subEntityName != null) ? subEntityName + "." : "") + attribute);
        return value;
    }

}
