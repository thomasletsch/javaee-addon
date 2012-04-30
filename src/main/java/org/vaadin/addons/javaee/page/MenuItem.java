package org.vaadin.addons.javaee.page;

import javax.enterprise.inject.Instance;

public class MenuItem {

    private String parent;

    private String name;

    private String title;

    private boolean enabled;

    private Instance<? extends PortalPagePanel> panel;

    public MenuItem(String name, String title, Instance<? extends PortalPagePanel> panel) {
        this(null, name, title, panel, true);
    }

    public MenuItem(String parent, String name, String title, Instance<? extends PortalPagePanel> panel) {
        this(parent, name, title, panel, true);
    }

    public MenuItem(String name, String title, Instance<? extends PortalPagePanel> panel, boolean enabled) {
        this(null, name, title, panel, enabled);
    }

    public MenuItem(String parent, String name, String title, Instance<? extends PortalPagePanel> panel, boolean enabled) {
        this.parent = parent;
        this.name = name;
        this.title = title;
        this.panel = panel;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Instance<? extends PortalPagePanel> getPanel() {
        return panel;
    }

    public String getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void enable() {
        enabled = true;
    }

}
