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

import static org.vaadin.addons.javaee.i18n.TranslationKeys.MENU_ITEM_PREFIX;

import java.util.HashMap;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.addons.javaee.events.NavigationEvent;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.page.AbstractContentView;
import org.vaadin.addons.javaee.portal.PortalViewImpl;
import org.vaadin.virkki.cdiutils.application.UIContext.UIScoped;
import org.vaadin.virkki.cdiutils.componentproducers.Preconfigured;
import org.vaadin.virkki.cdiutils.mvp.ViewComponent;

import com.vaadin.data.Property;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.ItemStyleGenerator;

/*
 * Testing:
 * 
 * Second-Level-Menu-Item: 
 *      //div[@id='SideMenu']//div/div[{section}]/div[2]/div[{subSection}]/div/div/span
 * where section = row of first level item starting by 1 and subSection = row of second level item starting by 1
 */
@UIScoped
public class SideMenu extends ViewComponent {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("cdi-ambiguous-dependency")
    @Inject
    @Preconfigured(nullSelectionAllowed = false, immediate = false, id = "SideMenu")
    private Tree tree;

    @Inject
    private Instance<PortalViewImpl> portal;

    @Inject
    private TranslationService translationService;

    @Inject
    javax.enterprise.event.Event<NavigationEvent> navigation;

    private HashMap<String, MenuItem> panels = new HashMap<>();

    private ValueChangeListenerImplementation listener;

    public SideMenu() {
    }

    public void init() {
        tree.setSelectable(true);
        tree.setImmediate(true);
        setCompositionRoot(tree);
        listener = new ValueChangeListenerImplementation();
        tree.addValueChangeListener(listener);
        addDisabledStyleGenerator();
    }

    public MenuItem getMenuItem(String pageName) {
        return panels.get(pageName);
    }

    public void selectMenu(String menuName) {
        tree.removeValueChangeListener(listener);
        tree.setValue(menuName);
        tree.addValueChangeListener(listener);
    }

    public void addMenu(String pageName, Instance<? extends AbstractContentView> panel) {
        MenuItem item = new MenuItem(pageName, translationService.getText(MENU_ITEM_PREFIX + pageName), panel);
        addMenu(item);
    }

    public void addMenu(String parent, String pageName, Instance<? extends AbstractContentView> panel) {
        MenuItem item = new MenuItem(parent, pageName, translationService.getText(MENU_ITEM_PREFIX + pageName), panel);
        addMenu(item);
    }

    public void addMenu(String pageName, Instance<? extends AbstractContentView> panel, boolean enabled) {
        MenuItem item = new MenuItem(pageName, translationService.getText(MENU_ITEM_PREFIX + pageName), panel, enabled);
        addMenu(item);
    }

    public void addMenu(String parent, String pageName, Instance<? extends AbstractContentView> panel, boolean enabled) {
        MenuItem item = new MenuItem(parent, pageName, translationService.getText(MENU_ITEM_PREFIX + pageName), panel, enabled);
        addMenu(item);
    }

    public void enableAll() {
        for (MenuItem item : panels.values()) {
            item.enable();
        }
        tree.markAsDirty();
    }

    private void addMenu(MenuItem item) {
        panels.put(item.getName(), item);
        tree.addItem(item.getName());
        tree.setItemCaption(item.getName(), item.getTitle());
        if (item.hasParent()) {
            tree.setChildrenAllowed(item.getParent(), true);
            tree.setParent(item.getName(), item.getParent());
            tree.setChildrenAllowed(item.getName(), false);
            tree.expandItem(item.getParent());
        }
    }

    private void addDisabledStyleGenerator() {
        tree.setItemStyleGenerator(new ItemStyleGenerator() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getStyle(Tree tree, Object itemId) {
                String pageName = (String) itemId;
                MenuItem item = panels.get(pageName);
                if (!item.isEnabled()) {
                    return "disabled";
                }
                return null;
            }
        });
    }

    private final class ValueChangeListenerImplementation implements Property.ValueChangeListener {

        private static final long serialVersionUID = 1L;

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
                navigation.fire(new NavigationEvent(item.getName()));
                previous = tree.getValue();
            } else {
                tree.setValue(previous);
            }
        }
    }

}
