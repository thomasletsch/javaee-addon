package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.OneToOneRelationField;

@SuppressWarnings("rawtypes")
public class OneToOneRelationFieldCreator extends AbstractFieldCreator<OneToOneRelationField> {

    @Override
    protected Class<OneToOneRelationField> getDefaultFieldType() {
        return OneToOneRelationField.class;
    }

}
