package org.vaadin.addons.javaee.i18n;

import java.io.Serializable;
import java.util.Locale;

/**
 * Generic translation service. To extend it, just create a new TranslationSPI. The impl looks at all TranslationSPI implementations inside
 * the CDI container and uses them. The order cannot be specified.
 * 
 * @author contact@thomas-letsch.de
 * 
 */
public interface TranslationService extends Serializable {

    /**
     * Translates the given key.
     * 
     * If the key contains a "." it tries first to translate the whole key. If not found, it skips the first "word" (until first ".") and
     * tries again.
     * 
     * @return The translation if found. Otherwise the given key is returned.
     */
    public String get(String key);

    /**
     * Translates the given key and replaces {0-n} with the given params.
     * 
     * If the key contains a "." it tries first to translate the whole key. If not found, it skips the first "word" (until first ".") and
     * tries again.
     * 
     * @return The translation if found. Otherwise the given key is returned.
     */
    public String get(String key, Object... params);

    /**
     * Set the locale of the current session. This is done automatically from within the RootApplication.
     */
    public void setLocale(Locale locale);

}
