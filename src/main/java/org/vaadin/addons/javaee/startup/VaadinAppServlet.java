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

import javax.inject.Inject;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.javaee.fields.converter.ExtendedConverterFactory;

import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;

@WebServlet(urlPatterns = { "/vaadin/*", "/VAADIN/*" }, initParams = { @WebInitParam(name = "UI", value = "org.vaadin.addons.javaee.page.Portal") })
public class VaadinAppServlet extends VaadinServlet implements SessionInitListener {

    private static final long serialVersionUID = 1L;

    @Inject
    CDIProvider cdiProvider;

    @Inject
    ExtendedConverterFactory converterFactory;

    @Override
    protected void servletInitialized() {
        getService().addSessionInitListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        event.getSession().addUIProvider(cdiProvider);
    }

}