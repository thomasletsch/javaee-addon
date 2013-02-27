package org.vaadin.addons.javaee.fields.factory;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;

public class LocalDateFieldCreator<FIELD extends DateField> extends AbstractFieldCreator<FIELD> {

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        field.setResolution(Resolution.DAY);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) DateField.class;
    }

}
