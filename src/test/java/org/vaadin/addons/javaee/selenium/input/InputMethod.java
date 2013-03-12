package org.vaadin.addons.javaee.selenium.input;

public interface InputMethod {

    void input(String entityName, String attribute, String text);

    String value(String entityName, String attribute);

    void assertInput(String entityName, String attribute, String text);

    boolean accepts(String entityName, String attribute);
}
