package org.vaadin.addons.javaee.fields.factory;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;

public class DateFieldCreator<FIELD extends DateField> extends AbstractFieldCreator<FIELD> {

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        Temporal temporal = container.getAnnotation(fieldSpec.getName(), Temporal.class);
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
