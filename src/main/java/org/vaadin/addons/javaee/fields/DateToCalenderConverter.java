package org.vaadin.addons.javaee.fields;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class DateToCalenderConverter implements Converter<Date, Calendar> {

    @Override
    public Calendar convertToModel(Date value, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(value);
        return calendar;
    }

    @Override
    public Date convertToPresentation(Calendar value, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value == null) {
            return null;
        }
        return value.getTime();
    }

    @Override
    public Class<Calendar> getModelType() {
        return Calendar.class;
    }

    @Override
    public Class<Date> getPresentationType() {
        return Date.class;
    }

}
