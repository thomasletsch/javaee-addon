/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
