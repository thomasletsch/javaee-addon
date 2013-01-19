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
package org.vaadin.addons.javaee.fields.converter;

import java.util.Locale;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("rawtypes")
public class StringToEnumConverter implements Converter<String, Enum> {

    private static final long serialVersionUID = 1L;

    private TranslationService translationService;

    public StringToEnumConverter(TranslationService translationService) {
        this.translationService = translationService;
    }

    @Override
    public Enum convertToModel(String value, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(Enum value, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value == null) {
            return null;
        }
        return translationService.get(value.getClass().getSimpleName() + "." + value.name());
    }

    @Override
    public Class<Enum> getModelType() {
        return Enum.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
