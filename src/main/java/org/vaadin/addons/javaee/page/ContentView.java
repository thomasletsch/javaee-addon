package org.vaadin.addons.javaee.page;

import java.util.Map;

import org.vaadin.virkki.cdiutils.mvp.View;

/**
 * Main interface for content panel (central window part)
 * 
 * @author thomas
 * 
 */
public interface ContentView extends View {

    /**
     * Called every time the content view is accessed by menu (or other navigation)
     */
    void onShow(String comingFrom, Map<String, Object> parameters);

    /**
     * Returns a unique name for the page
     */
    String getPageName();

    /**
     * Should return true is there are unsaved values on the page. Should check all forms for unsaved values.
     * 
     * Used for displaying the user a dialog if he really wants to leave and loose unsaved values.
     */
    boolean containsUnsavedValues();
}