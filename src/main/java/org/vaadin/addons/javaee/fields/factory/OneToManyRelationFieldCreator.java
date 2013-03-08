package org.vaadin.addons.javaee.fields.factory;

import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.OneToManyRelationField;

@SuppressWarnings("rawtypes")
public class OneToManyRelationFieldCreator extends AbstractFieldCreator<OneToManyRelationField> {

    @Inject
    private GlobalFieldFactory fieldFactory;

    @Override
    @SuppressWarnings("unchecked")
    protected void initializeField(OneToManyRelationField field) {
        field.setEntityClass(container.getCollectionType(fieldSpec.getName()));
        field.setFieldFactory(fieldFactory);
    }

    @Override
    protected Class<OneToManyRelationField> getDefaultFieldType() {
        return OneToManyRelationField.class;
    }

}
