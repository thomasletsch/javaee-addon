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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SessionScoped
public abstract class ResourceBundleTranslations implements Serializable, TranslationSPI {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(ResourceBundleTranslations.class);

    private Map<Locale, ResourceBundle> bundles = new HashMap<Locale, ResourceBundle>();

    protected abstract String getBundleName();

    @Override
    public String get(String key, Locale locale) {
        if (getBundle(locale).containsKey(key)) {
            return getBundle(locale).getString(key);
        }
        return key;
    }

    public ResourceBundle getBundle(Locale locale) {
        if (!bundles.containsKey(locale)) {
            loadBundle(locale);
        }
        ResourceBundle bundle = bundles.get(locale);
        return bundle;
    }

    protected void loadBundle(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(getBundleName(), locale);
            bundles.put(locale, bundle);
        } catch (MissingResourceException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("/" + getBundleName(), locale);
            bundles.put(locale, bundle);
        }
    }

}