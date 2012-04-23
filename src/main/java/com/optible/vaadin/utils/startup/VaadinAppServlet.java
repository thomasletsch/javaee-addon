package com.optible.vaadin.utils.startup;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.optible.vaadin.utils.fields.ExtendedConverterFactory;
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