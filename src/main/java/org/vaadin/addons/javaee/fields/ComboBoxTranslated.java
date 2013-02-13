package org.vaadin.addons.javaee.fields;

import java.util.Map;

import javax.inject.Inject;

import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.ComboBox;

public class ComboBoxTranslated extends ComboBox {

    private static final long serialVersionUID = 1L;

    @Inject
    private TranslationService translationService;

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

}
