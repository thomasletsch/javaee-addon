package org.vaadin.addons.javaee.fields;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;

public class ExtendedConverterFactory extends DefaultConverterFactory {

    @Inject
    private TranslationService translationService;

    @Override
    protected Converter<String, ?> createStringConverter(Class<?> sourceType) {
        if (Calendar.class.isAssignableFrom(sourceType))
            return new StringToCalenderConverter();
        if (Enum.class.isAssignableFrom(sourceType))
            return new StringToEnumConverter(translationService);
        return super.createStringConverter(sourceType);
    }

    @Override
    protected Converter<Date, ?> createDateConverter(Class<?> sourceType) {
        if (Calendar.class.isAssignableFrom(sourceType))
            return new DateToCalenderConverter();
        return super.createDateConverter(sourceType);
    }
}
