package org.vaadin.addons.javaee.events;

public class NavigationEvent {

    private String pageName;

    public NavigationEvent(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}
