package org.vaadin.addons.javaee.form;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

@Singleton
public class LabelCreator {

    @Inject
    protected TranslationService translationService;

    public Label createLabel(String sectionName, FieldSpecification fieldSpec) {
        Label label = new Label(translationService.getText(sectionName + "." + fieldSpec.getName()) + ":");
        label.addStyleName("rightalign");
        label.addStyleName(Reindeer.LABEL_SMALL);
        if (fieldSpec.getLabelWidth() != null) {
            label.setWidth(fieldSpec.getLabelWidth());
        }
        return label;
    }

}
