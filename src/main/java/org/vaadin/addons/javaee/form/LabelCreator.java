package org.vaadin.addons.javaee.form;

import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.Label;

public class LabelCreator {

    @Inject
    protected TranslationService translationService;

    public Label createLabel(FormSection section, FieldSpecification fieldSpec) {
        Label label = new Label(translationService.getText(section.getName() + "." + fieldSpec.getName()) + ":");
        label.setStyleName("rightalign");
        if (fieldSpec.getLabelWidth() != null) {
            label.setWidth(fieldSpec.getLabelWidth());
        }
        return label;
    }

}
