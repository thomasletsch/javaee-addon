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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

@UIScoped
public class Header extends Panel {

    private static final long serialVersionUID = 1L;

    public static final String ID = "Header";

    @Inject
    protected TranslationService translationService;

    private HorizontalLayout layout;

    private Label title;

    public Header() {
        setId(ID);
    }

    @PostConstruct
    public void init() {
        title = new Label(translationService.getText(TranslationKeys.TITLE_PORTAL));
        title.setId("PortalTitle");
        title.setStyleName("portal_title");
        layout = new HorizontalLayout();
        layout.setStyleName("toolbar");
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.addComponent(title);
        setContent(layout);
    }

    public void setTitle(String newTitle) {
        title.setValue(newTitle);
    }

    public void addComponent(Component component) {
        layout.addComponent(component);
    }
}
