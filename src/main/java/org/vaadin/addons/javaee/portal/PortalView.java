package org.vaadin.addons.javaee.portal;

import org.vaadin.addons.javaee.page.AbstractContentView;
import org.vaadin.virkki.cdiutils.mvp.View;

public interface PortalView extends View {

    void navigateTo(AbstractContentView portalPagePanel);

}
