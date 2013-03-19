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
package org.vaadin.addons.javaee;

import javax.inject.Inject;

import org.vaadin.addons.javaee.events.NavigationEvent;
import org.vaadin.addons.javaee.fields.converter.ExtendedConverterFactory;
import org.vaadin.addons.javaee.i18n.SelectedLocale;
import org.vaadin.addons.javaee.navigation.SideMenu;
import org.vaadin.addons.javaee.portal.PortalViewImpl;
import org.vaadin.virkki.cdiutils.application.UIContext.UIScoped;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@UIScoped
public abstract class PortalUI extends UI {

    private static final long serialVersionUID = 1L;

    @Inject
    protected PortalViewImpl mainView;

    @Inject
    protected SelectedLocale selectedLocale;

    @Inject
    protected SideMenu menu;

    @Inject
    protected javax.enterprise.event.Event<NavigationEvent> navigation;

    @Inject
    private ExtendedConverterFactory extendedConverterFactory;

    @Override
    protected void init(VaadinRequest request) {
        getSession().setConverterFactory(extendedConverterFactory);
        setLocale(selectedLocale.getLocale());
        setContent(mainView);
        mainView.openView();
        initPortal();
    }

    protected abstract void initPortal();
}
