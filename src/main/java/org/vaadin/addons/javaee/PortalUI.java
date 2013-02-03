package org.vaadin.addons.javaee;

import javax.inject.Inject;

import org.vaadin.addons.javaee.events.NavigationEvent;
import org.vaadin.addons.javaee.fields.converter.ExtendedConverterFactory;
import org.vaadin.addons.javaee.i18n.SelectedLocale;
import org.vaadin.addons.javaee.navigation.SideMenu;
import org.vaadin.addons.javaee.page.PortalViewImpl;
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
