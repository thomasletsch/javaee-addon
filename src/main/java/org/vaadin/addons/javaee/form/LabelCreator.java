package org.vaadin.addons.javaee.form;

import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.Label;

public class LabelCreator {

    @Inject
    protected TranslationService translationService;

    public Label createLabel(String sectionName, FieldSpecification fieldSpec) {
        Label label = new Label(translationService.getText(sectionName + "." + fieldSpec.getName()) + ":");
        label.setStyleName("rightalign");
        if (fieldSpec.getLabelWidth() != null) {
            label.setWidth(fieldSpec.getLabelWidth());
        }
        return label;
    }

}
