package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.OneToOneRelationField;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.AbstractField;

public class OneToOneRelationFieldCreator<FIELD extends AbstractField<?>> extends AbstractFieldCreator<FIELD> {

    public OneToOneRelationFieldCreator(EntityContainer<?> container, FieldSpecification fieldSpec) {
        super(container, fieldSpec);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) OneToOneRelationField.class;
    }

}
