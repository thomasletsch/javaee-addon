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
package org.vaadin.addons.javaee.page;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.portal.PortalView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractContentView extends Panel implements ContentView {

    public static final int BUTTON_RATIO = 5;

    private static final long serialVersionUID = 1L;

    @Inject
    protected TranslationService translationService;

    private String pageName;

    private VerticalLayout mainPanel;

    protected AbstractContentView() {
    }

    public AbstractContentView(String pageName) {
        setId(pageName + "Panel");
        this.pageName = pageName;
    }

    @PostConstruct
    protected void initView() {
        setWidth(PortalView.CONTENT_WIDTH, Unit.PIXELS);
        setHeight(PortalView.CONTENT_HEIGHT, Unit.PIXELS);

        mainPanel = new VerticalLayout();
        mainPanel.setMargin(false);
        mainPanel.setSpacing(false);
        mainPanel.setSizeFull();
        mainPanel.setCaption(translationService.getText(getPageName()));
        setContent(mainPanel);
    }

    protected void addComponent(AbstractComponent component) {
        mainPanel.addComponent(component);
    }

    protected void addComponent(AbstractComponent component, float expandRatio) {
        mainPanel.addComponent(component);
        mainPanel.setExpandRatio(component, expandRatio);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // View init for showing is already done inside onShow.
    }

    /**
     * Can be overwritten. Called whenever a page is shown again to prepare.
     * 
     * @param comingFrom
     *            The page name from the old page
     * @param parameters
     *            Random parameters which can be forwarded from one page to another to trigger special handling
     */
    @Override
    public void onShow(String comingFrom, Map<String, Object> parameters) {
    }

    @Override
    public String getPageName() {
        return pageName;
    }

    @Override
    public boolean containsUnsavedValues() {
        return false;
    }

}
