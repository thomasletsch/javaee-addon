package org.vaadin.addons.javaee.i18n;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class SelectedLocale implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale locale;

    @PostConstruct
    public void SetDefaultLocale() {
        locale = Locale.getDefault();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
