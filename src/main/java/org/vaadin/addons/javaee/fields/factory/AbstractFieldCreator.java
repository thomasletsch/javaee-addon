package org.vaadin.addons.javaee.fields.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

@SuppressWarnings("unchecked")
public abstract class AbstractFieldCreator<FIELD extends Field<?>> implements FieldCreator<FIELD> {

    private static Logger log = LoggerFactory.getLogger(AbstractFieldCreator.class);

    protected final EntityContainer<?> container;

    protected final String fieldName;

    protected final Class<FIELD> fieldType;

    protected Class<?> dataType;

    protected FieldSpecification fieldSpec;

    public AbstractFieldCreator(EntityContainer<?> container, FieldSpecification fieldSpec) {
        this.container = container;
        this.fieldSpec = fieldSpec;
        this.fieldName = fieldSpec.getName();
        if (fieldSpec.getFieldType() != null) {
            this.fieldType = (Class<FIELD>) fieldSpec.getFieldType();
        } else {
            this.fieldType = getDefaultFieldType();
        }
        dataType = container.getType(fieldName);
    }

    protected abstract Class<FIELD> getDefaultFieldType();

    @SuppressWarnings("unused")
    protected void initializeField(FIELD field) {
    }

    @Override
    public FIELD createField() {
        FIELD field = instanciateField();
        commonFieldInit(field);
        initializeField(field);
        return field;
    }

    protected void commonFieldInit(FIELD field) {
        field.setCaption(null);
        field.setBuffered(true);
        field.setId(container.getEntityClass().getSimpleName() + "." + fieldName);
    }

    protected FIELD instanciateField() {
        try {
            FIELD field = fieldType.newInstance();
            return field;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Could not instanciate " + fieldType, e);
        }
        return (FIELD) new TextField();
    }

}