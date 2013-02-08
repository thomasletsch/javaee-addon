package org.vaadin.addons.javaee.selenium;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataDriven {

    private static Logger log = LoggerFactory.getLogger(DataDriven.class);

    public DataDriven() {
        super();
    }

    public String getDefaultValue(String entityName, String attribute) {
        Properties properties = loadProperties(entityName, null);
        String value = (String) properties.get(attribute);
        return value;
    }

    protected Properties loadProperties(String entityName, String extension) {
        String propertyFile = entityName;
        if (!StringUtils.isBlank(extension)) {
            propertyFile = propertyFile + "_" + extension;
        }
        Properties properties = new Properties();
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(propertyFile + ".properties");
            properties.load(resourceAsStream);
        } catch (Exception e) {
            log.error("Could not load " + propertyFile + ".properties", e);
            throw new RuntimeException(e);
        }
        return properties;
    }

}