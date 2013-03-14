package org.vaadin.addons.javaee.fields;

import java.math.BigDecimal;

import org.vaadin.addons.javaee.fields.converter.StringToBigDecimalConverter;

import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.TextField;

public class NumberField extends TextField {

    private static final long serialVersionUID = 1L;

    public NumberField() {
    }

    public NumberField(String name, final String pattern) {
        super(name);
        setConverter(new StringToBigDecimalConverter(pattern));
    }

    public void setValue(BigDecimal newFieldValue) throws Property.ReadOnlyException, Converter.ConversionException {
        super.setValue(getConverter().convertToPresentation(newFieldValue, getLocale()));
    }

}
