package org.vaadin.addons.javaee.fields.factory;

import javax.validation.constraints.Digits;

import org.apache.commons.lang.StringUtils;
import org.vaadin.addons.javaee.fields.NumberField;
import org.vaadin.addons.javaee.fields.converter.StringToBigDecimalConverter;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.EntityContainer;

public class DecimalFieldCreator<FIELD extends NumberField> extends NumberFieldCreator<FIELD> {

    public DecimalFieldCreator(EntityContainer<?> container, FieldSpecification fieldSpec) {
        super(container, fieldSpec);
    }

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        Digits digits = container.getAnnotation(fieldName, Digits.class);
        if (digits != null) {
            String pattern = "0" + ((digits.fraction() > 0) ? "." + StringUtils.repeat("0", digits.fraction()) : "");
            field.setConverter(new StringToBigDecimalConverter(pattern));
        }
    }

}
