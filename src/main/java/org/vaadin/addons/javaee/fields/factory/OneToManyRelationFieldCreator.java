package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.OneToManyRelationField;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public class OneToManyRelationFieldCreator<ENTITY extends PersistentEntity, FIELD extends OneToManyRelationField<ENTITY>> extends
        AbstractFieldCreator<FIELD> {

    private TranslationService translationService;

    public OneToManyRelationFieldCreator(EntityContainer<?> container, TranslationService translationService, FieldSpecification fieldSpec) {
        super(container, fieldSpec);
        this.translationService = translationService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initializeField(FIELD field) {
        field.setEntityClass((Class<ENTITY>) container.getCollectionType(fieldSpec.getName()));
        field.setTranslationService(translationService);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) OneToManyRelationField.class;
    }

}
