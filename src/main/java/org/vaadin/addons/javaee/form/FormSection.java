package org.vaadin.addons.javaee.form;

import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.portal.PortalView;

import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class FormSection extends GridLayout {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = PortalView.CONTENT_WIDTH - (PortalView.DEFAULT_MARGIN * 2);

    private final String name;

    public FormSection(String name, String title) {
        this.name = name;
        setCaption(title);
        setColumns(3);
        setSpacing(true);
        setMargin(true);
        setStyleName("border smallmargin smallspacing");
        setWidth("100%");
    }

    public String getName() {
        return name;
    }

    public void addField(FieldSpecification fieldSpec, Label label, Field<?> field) {
        addComponent(fieldSpec, label, field);
    }

    public void addComponent(FieldSpecification fieldSpec, Label label, Component field) {
        if (fieldSpec.getLabelWidth() != null) {
            label.setWidth(fieldSpec.getLabelWidth());
        }
        addComponent(label);
        if (fieldSpec.getFieldWidth() != null) {
            field.setWidth(fieldSpec.getFieldWidth());
        }
        if (fieldSpec.getRows() > 1) {
            setRows(getRows() + fieldSpec.getRows());
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
