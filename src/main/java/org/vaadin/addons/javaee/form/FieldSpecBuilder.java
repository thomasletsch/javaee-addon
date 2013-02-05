package org.vaadin.addons.javaee.form;

import com.vaadin.ui.Field;

public class FieldSpecBuilder {

    private FieldSpecification spec;

    public FieldSpecBuilder(String fieldName) {
        spec = new FieldSpecification(fieldName);
    }

    public FieldSpecification build() {
        return spec;
    }

    public FieldSpecBuilder endRow() {
        spec.setEndRow(true);
        return this;
    }

    public FieldSpecBuilder cols(int columns) {
        spec.setColumns(columns);
        return this;
    }

    public FieldSpecBuilder rows(int rows) {
        spec.setRows(rows);
        return this;
    }

    public FieldSpecBuilder type(Class<? extends Field<?>> fieldType) {
        spec.setFieldType(fieldType);
        return this;
    }

    public FieldSpecBuilder vertical() {
        spec.setMultiColumnStyle(MultiColumnStyle.VERTICAL);
        return this;
    }

    public FieldSpecBuilder horizontal() {
        spec.setMultiColumnStyle(MultiColumnStyle.HORIZONTAL);
        return this;
    }

    public FieldSpecBuilder labelWidth(String labelWidth) {
        spec.setLabelWidth(labelWidth);
        return this;
    }

    public FieldSpecBuilder fieldWidth(String fieldWidth) {
        spec.setFieldWidth(fieldWidth);
        return this;
    }

}
