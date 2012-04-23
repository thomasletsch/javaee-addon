package com.optible.vaadin.utils.fields;

import java.text.NumberFormat;
import java.util.Currency;

import com.vaadin.data.util.BeanItem;

public class NumberFormatFactory {

    @SuppressWarnings("rawtypes")
    public static NumberFormat getNumberFormat(final String currencyField, BeanItem beanItem) {
        String currencyCode = getCurrencyCode(currencyField, beanItem);
        NumberFormat fmt = getNumberFormat(currencyCode);
        return fmt;
    }

    public static NumberFormat getNumberFormat(String currencyCode) {
        NumberFormat fmt = null;
        if (currencyCode != null) {
            fmt = NumberFormat.getCurrencyInstance();
            fmt.setCurrency(Currency.getInstance(currencyCode));
        } else {
            fmt = NumberFormat.getCurrencyInstance();
        }
        return fmt;
    }

    @SuppressWarnings("rawtypes")
    public static String getCurrencyCode(final String currencyField, BeanItem beanItem) {
        String value = (String) beanItem.getItemProperty(currencyField).getValue();
        return value;
    }

}
