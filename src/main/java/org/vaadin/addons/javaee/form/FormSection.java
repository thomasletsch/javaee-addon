package org.vaadin.addons.javaee.form;

import javax.inject.Inject;

import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;
import org.vaadin.addons.javaee.portal.PortalView;

import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class FormSection extends GridLayout {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = PortalView.CONTENT_WIDTH - (PortalView.DEFAULT_MARGIN * 2);

    private String name;

    @Inject
    protected TranslationService translationService;

    @Inject
    protected FieldCreator fieldCreator;

    @Inject
    protected LabelCreator labelCreator;

    protected EntityContainer<?> container;

    protected EntityFieldGroup<?> fieldGroup;

    public FormSection() {
        setColumns(3);
        setSpacing(true);
        setMargin(true);
        setStyleName("border smallmargin smallspacing");
        setWidth("100%");
    }

    /**
     * Can be overwritten. Will be called after initialization.
     */
    public void init() {
        setCaption();
    }

    protected void setCaption() {
        if (translationService != null && name != null) {
            setCaption(translationService.getText(name));
        }
    }

    public String getName() {
        return name;
    }

    public Field<?> addField(String fieldName) {
        return addField(new FieldSpecification(fieldName));
    }

    public Field<?> addField(FieldSpecification fieldSpec) {
        Field<?> field = fieldCreator.createField(container, fieldGroup, fieldSpec);
        addComponent(fieldSpec, labelCreator.createLabel(this, fieldSpec), field);
        return field;
    }

    public void addField(FieldSpecification fieldSpec, Field<?> field) {
        fieldCreator.bindField(fieldGroup, fieldSpec, field);
        addComponent(fieldSpec, labelCreator.createLabel(this, fieldSpec), field);
    }

    public void addField(FieldSpecification fieldSpec, Label label, Field<?> field) {
        fieldCreator.bindField(fieldGroup, fieldSpec, field);
        addComponent(fieldSpec, label, field);
    }

    public void addComponent(FieldSpecification fieldSpec, Component field) {
        addComponent(fieldSpec, labelCreator.createLabel(this, fieldSpec), field);
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
            setCursorX(0);
            setCursorY(getCursorY() + fieldSpec.getRows());
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

    public EntityContainer<?> getContainer() {
        return container;
    }

    public void setContainer(EntityContainer<?> container) {
        this.container = container;
    }

    public EntityFieldGroup<?> getFieldGroup() {
        return fieldGroup;
    }

    public void setFieldGroup(EntityFieldGroup<?> fieldGroup) {
        this.fieldGroup = fieldGroup;
    }

    public void setName(String name) {
        this.name = name;
        setCaption();
    }

}
