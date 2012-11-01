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

import static org.vaadin.addons.javaee.TranslationKeys.TITLE_PORTAL;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.vaadin.addons.javaee.events.NavigationEvent;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;

public abstract class Portal extends UI {

	private static final long serialVersionUID = 6788886405321379139L;

	public static final int DEFAULT_MARGIN = 5;

    public static final int SMALL_MARGIN = 2;

    public static final int DEFAULT_BORDER_WIDTH = 1;

    public static final int SCREEN_WIDTH = 1024;

    public static final int PAGE_WIDTH = SCREEN_WIDTH - (DEFAULT_MARGIN * 2) - (DEFAULT_BORDER_WIDTH * 2);

    public static final int HEADER_WIDTH = PAGE_WIDTH;

    public static final int HEADER_HEIGHT = 30;

    public static final int MENU_WIDTH = 185;

    @Inject
    protected TranslationService translationService;

    protected Layout mainPanel;

    private String oldPageName = null;

    @Inject
    protected SideMenu menu;

    protected Header header;

    @Override
    protected void init(VaadinRequest request) {
        initLayout();
        initMenu();
    }

    private void initLayout() {
        VerticalSplitPanel page = new VerticalSplitPanel();
        page.setStyleName("default-panel");
        page.addComponent(createHeader());
        HorizontalSplitPanel innerLayout = new HorizontalSplitPanel();
        page.addComponent(innerLayout);
        page.setSplitPosition(HEADER_HEIGHT + DEFAULT_MARGIN, Unit.PIXELS);
        mainPanel = new HorizontalLayout();
        mainPanel.setWidth(PAGE_WIDTH, Unit.PIXELS);
        mainPanel.setHeight(100, Unit.PERCENTAGE);
        innerLayout.setSizeFull();
        innerLayout.addComponent(createMenuPanel());
        innerLayout.addComponent(mainPanel);
        innerLayout.setSplitPosition(MENU_WIDTH + DEFAULT_MARGIN, Unit.PIXELS);
        setContent(page);
        Page.getCurrent().setTitle(translationService.get(TITLE_PORTAL));
    }

    protected Header createHeader() {
        header = new Header();
        header.setTitle(translationService.get(TITLE_PORTAL));
        return header;
    }

    private Panel createMenuPanel() {
        Panel menuContainer = new Panel();
        menuContainer.setWidth(MENU_WIDTH, Unit.PIXELS);
        menuContainer.addComponent(menu);
        initMenu();
        return menuContainer;
    }

    protected abstract void initMenu();

    public Layout getMainPanel() {
        return mainPanel;
    }

    public void handleNavigation(@Observes NavigationEvent navigationEvent) {
        menu.setValue(navigationEvent.getPageName());
    }

    public void navigateTo(PortalPagePanel portalPagePanel) {
        mainPanel.removeAllComponents();
        mainPanel.addComponent(portalPagePanel);
        portalPagePanel.onShow(oldPageName);
        oldPageName = portalPagePanel.getPageName();
    }
}
