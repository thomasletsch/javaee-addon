package org.vaadin.addons.javaee.fields.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

@SuppressWarnings("unchecked")
public abstract class AbstractFieldCreator<FIELD extends Field<?>> implements FieldCreator<FIELD> {

    private static Log log = LogFactory.getLog(AbstractFieldCreator.class);

    protected final EntityContainer<?> container;

    protected final String fieldName;

    protected final Class<FIELD> fieldType;

    protected final TranslationService translationService;

    protected Class<?> dataType;

    public AbstractFieldCreator(TranslationService translationService, EntityContainer<?> container, String fieldName,
            Class<FIELD> fieldType) {
        this.translationService = translationService;
        this.container = container;
        this.fieldName = fieldName;
        if (fieldType != null) {
            this.fieldType = fieldType;
        } else {
            this.fieldType = getDefaultFieldType();
        }
        dataType = container.getType(fieldName);
    }

    protected abstract Class<FIELD> getDefaultFieldType();

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
//        field.getState().setImmediate(true);
        field.setId(container.getEntityClass().getSimpleName() + "." + fieldName);
        ((AbstractComponent) field).setImmediate(true);
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