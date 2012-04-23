package org.vaadin.addons.javaee.startup;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.vaadin.addons.javaee.fields.ExtendedConverterFactory;

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