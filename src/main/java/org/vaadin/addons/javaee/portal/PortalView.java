package org.vaadin.addons.javaee.portal;

import org.vaadin.addons.javaee.page.AbstractContentView;
import org.vaadin.virkki.cdiutils.mvp.View;

public interface PortalView extends View {

    public static final int HEIGHT = 800;

    public static final int WIDTH = 1000;

    public static final int DEFAULT_MARGIN = 5;

    public static final int SMALL_MARGIN = 2;

    public static final int DEFAULT_BORDER_WIDTH = 1;

    public static final int HEADER_HEIGHT = 30;

    public static final int MENU_WIDTH = 185;

    public static final int CONTENT_HEIGHT = HEIGHT - HEADER_HEIGHT - 7;

    public static final int CONTENT_WIDTH = WIDTH - MENU_WIDTH - 7;

    void navigateTo(AbstractContentView portalPagePanel);

}
