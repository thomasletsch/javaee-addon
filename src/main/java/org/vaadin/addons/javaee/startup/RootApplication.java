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