package com.optible.vaadin.utils.page;

import static com.optible.vaadin.utils.TranslationKeys.*;

import java.util.HashMap;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.optible.vaadin.utils.i18n.TranslationService;
import com.vaadin.data.Property;
import com.vaadin.ui.Tree;

/*
 * Testing:
 * 
 * First First-Level-Menu-Item: 
 *      //div[@id='SideMenu']/div/div/div[1]/div/span
 * 
 * First Second-Level-Menu-Item: 
 *      //div[@id='SideMenu']/div/div/div[2]/div/div/div/span
 * 
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
