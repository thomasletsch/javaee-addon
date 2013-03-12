package org.vaadin.addons.javaee.i18n;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.vaadin.virkki.cdiutils.application.UIContext.UIScoped;

@UIScoped
public class SelectedLocale implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale locale;

    @PostConstruct
    public void setDefaultLocale() {
        locale = Locale.getDefault();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
