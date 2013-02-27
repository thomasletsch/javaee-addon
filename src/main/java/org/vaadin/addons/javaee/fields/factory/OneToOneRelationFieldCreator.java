package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.OneToOneRelationField;

import com.vaadin.ui.AbstractField;

public class OneToOneRelationFieldCreator<FIELD extends AbstractField<?>> extends AbstractFieldCreator<FIELD> {

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) OneToOneRelationField.class;
    }

}
