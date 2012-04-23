package com.optible.vaadin.utils.fields;

import com.optible.vaadin.utils.jpa.EntityContainer;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.ui.Field;

public interface FieldFactory extends FieldGroupFieldFactory {

    <T extends Field<?>> T createField(EntityContainer<?> container, String fieldName);

    <T extends Field<?>> T createField(EntityContainer<?> container, String fieldName, Class<T> fieldType);

}
