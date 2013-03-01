package org.vaadin.addons.javaee.fields.factory;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextField;

public class LocalTimeFieldCreator<FIELD extends AbstractTextField> extends AbstractFieldCreator<FIELD> {

    @Override
    protected void initializeField(FIELD field) {
        super.initializeField(field);
        field.setNullRepresentation("");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) TextField.class;
    }

}
