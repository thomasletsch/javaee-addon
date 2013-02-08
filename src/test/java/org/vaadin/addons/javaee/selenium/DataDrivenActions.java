package org.vaadin.addons.javaee.selenium;

import java.util.Properties;

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
            actions.input(entityName, attribute, properties.getProperty(attribute));
        }
    }

    public void setDefaultValues(String entityName, String extension, String attribute) {
        Properties properties = loadProperties(entityName, extension);
        actions.input(entityName, attribute, properties.getProperty(attribute));
    }

}
