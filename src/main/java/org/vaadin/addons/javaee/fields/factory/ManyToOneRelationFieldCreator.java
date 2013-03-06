package org.vaadin.addons.javaee.fields.factory;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.fields.ManyToOneRelationField;
import org.vaadin.addons.javaee.fields.converter.SelectEntityConverter;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.AbstractSelect;

public class ManyToOneRelationFieldCreator<FIELD extends AbstractSelect, ENTITY extends PersistentEntity> extends
        AbstractFieldCreator<FIELD> {

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) ManyToOneRelationField.class;
    }

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        EntityContainer<ENTITY> subContainer = container.getSubContainer(fieldSpec.getName());
        field.setItemCaptionPropertyId(fieldSpec.getVisibleProperty());
        field.setContainerDataSource(subContainer);
        field.setConverter(new SelectEntityConverter<ENTITY>(subContainer, field));
    }

}
