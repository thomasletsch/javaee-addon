package org.vaadin.addons.javaee.fields.factory;

import java.util.Map;

import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.ComboBoxTranslated;
import org.vaadin.addons.javaee.form.MultiColumnStyle;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.AbstractSelect;

public class ListValueFieldCreator<FIELD extends AbstractSelect> extends AbstractFieldCreator<FIELD> {

    @Inject
    private TranslationService translationService;

    @Override
    @SuppressWarnings("unchecked")
    protected Class<FIELD> getDefaultFieldType() {
        return (Class<FIELD>) ComboBoxTranslated.class;
    }

    @Override
    protected void initializeField(FIELD field) {
        populateSelect(field);
    };

    protected void populateSelect(FIELD field) {
        Map<String, String> valueMap = null;
        if (fieldSpec.getValueMap() != null) {
            valueMap = fieldSpec.getValueMap();
        } else {
            valueMap = translationService.get(fieldSpec.getName(), fieldSpec.getValues());
        }
        for (String value : valueMap.keySet()) {
            field.addItem(value);
            field.setItemCaption(value, valueMap.get(value));
        }
        if (MultiColumnStyle.HORIZONTAL.equals(fieldSpec.getMultiColumnStyle()) || (valueMap.size() <= 3)) {
            field.setStyleName("horizontal");
        }
    }

}
