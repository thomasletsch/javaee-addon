package org.vaadin.addons.javaee.fields;

import java.util.Map;

import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.ComboBox;

public class ComboBoxTranslated extends ComboBox {

    private static final long serialVersionUID = 1L;

    @Inject
    private TranslationService translationService;

    public ComboBoxTranslated() {
    }

    /**
     * @param caption
     *            The internationalized caption
     * @param values
     *            A Map of value to display value
     * 
     */
    public ComboBoxTranslated(String caption, Map<String, String> values) {
        super(caption, values.keySet());
        for (String value : values.keySet()) {
            setItemCaption(value, values.get(value));
        }
        setNullSelectionAllowed(false);
        setTextInputAllowed(false);
    }

    public void setValues(Map<String, String> values) {
        for (String value : values.keySet()) {
            addItem(value);
            setItemCaption(value, values.get(value));
        }
    }

    @Override
    public void addValueChangeListener(com.vaadin.data.Property.ValueChangeListener listener) {
        super.addValueChangeListener(listener);
        setImmediate(true);
    }

}
