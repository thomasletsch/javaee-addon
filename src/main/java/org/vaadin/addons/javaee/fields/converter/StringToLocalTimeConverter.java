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
package org.vaadin.addons.javaee.fields.converter;

import java.util.Locale;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import com.vaadin.data.util.converter.Converter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    private static final long serialVersionUID = 1L;

    @Override
    public LocalTime convertToModel(String value, Class<? extends LocalTime> targetType, Locale locale)
            throws Converter.ConversionException {
        if (value == null) {
            return null;
        }
        return LocalTime.parse(value);
    }

    @Override
    public String convertToPresentation(LocalTime value, Class<? extends String> targetType, Locale locale)
            throws Converter.ConversionException {
        if (value == null) {
            return null;
        }
        return DateTimeFormat.shortTime().print(value);
    }

    @Override
    public Class<LocalTime> getModelType() {
        return LocalTime.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
