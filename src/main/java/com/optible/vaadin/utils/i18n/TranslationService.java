package com.optible.vaadin.utils.i18n;

import java.util.Locale;

import javax.inject.Singleton;

/**
 * Generic translation service. To extend it, just create a new TranslationSPI. The impl looks at all TranslationSPI implementations inside
 * the CDI container and uses them. The order cannot be specified.
 * 
 * @author contact@thomas-letsch.de
 * 
 */
@Singleton
public interface TranslationService {

    /**
     * Translates the given key.
     * 
     * If the key contains a "." it tries first to translate the whole key. If not found, it skips the first "word" (until first ".") and
     * tries again.
     * 
     * @return The translation if found. Otherwise the given key is returned.
     */
    public String get(String key, Locale locale);

    /**
     * Translates the given key and replaces {0-n} with the given params.
     * 
     * If the key contains a "." it tries first to translate the whole key. If not found, it skips the first "word" (until first ".") and
     * tries again.
     * 
     * @return The translation if found. Otherwise the given key is returned.
     */
    public String get(String key, Locale locale, Object... params);

}
