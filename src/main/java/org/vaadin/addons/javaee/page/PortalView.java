package org.vaadin.addons.javaee.page;

import org.vaadin.virkki.cdiutils.mvp.View;

public interface PortalView extends View {

    void navigateTo(AbstractContentView portalPagePanel);

}
