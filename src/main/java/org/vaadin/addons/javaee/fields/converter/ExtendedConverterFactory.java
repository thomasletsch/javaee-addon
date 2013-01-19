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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;

public class ExtendedConverterFactory extends DefaultConverterFactory {

    private static final long serialVersionUID = 1L;

    @Inject
    private TranslationService translationService;

    @Override
    protected Converter<String, ?> createStringConverter(Class<?> sourceType) {
        if (Calendar.class.isAssignableFrom(sourceType)) {
            return new StringToCalenderConverter();
        }
        if (Enum.class.isAssignableFrom(sourceType)) {
            return new StringToEnumConverter(translationService);
        }
        if (BigDecimal.class.isAssignableFrom(sourceType)) {
            return new StringToBigDecimalConverter();
        }
        return super.createStringConverter(sourceType);
    }

    @Override
    protected Converter<Date, ?> createDateConverter(Class<?> sourceType) {
        if (Calendar.class.isAssignableFrom(sourceType)) {
            return new DateToCalenderConverter();
        }
        return super.createDateConverter(sourceType);
    }
}
