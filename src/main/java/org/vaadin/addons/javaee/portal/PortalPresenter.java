package org.vaadin.addons.javaee.portal;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.vaadin.addons.javaee.events.NavigationEvent;
import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.navigation.MenuItem;
import org.vaadin.addons.javaee.navigation.SideMenu;
import org.vaadin.addons.javaee.page.AbstractContentView;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.virkki.cdiutils.mvp.AbstractPresenter;
import org.vaadin.virkki.cdiutils.mvp.AbstractPresenter.ViewInterface;

import com.vaadin.ui.UI;

@ViewInterface(PortalView.class)
public class PortalPresenter extends AbstractPresenter<PortalView> {

    private static final long serialVersionUID = 1L;

    @Inject
    private SideMenu sideMenu;

    @Inject
    protected TranslationService translationService;

    private AbstractContentView actualContentView = null;

    @Override
    protected void initPresenter() {
    }

    @Override
    public void viewOpened() {
    }

    public void handleNavigation(@Observes final NavigationEvent navigationEvent) {
        if (actualContentView != null && actualContentView.containsUnsavedValues()) {
            ConfirmDialog.show(UI.getCurrent(), translationService.getText(TranslationKeys.TITLE_NAVIGATE),
                    translationService.getText(TranslationKeys.MESSAGE_REALLY_NAVIGATE), translationService.getText(TranslationKeys.YES),
                    translationService.getText(TranslationKeys.NO), new ConfirmDialog.Listener() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public void onClose(ConfirmDialog dialog) {
                            if (dialog.isConfirmed()) {
                                realHandleNavigation(navigationEvent);
                            } else {
                                sideMenu.selectMenu(actualContentView.getPageName());
                            }
                        }
                    });
        } else {
            realHandleNavigation(navigationEvent);
        }
    }

    public void realHandleNavigation(NavigationEvent navigationEvent) {
        MenuItem menuItem = sideMenu.getMenuItem(navigationEvent.getPageName());
        AbstractContentView newContentView = menuItem.getPanel();
        view.navigateTo(newContentView, navigationEvent.getParameters());
        sideMenu.selectMenu(menuItem.getName());
        actualContentView = newContentView;
    }
}
