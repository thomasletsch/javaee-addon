package org.vaadin.addons.javaee.container;

public class SortDefinition {

    private String key;

    private boolean ascending = true;

    public SortDefinition(String key, boolean ascending) {
        this.key = key;
        this.ascending = ascending;
    }

    public String getKey() {
        return key;
    }

    public boolean isAscending() {
        return ascending;
    }
}
