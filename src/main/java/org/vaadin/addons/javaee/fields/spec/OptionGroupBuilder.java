package org.vaadin.addons.javaee.fields.spec;

import com.vaadin.ui.OptionGroup;

public class OptionGroupBuilder extends FieldSpecBuilder {

    public OptionGroupBuilder(String fieldName) {
        super(fieldName);
        type(OptionGroup.class);
    }
}