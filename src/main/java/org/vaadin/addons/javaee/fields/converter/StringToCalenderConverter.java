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

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.vaadin.data.util.converter.Converter;

public class StringToCalenderConverter implements Converter<String, Calendar> {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_PATTERN = "dd.MM.yyyy HH:mm:ss z";

    public static final String DATE_ONLY_PATTERN = "dd.MM.yyyy";

    private String pattern = DEFAULT_PATTERN;

    private Map<Locale, DateFormat> cachedFormats = new HashMap<Locale, DateFormat>();

    public StringToCalenderConverter() {
    }

    public StringToCalenderConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Calendar convertToModel(String value, Locale locale) throws Converter.ConversionException {
        if (value == null) {
            return null;
        }

        // Remove leading and trailing white space
        value = value.trim();

        ParsePosition parsePosition = new ParsePosition(0);
        Calendar calendar = Calendar.getInstance(locale);
        Date parsedValue = getFormat(locale).parse(value, parsePosition);
        if (parsePosition.getIndex() != value.length()) {
            throw new ConversionException("Could not convert '" + value + "' to " + getModelType().getName());
        }
        calendar.setTime(parsedValue);
        return calendar;
    }

    @Override
    public String convertToPresentation(Calendar value, Locale locale) throws Converter.ConversionException {
        if (value == null) {
            return null;
        }

        return getFormat(locale).format(value.getTime());
    }

    @Override
    public Class<Calendar> getModelType() {
        return Calendar.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

    protected DateFormat getFormat(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }

        DateFormat f = getOrCreateFormat(locale);
        f.setLenient(false);
        return f;
    }

    private DateFormat getOrCreateFormat(Locale locale) {
        if (!cachedFormats.containsKey(locale)) {
            cachedFormats.put(locale, new SimpleDateFormat(pattern, locale));
        }
        return cachedFormats.get(locale);
    }

}
