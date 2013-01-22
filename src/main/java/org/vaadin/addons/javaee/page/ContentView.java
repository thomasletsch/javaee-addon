package org.vaadin.addons.javaee.page;

import org.vaadin.virkki.cdiutils.mvp.View;

public interface ContentView extends View {

    public abstract void onShow(String comingFrom);

    public abstract String getPageName();

    public abstract void prepareShow();

}