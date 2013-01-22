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

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.virkki.cdiutils.componentproducers.Preconfigured;
import org.vaadin.virkki.cdiutils.mvp.AbstractView;

import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalSplitPanel;

public class PortalViewImpl extends AbstractView implements PortalView {

    private static final long serialVersionUID = 6788886405321379139L;

    public static final int DEFAULT_MARGIN = 5;

    public static final int SMALL_MARGIN = 2;

    public static final int DEFAULT_BORDER_WIDTH = 1;

    public static final int HEADER_HEIGHT = 30;

    public static final int MENU_WIDTH = 185;

    @Inject
    @Preconfigured
    private HorizontalSplitPanel horizontalSplit;

    @Inject
    protected TranslationService translationService;

    protected Layout mainPanel;

    private String oldPageName = null;

    @Inject
    protected Instance<SideMenu> menu;

    @Inject
    protected Instance<Header> header;

    @Override
    protected void initView() {
        setSizeFull();

        final VerticalSplitPanel mainLayout = new VerticalSplitPanel();
        setCompositionRoot(mainLayout);
        mainLayout.setSizeFull();
        header.get().init();
        mainLayout.setFirstComponent(header.get());
        mainLayout.setSplitPosition(HEADER_HEIGHT, Unit.PIXELS);
        mainLayout.setSecondComponent(horizontalSplit);

        menu.get().init();
        horizontalSplit.setFirstComponent(menu.get());
        horizontalSplit.setSplitPosition(MENU_WIDTH, Unit.PIXELS);

        Page.getCurrent().setTitle(translationService.getText(TITLE_PORTAL));
    }

    @Override
    public void navigateTo(AbstractContentView portalPagePanel) {
        horizontalSplit.setSecondComponent(portalPagePanel);
        portalPagePanel.openView();
        portalPagePanel.onShow(oldPageName);
        oldPageName = portalPagePanel.getPageName();
    }
}
