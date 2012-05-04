package org.vaadin.addons.javaee.form;

import com.vaadin.ui.GridLayout;

public class FormSection extends GridLayout {

    public FormSection(String title) {
        setCaption(title);
        setColumns(3);
        setSpacing(true);
        setMargin(true);
        setStyleName("border");
    }
}
