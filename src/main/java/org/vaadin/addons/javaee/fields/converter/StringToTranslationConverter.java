package org.vaadin.addons.javaee.fields.converter;

import java.util.Locale;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.data.util.converter.Converter;

public class StringToTranslationConverter implements Converter<String, String> {

    private static final long serialVersionUID = 1L;

    private TranslationService translationService;

    public StringToTranslationConverter(TranslationService translationService) {
        this.translationService = translationService;
    }

    @Override
    public String convertToModel(String value, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(String value, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
        return translationService.getText(value);
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
