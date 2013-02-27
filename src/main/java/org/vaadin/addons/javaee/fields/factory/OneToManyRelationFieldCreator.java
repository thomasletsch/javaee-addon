package org.vaadin.addons.javaee.fields.factory;

import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.OneToManyRelationField;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public class OneToManyRelationFieldCreator<ENTITY extends PersistentEntity, FIELD extends OneToManyRelationField<ENTITY>> extends
        AbstractFieldCreator<FIELD> {

    @Inject
    private JavaEEFieldFactory fieldFactory;

    @Override
    @SuppressWarnings("unchecked")
    protected void initializeField(FIELD field) {
        field.setEntityClass((Class<ENTITY>) container.getCollectionType(fieldSpec.getName()));
        field.setFieldFactory(fieldFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) OneToManyRelationField.class;
    }

}
