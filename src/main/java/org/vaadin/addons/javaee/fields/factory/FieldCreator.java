package org.vaadin.addons.javaee.fields.factory;

import com.vaadin.ui.Field;

@SuppressWarnings("rawtypes")
public interface FieldCreator<FIELD extends Field> {

    FIELD createField();

}
