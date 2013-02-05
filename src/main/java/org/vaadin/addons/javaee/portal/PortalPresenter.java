package org.vaadin.addons.javaee.portal;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.vaadin.addons.javaee.events.NavigationEvent;
import org.vaadin.addons.javaee.navigation.MenuItem;
import org.vaadin.addons.javaee.navigation.SideMenu;
import org.vaadin.virkki.cdiutils.mvp.AbstractPresenter;
import org.vaadin.virkki.cdiutils.mvp.AbstractPresenter.ViewInterface;

@ViewInterface(PortalView.class)
public class PortalPresenter extends AbstractPresenter<PortalView> {

    private static final long serialVersionUID = 1L;

    @Inject
    private SideMenu sideMenu;

    @Override
    protected void initPresenter() {
    }

    @Override
    public void viewOpened() {
    }

    public void handleNavigation(@Observes NavigationEvent navigationEvent) {
        MenuItem menuItem = sideMenu.getMenuItem(navigationEvent.getPageName());
        view.navigateTo(menuItem.getPanel());
        sideMenu.selectMenu(menuItem.getName());
    }
}
