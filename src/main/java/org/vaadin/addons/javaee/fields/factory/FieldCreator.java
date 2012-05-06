package org.vaadin.addons.javaee.fields.factory;

import com.vaadin.ui.Field;

public interface FieldCreator<FIELD extends Field<?>> {

    FIELD createField();

}
