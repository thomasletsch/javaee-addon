package com.optible.vaadin.utils.fields;

import java.util.Calendar;
import java.util.EnumSet;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.optible.vaadin.utils.i18n.TranslationService;
import com.optible.vaadin.utils.jpa.EntityContainer;
import com.vaadin.data.Item;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

@SessionScoped
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JavaEEFieldGroupFieldFactory implements FieldFactory {

    private static Log log = LogFactory.getLog(JavaEEFieldGroupFieldFactory.class);

    public static final Object CAPTION_PROPERTY_ID = "Caption";

    @Inject
    private TranslationService translationService;

    @Override
    public <T extends Field<?>> T createField(EntityContainer<?> container, String fieldName) {
        return createField(container, fieldName, null);
    }

    @Override
    public <T extends Field<?>> T createField(EntityContainer<?> container, String fieldName, Class<T> fieldType) {
        Class<?> dataType = container.getType(fieldName);
        T field = createField(dataType, fieldType);
        field.setCaption(translationService.get(fieldName, field.getLocale()));
        field.setBuffered(true);
        field.getState().setImmediate(false);
        return field;
    }

    @Override
    public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
        if (fieldType == null) {
            fieldType = getDefaultFieldType(dataType);
        }
        Field field = instanciateField(fieldType);
        if (Enum.class.isAssignableFrom(dataType)) {
            initializeEnumSelect((AbstractSelect) field, (Class<? extends Enum>) dataType);
        }
        if (field instanceof AbstractTextField) {
            AbstractTextField abstractTextField = (AbstractTextField) field;
            abstractTextField.setNullRepresentation("");
        }
        return (T) field;
    }

    private <T extends Field> Class<T> getDefaultFieldType(Class<?> dataType) {
        if (Boolean.class.isAssignableFrom(dataType) || boolean.class.isAssignableFrom(dataType)) {
            return (Class<T>) CheckBox.class;
        } else if (Number.class.isAssignableFrom(dataType)) {
            return (Class<T>) TextField.class;
        } else if (String.class.isAssignableFrom(dataType)) {
            return (Class<T>) TextField.class;
        } else if (Enum.class.isAssignableFrom(dataType)) {
            return (Class<T>) ComboBox.class;
        } else if (Calendar.class.isAssignableFrom(dataType)) {
            return (Class<T>) DateField.class;
        } else {
            return (Class<T>) TextField.class;
        }
    }

    protected <T extends Field> T instanciateField(Class<T> fieldType) {
        try {
            T field = fieldType.newInstance();
            ((AbstractComponent) field).setImmediate(true);
            return field;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Could not instanciate " + fieldType, e);
        }
        return (T) new TextField();
    }

    protected <T extends AbstractSelect> void initializeEnumSelect(T field, Class<? extends Enum> dataType) {
        field.setMultiSelect(false);
        field.setNullSelectionAllowed(false);
        populateEnumSelect(field, dataType);
    }

    protected void populateEnumSelect(AbstractSelect select, Class<? extends Enum> enumClass) {
        select.removeAllItems();
        for (Object p : select.getContainerPropertyIds()) {
            select.removeContainerProperty(p);
        }
        select.addContainerProperty(CAPTION_PROPERTY_ID, String.class, "");
        select.setItemCaptionPropertyId(CAPTION_PROPERTY_ID);
        EnumSet<?> enumSet = EnumSet.allOf(enumClass);
        for (Object r : enumSet) {
            Item newItem = select.addItem(r);
            String i18nKey = enumClass.getSimpleName() + "." + r.toString();
            newItem.getItemProperty(CAPTION_PROPERTY_ID).setValue(translationService.get(i18nKey, select.getLocale()));
        }
    }

}
