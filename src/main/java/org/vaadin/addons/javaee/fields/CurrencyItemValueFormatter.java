package org.vaadin.addons.javaee.fields;

import java.text.NumberFormat;


public class CurrencyItemValueFormatter {

    private final String currency;

    public CurrencyItemValueFormatter(String currency) {
        this.currency = currency;
    }

    public String formatValue(Object value) {
        NumberFormat fmt = NumberFormatFactory.getNumberFormat(currency);
        return format(value, fmt);
    }

    public String format(Object value, NumberFormat fmt) {
        if (value instanceof String) {
            return (String) value;
        }
        Number strValue = (Number) value;
        String newValue = null;
        if (value == null) {
            newValue = fmt.format(0);
        } else {
            newValue = fmt.format(strValue);
        }
        return newValue;
    }
}
