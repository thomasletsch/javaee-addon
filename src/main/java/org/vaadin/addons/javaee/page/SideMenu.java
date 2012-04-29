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

    private HashMap<String, Instance<? extends PortalPagePanel>> panels = new HashMap<>();

    public SideMenu() {
        setSizeFull();
        setImmediate(true);
        setItemCaptionMode(ItemCaptionMode.EXPLICIT);
        setDebugId("SideMenu");
        addListener(new ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (event.getProperty() != null && event.getProperty().getValue() != null) {
                    String pageName = (String) event.getProperty().getValue();
                    PortalPagePanel panel = panels.get(pageName).get();
                    portal.navigateTo(panel);
                }
            }
        });
    }

    public void addMenu(String pageName, Instance<? extends PortalPagePanel> panel) {
        panels.put(pageName, panel);
        addItem(pageName);
        setItemCaption(pageName, translationService.get(MENU_ITEM_PREFIX + pageName));
    }

    public void addMenu(String parent, String pageName, Instance<? extends PortalPagePanel> panel) {
        panels.put(pageName, panel);
        addItem(pageName);
        setItemCaption(pageName, translationService.get(MENU_ITEM_PREFIX + pageName));
        setParent(pageName, parent);
        setChildrenAllowed(pageName, false);
        expandItem(parent);
    }

    public PortalPagePanel getPanel(String pageName) {
        return panels.get(pageName).get();
    }

}
