package com.optible.vaadin.utils.i18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

public class TranslationServiceImpl implements TranslationService {

    @Any
    @Inject
    Instance<TranslationSPI> providers;

    @Override
    public String get(String key, Locale locale) {
        List<String> variations = getPossibleKeyVariations(key);
        for (String variation : variations) {
            if (checkKey(variation, locale)) {
                return getForAllTranslationProviders(variation, locale);
            }
        }
        return key;
    }

    @Override
    public String get(String key, Locale locale, Object... params) {
        List<String> variations = getPossibleKeyVariations(key);
        for (String variation : variations) {
            if (checkKey(variation, locale)) {
                return getForAllTranslationProviders(variation, locale, params);
            }
        }
        return key;
    }

    boolean checkKey(String variation, Locale locale) {
        return !variation.equals(getForAllTranslationProviders(variation, locale));
    }

    private String getForAllTranslationProviders(String key, Locale locale) {
        for (TranslationSPI spi : providers) {
            if (checkKey(key, locale, spi)) {
                return spi.get(key, locale);
            }
        }
        return key;
    }

    private String getForAllTranslationProviders(String key, Locale locale, Object[] params) {
        for (TranslationSPI spi : providers) {
            if (checkKey(key, locale, spi)) {
                return spi.get(key, locale, params);
            }
        }
        return key;
    }

    boolean checkKey(String key, Locale locale, TranslationSPI spi) {
        return !key.equals(spi.get(key, locale));
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
