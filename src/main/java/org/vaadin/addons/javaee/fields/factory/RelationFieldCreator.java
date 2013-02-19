package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.RelationField;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public class RelationFieldCreator<FIELD extends RelationField> extends AbstractFieldCreator<FIELD> {

    public RelationFieldCreator(EntityContainer<?> container, FieldSpecification fieldSpec) {
        super(container, fieldSpec);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initializeField(RelationField field) {
        field.setEntityClass((Class<PersistentEntity>) dataType);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) RelationField.class;
    }

}
