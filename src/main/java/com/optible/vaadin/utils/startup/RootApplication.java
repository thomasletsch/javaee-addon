package com.optible.vaadin.utils.startup;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.optible.vaadin.utils.page.Portal;
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

    @Override
    protected Root getRoot(WrappedRequest request) throws RootRequiresMoreInformationException {
        return portal;
    }

    @Override
    public void terminalError(ErrorEvent event) {
        log.error("Error", event.getThrowable());
    }
}