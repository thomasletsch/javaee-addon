package org.vaadin.addons.javaee.fields.converter;

import java.util.Locale;

import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractSelect;

public class SelectPropertyConverter<ENTITY extends PersistentEntity> implements Converter<String, Long> {

    private static final long serialVersionUID = 1L;

    private EntityContainer<ENTITY> container;

    private AbstractSelect select;

    private String propertyId;

    public SelectPropertyConverter(EntityContainer<ENTITY> container, AbstractSelect select, String propertyId) {
        this.container = container;
        this.select = select;
        this.propertyId = propertyId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long convertToModel(String value, Locale locale) throws Converter.ConversionException {
        return (Long) select.getValue();
    }

    @Override
    public String convertToPresentation(Long value, Locale locale) throws Converter.ConversionException {
        return "" + value;
    }

    @Override
    public Class<Long> getModelType() {
        return Long.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
