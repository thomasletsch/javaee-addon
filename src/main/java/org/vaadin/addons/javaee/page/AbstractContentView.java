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

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.virkki.cdiutils.mvp.AbstractView;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.VerticalLayout;

@Dependent
public abstract class AbstractContentView extends AbstractView implements ContentView {

    public static final String HEIGHT = "700px";

    public static final String WIDTH = "800px";

    public static final int FORM_RATIO = 6;

    public static final int BUTTON_RATIO = 1;

    public static final int TABLE_RATIO = 3;

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

    @Override
    protected void initView() {
        setWidth(WIDTH);
        setHeight(HEIGHT);

        mainPanel = new VerticalLayout();
        mainPanel.setMargin(true);
        mainPanel.setSpacing(true);
        mainPanel.setSizeFull();
        mainPanel.setCaption(translationService.getText(getPageName()));
        setCompositionRoot(mainPanel);
    }

    protected void addComponent(AbstractComponent component) {
        mainPanel.addComponent(component);
    }

    protected void addComponent(AbstractComponent component, float expandRatio) {
        mainPanel.addComponent(component);
        mainPanel.setExpandRatio(component, expandRatio);
    }

    @Override
    public void onShow(String comingFrom) {
    }

    @Override
    public String getPageName() {
        return pageName;
    }

    @Override
    public void prepareShow() {
    }

}
