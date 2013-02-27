package org.vaadin.addons.javaee.page;

import java.util.Map;

import org.vaadin.virkki.cdiutils.mvp.View;

public interface ContentView extends View {

    public abstract void onShow(String comingFrom, Map<String, Object> parameters);

    public abstract String getPageName();

    public abstract void prepareShow();

}