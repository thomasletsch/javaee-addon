/*******************************************************************************
 * Copyright 2012 Thomas Letsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
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