package org.vaadin.addons.javaee.startup;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.addons.javaee.page.Portal;

import com.vaadin.server.DefaultUIProvider;
import com.vaadin.server.UICreateEvent;
import com.vaadin.ui.UI;

public class CDIProvider extends DefaultUIProvider {

    private static final long serialVersionUID = 1L;

    @Inject
    private Instance<Portal> portal;

    @Override
    @SuppressWarnings("unchecked")
    public UI createInstance(UICreateEvent event) {
        Class<Portal> uiClass = (Class<Portal>) event.getUIClass();
        return portal.select(uiClass).get();
    }

}
