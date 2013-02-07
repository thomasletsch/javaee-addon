package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.RelationField;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public class RelationFieldCreator<FIELD extends RelationField> extends AbstractFieldCreator<FIELD> {

    public RelationFieldCreator(TranslationService translationService, EntityContainer<?> container, String fieldName,
            Class<FIELD> fieldType) {
        super(translationService, container, fieldName, fieldType);
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
