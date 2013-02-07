package org.vaadin.addons.javaee.selenium;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataDrivenActions {

    private static Logger log = LoggerFactory.getLogger(DataDrivenActions.class);

    private SeleniumActions actions;

    public DataDrivenActions(SeleniumActions actions) {
        this.actions = actions;
    }

    public void select(String entityName, String attribute) {
        actions.select(entityName, attribute, getDefaultValue(entityName, attribute));
    }

    public void typeText(String entityName, String attribute) {
        actions.typeText(entityName, attribute, getDefaultValue(entityName, attribute));
    }

    public void typeText(String entityName, String subEntityName, String attribute) {
        actions.typeText(entityName, subEntityName, attribute, getDefaultValue(entityName, subEntityName, attribute));
    }

    public void typeDate(String entityName, String attribute) {
        actions.typeDate(entityName, attribute, getDefaultValue(entityName, attribute));
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
