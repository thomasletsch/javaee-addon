/*******************************************************************************
 * Copyright 2012 Thomas Letsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package org.vaadin.addons.javaee.navigation;

import javax.enterprise.inject.Instance;

import org.vaadin.addons.javaee.page.AbstractContentView;

public class MenuItem {

    private String parent;

    private String name;

    private String title;

    private boolean enabled;

    private Instance<? extends AbstractContentView> panel;

    public MenuItem(String name, String title, Instance<? extends AbstractContentView> panel) {
        this(null, name, title, panel, true);
    }

    public MenuItem(String parent, String name, String title, Instance<? extends AbstractContentView> panel) {
        this(parent, name, title, panel, true);
    }

    public MenuItem(String name, String title, Instance<? extends AbstractContentView> panel, boolean enabled) {
        this(null, name, title, panel, enabled);
    }

    public MenuItem(String parent, String name, String title, Instance<? extends AbstractContentView> panel, boolean enabled) {
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

    public AbstractContentView getPanel() {
        return panel.get();
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
