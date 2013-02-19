package org.vaadin.addons.javaee.selenium;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class DataDrivenActions extends DataDriven {

    private SeleniumActions actions;

    public DataDrivenActions(SeleniumActions actions) {
        this.actions = actions;
    }

    public void setDefaultValues(String entityName) {
        setDefaultValues(entityName, null);
    }

    public void setDefaultValues(String entityName, String extension) {
        Properties properties = loadProperties(entityName, extension);
        for (Object obj : properties.keySet()) {
            String attribute = (String) obj;
            String property = properties.getProperty(attribute);
            if (!StringUtils.isBlank(property)) {
                actions.input(entityName, attribute, property);
            }
        }
    }

    public void setDefaultValues(String entityName, String extension, String attribute) {
        Properties properties = loadProperties(entityName, extension);
        actions.input(entityName, attribute, properties.getProperty(attribute));
    }

}
