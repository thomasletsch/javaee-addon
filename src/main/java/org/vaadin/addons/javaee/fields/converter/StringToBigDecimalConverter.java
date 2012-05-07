package org.vaadin.addons.javaee.fields.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

    @Override
    public BigDecimal convertToModel(String value, Locale locale) throws Converter.ConversionException {
        if (value == null)
            return null;
        value = value.replace(",", ".");
        BigDecimal result = new BigDecimal(value);
        return result;
    }

    @Override
    public String convertToPresentation(BigDecimal value, Locale locale) throws Converter.ConversionException {
        if (value == null)
            return null;
        NumberFormat nf = NumberFormat.getInstance(locale);
        return nf.format(value.doubleValue());
    }

    @Override
    public Class<BigDecimal> getModelType() {
        return BigDecimal.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
