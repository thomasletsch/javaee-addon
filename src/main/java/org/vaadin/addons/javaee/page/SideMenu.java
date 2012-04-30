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
package org.vaadin.addons.javaee.page;

import static org.vaadin.addons.javaee.TranslationKeys.*;

import java.util.HashMap;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.data.Property;
import com.vaadin.ui.Tree;

/*
 * Testing:
 * 
 * Second-Level-Menu-Item: 
 *      //div[@id='SideMenu']//div/div[{section}]/div[2]/div[{subSection}]/div/div/span
 * where section = row of first level item starting by 1 and subSection = row of second level item starting by 1
 */
public class SideMenu extends Tree {

    @Inject
    private Portal portal;

    @Inject
    private TranslationService translationService;

    private HashMap<String, MenuItem> panels = new HashMap<>();

    public SideMenu() {
        setSizeFull();
        setImmediate(true);
        setItemCaptionMode(ItemCaptionMode.EXPLICIT);
        setDebugId("SideMenu");
        addNavigationListener();
        addDisabledStyleGenerator();
    }

    private void addDisabledStyleGenerator() {
        setItemStyleGenerator(new Tree.ItemStyleGenerator() {

            @Override
            public String getStyle(Object itemId) {
                String pageName = (String) itemId;
                MenuItem item = panels.get(pageName);
                if (!item.isEnabled()) {
                    return "disabled";
                } else {
                    return null;
                }
            }
        });
    }

    private void addNavigationListener() {

        addListener(new ValueChangeListener() {

            Object previous = null;

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (event.getProperty() == null || event.getProperty().getValue() == null) {
                    return;
                }
                String pageName = (String) event.getProperty().getValue();
                MenuItem item = panels.get(pageName);
                navigateToPageIfEnabled(item);
            }

            private void navigateToPageIfEnabled(MenuItem item) {
                if (item.isEnabled()) {
                    portal.navigateTo(item.getPanel().get());
                    previous = getValue();
                } else {
                    setValue(previous);
                }
            }
        });
    }

    public void addMenu(String pageName, Instance<? extends PortalPagePanel> panel) {
        MenuItem item = new MenuItem(pageName, translationService.get(MENU_ITEM_PREFIX + pageName), panel);
        addMenu(item);
    }

    public void addMenu(String parent, String pageName, Instance<? extends PortalPagePanel> panel) {
        MenuItem item = new MenuItem(parent, pageName, translationService.get(MENU_ITEM_PREFIX + pageName), panel);
        addMenu(item);
    }

    public void addMenu(String pageName, Instance<? extends PortalPagePanel> panel, boolean enabled) {
        MenuItem item = new MenuItem(pageName, translationService.get(MENU_ITEM_PREFIX + pageName), panel, enabled);
        addMenu(item);
    }

    public void addMenu(String parent, String pageName, Instance<? extends PortalPagePanel> panel, boolean enabled) {
        MenuItem item = new MenuItem(parent, pageName, translationService.get(MENU_ITEM_PREFIX + pageName), panel, enabled);
        addMenu(item);
    }

    private void addMenu(MenuItem item) {
        panels.put(item.getName(), item);
        addItem(item.getName());
        setItemCaption(item.getName(), translationService.get(MENU_ITEM_PREFIX + item.getName()));
        if (item.hasParent()) {
            setParent(item.getName(), item.getParent());
            setChildrenAllowed(item.getName(), false);
            expandItem(item.getParent());
        }
    }

    public void enableAll() {
        for (MenuItem item : panels.values()) {
            item.enable();
        }
        fireValueChange(false);
    }

    public PortalPagePanel getPanel(String pageName) {
        return panels.get(pageName).getPanel().get();
    }

}
