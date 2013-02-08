package org.vaadin.addons.javaee.form;

import org.vaadin.addons.javaee.page.AbstractContentView;

import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class FormSection extends GridLayout {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = AbstractContentView.WIDTH - 50;

    private final String name;

    public FormSection(String name, String title) {
        this.name = name;
        setCaption(title);
        setColumns(3);
        setSpacing(true);
        setMargin(true);
        setStyleName("border smallmargin smallspacing");
        setWidth(WIDTH, Unit.PIXELS);
    }

    public String getName() {
        return name;
    }

    public void addField(FieldSpecification fieldSpec, Label label, Field<?> field) {
        if (fieldSpec.getLabelWidth() != null) {
            label.setWidth(fieldSpec.getLabelWidth());
        }
        addComponent(label);
        if (fieldSpec.getFieldWidth() != null) {
            field.setWidth(fieldSpec.getFieldWidth());
        }
        addComponent(field, getCursorX(), getCursorY(), getCursorX() + fieldSpec.getColumns() - 1, getCursorY() + fieldSpec.getRows() - 1);
        if (fieldSpec.isEndRow()) {
            newLine();
        }
    }

    public void setColumnExpandRatios(float... ratios) {
        int i = 0;
        for (float f : ratios) {
            setColumnExpandRatio(i++, f);
        }
    }

    @Override
    public void setColumns(int columns) {
        super.setColumns(columns * 2);
    }
}
