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
package org.vaadin.addons.javaee.startup;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.page.Portal;

import com.vaadin.Application;
import com.vaadin.RootRequiresMoreInformationException;
import com.vaadin.terminal.Terminal.ErrorEvent;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.Root;

@SessionScoped
public class RootApplication extends Application {

    private static Log log = LogFactory.getLog(RootApplication.class);

    @Inject
    private Portal portal;

    @Inject
    private TranslationService translationService;

    @Override
    protected Root getRoot(WrappedRequest request) throws RootRequiresMoreInformationException {
        return portal;
    }

    @Override
    public void terminalError(ErrorEvent event) {
        log.error("Error", event.getThrowable());
    }

    @PostConstruct
    public void setLocale() {
        translationService.setLocale(getLocale());
    }
}