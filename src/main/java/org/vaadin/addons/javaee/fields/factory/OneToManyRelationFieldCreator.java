package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.OneToManyRelationField;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.AbstractField;

public class OneToManyRelationFieldCreator<FIELD extends AbstractField<?>> extends AbstractFieldCreator<FIELD> {

    public OneToManyRelationFieldCreator(EntityContainer<?> container, FieldSpecification fieldSpec) {
        super(container, fieldSpec);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) OneToManyRelationField.class;
    }

}
