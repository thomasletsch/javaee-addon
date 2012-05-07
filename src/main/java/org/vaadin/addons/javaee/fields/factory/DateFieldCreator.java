package org.vaadin.addons.javaee.fields.factory;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.vaadin.ui.DateField;
import com.vaadin.ui.DateField.Resolution;

public class DateFieldCreator<FIELD extends DateField> extends AbstractFieldCreator<FIELD> {

    public DateFieldCreator(TranslationService translationService, EntityContainer<?> container, String fieldName, Class<FIELD> fieldType) {
        super(translationService, container, fieldName, fieldType);
    }

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        Temporal temporal = container.getAnnotation(fieldName, Temporal.class);
        TemporalType type = TemporalType.DATE;
        if (temporal != null) {
            type = temporal.value();
        }
        switch (type) {
            case DATE:
                field.setResolution(Resolution.DAY);
                break;
            case TIME:
                field.setResolution(Resolution.SECOND);
                field.setDateFormat("HH:mm:ss");
                break;
            case TIMESTAMP:
                field.setResolution(Resolution.SECOND);
                break;
        }
    };

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) DateField.class;
    }

}
