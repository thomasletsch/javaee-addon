package org.vaadin.addons.javaee.fields.converter;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.util.converter.Converter;

public class StringToEntityConverter<ENTITY extends PersistentEntity> implements Converter<String, ENTITY> {

    private static final long serialVersionUID = 1L;

    private EntityContainer<ENTITY> container;

    public StringToEntityConverter(EntityContainer<ENTITY> container) {
        this.container = container;
    }

    @Override
    public ENTITY convertToModel(String value, Locale locale) throws Converter.ConversionException {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return container.getItem(Long.parseLong(value)).getEntity();
    }

    @Override
    public String convertToPresentation(ENTITY value, Locale locale) throws Converter.ConversionException {
        if (value == null) {
            return "";
        }
        return "" + value.getId();
    }

    @Override
    public Class<ENTITY> getModelType() {
        return container.getEntityClass();
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
