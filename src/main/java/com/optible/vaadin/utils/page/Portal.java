package com.optible.vaadin.utils.page;

import static com.optible.vaadin.utils.TranslationKeys.*;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.optible.vaadin.utils.events.NavigationEvent;
import com.optible.vaadin.utils.i18n.TranslationService;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Root;
import com.vaadin.ui.VerticalSplitPanel;

public abstract class Portal extends Root {

    public static final int DEFAULT_MARGIN = 5;

    public static final int SMALL_MARGIN = 2;

    private static final int DEFAULT_BORDER_WIDTH = 1;

    private static final int SCREEN_WIDTH = 1024;

    public static final int PAGE_WIDTH = SCREEN_WIDTH - (DEFAULT_MARGIN * 2) - (DEFAULT_BORDER_WIDTH * 2);

    private static final int HEADER_WIDTH = PAGE_WIDTH;

    private static final int HEADER_HEIGHT = 30;

    public static final int MENU_WIDTH = 185;

    @Inject
    protected TranslationService translationService;

    protected Layout mainPanel;

    private Label title;

    private String oldPageName = null;

    @Inject
    protected SideMenu menu;

    @Override
    protected void init(WrappedRequest request) {
        initLayout();
        initMenu();
    }

    private void initLayout() {
        VerticalSplitPanel page = new VerticalSplitPanel();
        page.setStyleName("default-panel");
        page.addComponent(createHeader());
        HorizontalSplitPanel innerLayout = new HorizontalSplitPanel();
        page.addComponent(innerLayout);
        page.setSplitPosition(HEADER_HEIGHT + DEFAULT_MARGIN, Unit.PIXELS);
        mainPanel = new HorizontalLayout();
        mainPanel.setWidth(PAGE_WIDTH, Unit.PIXELS);
        mainPanel.setHeight(100, Unit.PERCENTAGE);
        innerLayout.setSizeFull();
        innerLayout.addComponent(createMenuPanel());
        innerLayout.addComponent(mainPanel);
        innerLayout.setSplitPosition(MENU_WIDTH + DEFAULT_MARGIN, Unit.PIXELS);
        setContent(page);
        setCaption(translationService.get(TITLE_PORTAL));
    }

    private Layout createHeader() {
        HorizontalLayout head = new HorizontalLayout();
        head.setWidth(HEADER_WIDTH, Unit.PIXELS);
        head.setHeight(HEADER_HEIGHT, Unit.PIXELS);
        title = new Label(translationService.get(TITLE_PORTAL));
        head.addComponent(title);
        return head;
    }

    private Panel createMenuPanel() {
        Panel menuContainer = new Panel();
        menuContainer.setWidth(MENU_WIDTH, Unit.PIXELS);
        menuContainer.addComponent(menu);
        initMenu();
        return menuContainer;
    }

    protected abstract void initMenu();

    public Layout getMainPanel() {
        return mainPanel;
    }

    public void handleNavigation(@Observes NavigationEvent navigationEvent) {
        menu.setValue(navigationEvent.getPageName());
    }

    public void navigateTo(PortalPagePanel portalPagePanel) {
        mainPanel.removeAllComponents();
        mainPanel.addComponent(portalPagePanel);
        portalPagePanel.onShow(oldPageName);
        oldPageName = portalPagePanel.getPageName();
    }
}
