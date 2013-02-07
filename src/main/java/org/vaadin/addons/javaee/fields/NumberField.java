package org.vaadin.addons.javaee.fields;

import org.vaadin.addons.javaee.fields.converter.StringToBigDecimalConverter;

import com.vaadin.ui.TextField;

public class NumberField extends TextField {

    private static final long serialVersionUID = 1L;

    public NumberField() {
    }

    public NumberField(String name, final String pattern) {
        super(name);
        setConverter(new StringToBigDecimalConverter(pattern));
    }

}
