/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.vaadin.addons.javaee.portal;

import static org.vaadin.addons.javaee.portal.PortalView.*;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.navigation.MenuItem;
import org.vaadin.addons.javaee.navigation.NavigationEvent;
import org.vaadin.addons.javaee.navigation.SideMenu;
import org.vaadin.addons.javaee.page.AbstractContentView;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.Reindeer;

@UIScoped
public class PortalPage extends Panel {

    private static final long serialVersionUID = 6788886405321379139L;

    @Inject
    protected TranslationService translationService;

    private AbstractContentView actualContentView = null;

    @Inject
    protected SideMenu menu;

    @Inject
    protected Header header;

    private VerticalSplitPanel mainPanel = new VerticalSplitPanel();

    private HorizontalSplitPanel menuAndContentPanel = new HorizontalSplitPanel();

    private Panel contentPanel = new Panel();

    @PostConstruct
    public void initView() {
        setStyleName(Reindeer.LAYOUT_WHITE);
        setWidth(WIDTH, Unit.PIXELS);
        setHeight(HEIGHT, Unit.PIXELS);

        contentPanel.setSizeFull();

        menuAndContentPanel.setSizeFull();
        menuAndContentPanel.setStyleName(Reindeer.SPLITPANEL_SMALL);
        menuAndContentPanel.setSplitPosition(MENU_WIDTH, Unit.PIXELS);
        menuAndContentPanel.setLocked(true);
        menuAndContentPanel.setFirstComponent(menu);
        menuAndContentPanel.setSecondComponent(contentPanel);

        mainPanel.setSizeFull();
        mainPanel.setStyleName(Reindeer.SPLITPANEL_SMALL);
        mainPanel.setSplitPosition(HEADER_HEIGHT, Unit.PIXELS);
        mainPanel.setLocked(true);
        mainPanel.setFirstComponent(header);
        mainPanel.setSecondComponent(menuAndContentPanel);
        setContent(mainPanel);
    }

    public Panel getMainPanel() {
        return contentPanel;
    }

    public void handleNavigation(@Observes final NavigationEvent navigationEvent) {
        if (actualContentView != null && actualContentView.containsUnsavedValues()) {
            ConfirmDialog.show(UI.getCurrent(), translationService.getText(TranslationKeys.TITLE_NAVIGATE),
                    translationService.getText(TranslationKeys.MESSAGE_REALLY_NAVIGATE), translationService.getText(TranslationKeys.YES),
                    translationService.getText(TranslationKeys.NO), new ConfirmDialog.Listener() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public void onClose(ConfirmDialog dialog) {
                            if (dialog.isConfirmed()) {
                                realHandleNavigation(navigationEvent);
                            } else {
                                menu.selectMenu(actualContentView.getPageName());
                            }
                        }
                    });
        } else {
            realHandleNavigation(navigationEvent);
        }
    }

    public void realHandleNavigation(NavigationEvent navigationEvent) {
        MenuItem menuItem = menu.getMenuItem(navigationEvent.getPageName());
        AbstractContentView newContentView = menuItem.getPanel();
        if (newContentView == null) {
            return;
        }
        newContentView.onShow((actualContentView == null) ? null : actualContentView.getPageName(), navigationEvent.getParameters());
        menu.selectMenu(menuItem.getName());
        UI.getCurrent().getNavigator().navigateTo(navigationEvent.getPageName());
        actualContentView = newContentView;
    }

}
