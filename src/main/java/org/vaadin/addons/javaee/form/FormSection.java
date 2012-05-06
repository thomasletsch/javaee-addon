package org.vaadin.addons.javaee.form;

import com.vaadin.ui.GridLayout;

public class FormSection extends GridLayout {

    private final String name;

    public FormSection(String name, String title) {
        this.name = name;
        setCaption(title);
        setColumns(3);
        setSpacing(true);
        setMargin(true);
        setStyleName("border smallmargin smallspacing");
        setWidth(100, Unit.PERCENTAGE);
    }

    public String getName() {
        return name;
    }

    @Override
    public void setColumns(int columns) {
        super.setColumns(columns * 2);
    }
}
