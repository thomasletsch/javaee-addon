package org.vaadin.addons.javaee.selenium.input;

public interface InputMethod {

    void input(String id, String text);

    void input(String entityName, String attribute, String text);

    String value(String entityName, String attribute);

    String value(String id);

    void assertInput(String entityName, String attribute, String text);

    void assertInput(String id, String text);

    boolean accepts(String entityName, String attribute);

    boolean accepts(String id);
}
