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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.vaadin.addons.javaee.fields.converter.ExtendedConverterFactory;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

@WebServlet(urlPatterns = { "/vaadin/*", "/VAADIN/*" })
public class VaadinAppServlet extends AbstractApplicationServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    RootApplication application;

    @Inject
    ExtendedConverterFactory converterFactory;

    @Override
    protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
        return RootApplication.class;
    }

    @Override
    protected Application getNewApplication(HttpServletRequest request) throws ServletException {
        application.setConverterFactory(converterFactory);
        return application;
    }

}