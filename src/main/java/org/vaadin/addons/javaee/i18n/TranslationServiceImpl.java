package org.vaadin.addons.javaee.i18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SessionScoped
public class TranslationServiceImpl implements TranslationService {

    private static Log log = LogFactory.getLog(TranslationServiceImpl.class);

    @Any
    @Inject
    Instance<TranslationSPI> providers;

    private Locale locale;

    public TranslationServiceImpl() {
        log.debug("Cosntructor called");
    }

    @Override
    public String get(String key) {
        List<String> variations = getPossibleKeyVariations(key);
        for (String variation : variations) {
            String translation = searchInAllProviders(variation);
            if (isTranslated(variation, translation)) {
                return translation;
            }
        }
        return key;
    }

    @Override
    public String get(String key, Object... params) {
        List<String> variations = getPossibleKeyVariations(key);
        for (String variation : variations) {
            String translation = searchInAllProviders(variation, params);
            if (isTranslated(variation, translation)) {
                return translation;
            }
        }
        return key;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    boolean isTranslated(String key, String translation) {
        return !key.equals(translation);
    }

    private String searchInAllProviders(String key) {
        for (TranslationSPI spi : providers) {
            String translation = spi.get(key, locale);
            if (isTranslated(key, translation)) {
                return translation;
            }
        }
        return key;
    }

    private String searchInAllProviders(String key, Object[] params) {
        for (TranslationSPI spi : providers) {
            String translation = spi.get(key, locale, params);
            if (isTranslated(key, translation)) {
                return translation;
            }
        }
        return key;
    }

    boolean checkKey(String key, Locale locale, TranslationSPI spi) {
        String translation = spi.get(key, locale);
        return !key.equals(translation);
    }

    List<String> getPossibleKeyVariations(String key) {
        List<String> result = new ArrayList<String>();
        result.add(key);
        int pos = key.indexOf(".");
        while (pos > 0) {
            key = key.substring(pos + 1);
            result.add(key);
            pos = key.indexOf(".");
        }
        return result;
    }

}
