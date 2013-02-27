package org.vaadin.addons.javaee.fields.factory;

import javax.validation.constraints.Digits;

import org.apache.commons.lang.StringUtils;
import org.vaadin.addons.javaee.fields.NumberField;
import org.vaadin.addons.javaee.fields.converter.StringToBigDecimalConverter;

public class DecimalFieldCreator<FIELD extends NumberField> extends NumberFieldCreator<FIELD> {

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        Digits digits = container.getAnnotation(fieldSpec.getName(), Digits.class);
        if (digits != null) {
            String pattern = "0" + ((digits.fraction() > 0) ? "." + StringUtils.repeat("0", digits.fraction()) : "");
            field.setConverter(new StringToBigDecimalConverter(pattern));
        }
    }

}
