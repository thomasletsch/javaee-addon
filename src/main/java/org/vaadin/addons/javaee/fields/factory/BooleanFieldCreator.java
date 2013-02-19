package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.CheckBox;

public class BooleanFieldCreator extends AbstractFieldCreator<CheckBox> {

    public BooleanFieldCreator(EntityContainer<?> container, FieldSpecification fieldSpec) {
        super(container, fieldSpec);
    }

    @Override
    protected Class<CheckBox> getDefaultFieldType() {
        return CheckBox.class;
    }

}
