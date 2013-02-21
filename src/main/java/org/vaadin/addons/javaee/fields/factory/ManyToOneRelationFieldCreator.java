package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.fields.ManyToOneRelationField;
import org.vaadin.addons.javaee.fields.converter.SelectEntityConverter;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.AbstractSelect;

public class ManyToOneRelationFieldCreator<FIELD extends AbstractSelect, ENTITY extends PersistentEntity> extends
        AbstractFieldCreator<FIELD> {

    public ManyToOneRelationFieldCreator(EntityContainer<?> container, FieldSpecification fieldSpec) {
        super(container, fieldSpec);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) ManyToOneRelationField.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        Class<ENTITY> type = (Class<ENTITY>) container.getType(fieldSpec.getName());
        EntityContainer<ENTITY> subContainer = container.getSubContainer(type);
        field.setItemCaptionPropertyId(fieldSpec.getVisibleProperty());
        field.setContainerDataSource(subContainer);
        field.setConverter(new SelectEntityConverter<ENTITY>(subContainer, field));
    }

}
