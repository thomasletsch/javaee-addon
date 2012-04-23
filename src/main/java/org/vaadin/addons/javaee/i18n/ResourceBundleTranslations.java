package org.vaadin.addons.javaee.i18n;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class ResourceBundleTranslations implements TranslationSPI {

    private Map<Locale, ResourceBundle> bundles = new HashMap<Locale, ResourceBundle>();

    private final String bundleName;

    public ResourceBundleTranslations(String bundleName) {
        this.bundleName = bundleName;
    }

    @Override
    public String get(String key, Locale locale) {
        if (getBundle(locale).containsKey(key)) {
            return getBundle(locale).getString(key);
        }
        return key;
    }

    @Override
    public String get(String key, Locale locale, Object... params) {
        String translation = get(key, locale);
        translation = MessageFormat.format(translation, params);
        return translation;
    }

    public ResourceBundle getBundle(Locale locale) {
        ResourceBundle bundle = bundles.get(locale);
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(bundleName, locale);
            bundles.put(locale, bundle);
        }
        return bundle;
    }

}