package org.vaadin.addons.javaee.fields.spec;

import java.util.Collection;
import java.util.Map;

import org.vaadin.addons.javaee.form.MultiColumnStyle;

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

    public FieldSpecBuilder values(Collection<String> values) {
        spec.setValues(values);
        return this;
    }

    public FieldSpecBuilder values(Map<String, String> values) {
        spec.setValues(values);
        return this;
    }

}
